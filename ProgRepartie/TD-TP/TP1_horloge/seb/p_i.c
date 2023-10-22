#include <arpa/inet.h>
#include <errno.h>
#include <netdb.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>

int init_socket(struct sockaddr_in *addr) {
  int sd = socket(AF_INET, SOCK_STREAM, 0);
  int no_addr = addr == NULL;
  struct sockaddr_in any_addr;

  if (addr == NULL) {
    any_addr.sin_family = AF_INET;
    any_addr.sin_port = htons((short)0);
    any_addr.sin_addr.s_addr = INADDR_ANY;
    addr = &any_addr;
  }
  if (bind(sd, (struct sockaddr *)addr, sizeof(struct sockaddr_in)) == -1) {
    if (no_addr)
      perror("Erreur lors du nommage de la socket (automatique)");
    else
      perror("Erreur lors du nommage de la socket (non-automatique)");
    exit(EXIT_FAILURE);
  }

  return sd;
}

struct sockaddr_in get_addr(int sd) {
  struct sockaddr_in addr;
  socklen_t size;
  if (getsockname(sd, (struct sockaddr *)&addr, &size) == -1) {
    perror("Erreur lors de la récupération de l'adresse d'une socket");
    exit(EXIT_FAILURE);
  }
  return addr;
}

void connect_to(int sd, char *ip, short port) {
  struct sockaddr_in server_addr;
  server_addr.sin_family = AF_INET;
  server_addr.sin_port = htons(port);
  server_addr.sin_addr.s_addr = inet_addr(ip);
  if (connect(sd, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
    printf("Erreur lors de la connexion: %s\n", strerror(errno));
    exit(EXIT_FAILURE);
  }
}

struct sockaddr_in read_address(int sd) {
  struct sockaddr_in addr;
  socklen_t size;
  if (getsockname(sd, (struct sockaddr *)&addr, &size) == -1) {
    perror("Impossible de trouver le nom donné à la socket.");
    exit(EXIT_FAILURE);
  }
  return addr;
}

struct HelloMessage {
  int id;
  int receiving_from_count;
  int sending_to_count;
};

struct HelloMessage read_hello(int sd_controller) {
  struct HelloMessage message;
  if (recv(sd_controller, &message, sizeof(message), 0) == -1) {
    printf("Erreur lors de la réception du message: %s\n", strerror(errno));
  }
  return message;
}

void send_addr(int sd_controller, struct sockaddr_in addr) {
  if (send(sd_controller, (struct sockaddr *)&addr, sizeof(addr), 0) == -1) {
    perror("Erreur lors de l'envoi de l'adresse du Pi");
    exit(EXIT_FAILURE);
  }
}

void listen_to_neighbours(int sd, struct HelloMessage hello) {
  if (listen(sd, hello.receiving_from_count) == -1) {
    perror("Erreur lors du passage en écoute pour les voisins");
    exit(EXIT_FAILURE);
  }
}

void read_and_connect_neighbours(int sd_controller, struct HelloMessage hello,
                                 int *sockets) {
  printf("Je suis %i et dois me connecter à %i clients!\n", hello.id,
         hello.sending_to_count);
  for (int i = 0; i < hello.sending_to_count; i++) {
    struct sockaddr_in addr;
    if (recv(sd_controller, &addr, sizeof(struct sockaddr_in), 0) == -1) {
      perror("Erreur lors de la réception des voisins");
      exit(EXIT_FAILURE);
    }
    printf("Connexion vers %s:%i\n", inet_ntoa(addr.sin_addr),
           ntohs(addr.sin_port));

    sockets[i] = init_socket(NULL);
    if (connect(sockets[i], (struct sockaddr *)&addr, sizeof(addr)) == -1) {
      perror("Erreur lors de la connexion aux voisins");
      exit(EXIT_FAILURE);
    }
    printf("✅ Connecté à %s:%i\n", inet_ntoa(addr.sin_addr),
           ntohs(addr.sin_port));
  }
}

void accept_neighbours(int sd, struct HelloMessage message, int *sockets) {
  printf("Je suis %i et dois accepter à %i clients!\n", message.id,
         message.receiving_from_count);
  for (int i = 0; i < message.receiving_from_count; i++) {
    struct sockaddr_in addr;
    socklen_t size;
    printf("J'attends le client %i...\n", i + 1);
    sockets[message.sending_to_count + i] =
        accept(sd, (struct sockaddr *)&addr, &size);
    if (sockets[message.sending_to_count + i] == -1) {
      perror("Erreur lors de l'acceptation des voisins");
      exit(EXIT_FAILURE);
    }
    printf("✅ %s:%i accepté\n", inet_ntoa(addr.sin_addr), ntohs(addr.sin_port));
  }
}

int main(int argc, char **argv) {
  if (argc != 3) {
    printf("Syntaxe: %s <ip controller> <port controller>\n", argv[0]);
    exit(EXIT_FAILURE);
  }

  // On initialise la socket pour p_control et on se connecte au serveur
  int sd_controller = init_socket(NULL);
  // On initialise la socket qui permettra d'accepter des voisins
  int sd = init_socket(NULL);
  struct sockaddr_in sd_addr = get_addr(sd);

  connect_to(sd_controller, argv[1], atoi(argv[2]));

  // On récupère le hello du message qui nous attribue un id et nous
  // donne des informations.
  struct HelloMessage message = read_hello(sd_controller);
  listen_to_neighbours(sd, message);
  send_addr(sd_controller, sd_addr);

  // On initialise un tableau de sockets
  int sockets[message.receiving_from_count + message.sending_to_count];

  read_and_connect_neighbours(sd_controller, message, sockets);
  accept_neighbours(sd, message, sockets);
}