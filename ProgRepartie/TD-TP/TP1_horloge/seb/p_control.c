#include <arpa/inet.h>
#include <errno.h>
#include <netdb.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

struct Edge {
  int from;
  int to;
};

struct Graph {
  int nodes_count;
  int edges_count;

  struct Edge *edges;
};

struct Graph *init_graph(char *file_path) {
  struct Graph *result = malloc(sizeof(struct Graph));

  FILE *fs = fopen(file_path, "r");
  if (fs == NULL) {
    perror("Erreur lors de l'ouverture du fichier graph");
    exit(EXIT_FAILURE);
  }

  char line[100];
  int e_i = 0;
  while (fgets(line, sizeof(line), fs)) {
    // Si la ligne commence par un c, on l'omit
    if (strncmp(line, "c", strlen("c")) == 0)
      continue;

    // Si elle commence par un p, c'est la chienneté
    if (strncmp(line, "p", strlen("p")) == 0) {
      char useless[20];
      if (sscanf(line, "p %s %i %i", useless, &result->nodes_count,
                 &result->edges_count) != 3) {
        printf("Erreur lors de la lecture de la taille du graph.\n");
      }

      result->edges = malloc(sizeof(struct Edge) * result->edges_count);
      continue;
    }

    int from, to;
    if (sscanf(line, "e %i %i", &from, &to) == 2) {
      struct Edge e = result->edges[e_i];
      e.from = from > to ? to : from;
      e.to = from > to ? from : to;
      result->edges[e_i] = e;

      e_i++;
    }
  }

  fclose(fs);
  return result;
}

void clear_graph(struct Graph *g) { free(g->edges); }

int init_socket(struct Graph *g, short port) {
  int sd = socket(AF_INET, SOCK_STREAM, 0);
  if (sd == -1) {
    perror("Erreur lors de l'initialisation de la socket");
    exit(EXIT_FAILURE);
  }

  struct sockaddr_in addr;
  addr.sin_family = AF_INET;
  addr.sin_addr.s_addr = INADDR_ANY;
  addr.sin_port = htons(port);
  if (bind(sd, (struct sockaddr *)&addr, sizeof(addr)) == -1) {
    perror("Erreur lors du nommage de la socket");
    exit(EXIT_FAILURE);
  }

  if (listen(sd, g->edges_count) == -1) {
    perror("Erreur lors du passage en mode écoute");
    exit(EXIT_FAILURE);
  }

  return sd;
}

struct Clients {
  int *sockets;
  struct sockaddr_in *addresses;
};

struct Clients await_clients(int sd, struct Graph *g) {
  int *sockets = malloc(sizeof(int) * g->edges_count);
  struct sockaddr_in *addresses =
      malloc(sizeof(struct sockaddr_in) * g->edges_count);

  struct Clients clients;
  clients.sockets = sockets;
  clients.addresses = addresses;

  for (int i = 0; i < g->nodes_count; i++) {
    socklen_t len;
    sockets[i] = accept(sd, (struct sockaddr *)&(addresses[i]), &len);
    if (sockets[i] == -1) {
      perror("Erreur lors de l'acceptation d'un client");
      exit(EXIT_FAILURE);
    }
  }

  return clients;
}

void clear_clients(struct Graph *g, struct Clients *clients) {
  for (int i = 0; i < g->nodes_count; i++) {
    close(clients->sockets[i]);
  }
  free(clients->addresses);
  free(clients->sockets);
}

struct HelloMessage {
  int id;
  int receiving_from_count;
  int sending_to_count;
};

void fill_with_graph(struct HelloMessage *message, struct Graph *g) {
  int r = 0;
  int s = 0;
  for (int i = 0; i < g->edges_count; i++) {
    struct Edge e = g->edges[i];
    if (e.to == message->id)
      s++;
    if (e.from == message->id)
      r++;
  }
  message->receiving_from_count = s;
  message->sending_to_count = r;
}

void hello_everyone(struct Graph *g, struct Clients *clients) {
  for (int i = 0; i < g->nodes_count; i++) {
    struct HelloMessage message;
    message.id = i + 1;
    fill_with_graph(&message, g);

    if (send(clients->sockets[i], &message, sizeof(message), 0) == -1) {
      perror("Erreur lors de l'envoi des messages hello");
      exit(EXIT_FAILURE);
    }
  }
}

void receive_addresses(struct Graph *g, struct Clients *clients) {
  for (int i = 0; i < g->nodes_count; i++) {
    struct sockaddr_in addr;
    if (recv(clients->sockets[i], &addr, sizeof(addr), 0) == -1) {
      perror("Erreur lors de la réception des adresses des clients");
      exit(EXIT_FAILURE);
    }
    clients->addresses[i] = addr;
  }
}

void send_neighbours(struct Graph *g, struct Clients *clients) {
  for (int i = 0; i < g->edges_count; i++) {
    struct Edge e = g->edges[i];
    if (send(clients->sockets[e.from - 1], &(clients->addresses[e.to - 1]),
             sizeof(struct sockaddr_in), 0) == -1) {
      perror("Erreur lors de l'envoi des voisins");
    }
  }
}

int main(int argc, char **argv) {
  if (argc != 3) {
    printf("Syntaxe: %s <fichier du graph> <port>\n", argv[0]);
    exit(EXIT_FAILURE);
  }

  // On initialise notre structure de donnée représentant le graphe
  struct Graph *g = init_graph(argv[1]);

  // On initialise la socket et attendons les clients
  int sd = init_socket(g, (short)atoi(argv[2]));
  struct Clients clients = await_clients(sd, g);

  // Une fois que tout le monde est là, on envoie à tout le monde le
  // message d'hello
  hello_everyone(g, &clients);
  receive_addresses(g, &clients);
  // et on envoie les voisins à chaque noeuds
  send_neighbours(g, &clients);

  // Une fois tout ça terminé, job done!
  clear_clients(g, &clients);
  clear_graph(g);
  close(sd);
}