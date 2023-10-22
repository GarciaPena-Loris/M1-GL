#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdio.h>

#include <arpa/inet.h>
#include <sys/socket.h>
#include <ifaddrs.h>
#include <stdio.h>

/* Programme client */
void traitement(int ds, int nbAppel)
{

  printf("  Envois du message... \n");
  long tailleMessage = 245;
  char* message = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
  
  ssize_t resSend = send(ds, &message, tailleMessage, 0);
  if (resSend == -1)
  {
    perror("Client : pb avec le send:");
    close(ds);
    exit(1);
  }
  else if (resSend == 0)
  {
    perror("Client : Connection avec le serveur perdu :");
    close(ds);
    exit(1);
  }
  printf("  taille message envoyé: %zd\n\n", sizeof(message));
  printf("  Message envoyés lors de l'envois du message: '%s'\n", message);
  printf("  Nombre d'octets envoyés lors de l'envois de la taille: %zd\n\n", resSend);
  close(ds);

  return;
}

int main(int argc, char *argv[])
{

  /* Je passe en paramètre le numéro de port qui sera donné à la socket créée plus loin.*/
  if (argc != 5)
  {
    printf("utilisation : %s ip_serveur port_serveur port_client nb_iteration\n", argv[0]);
    exit(1);
  }

  /* Etape 1 : créer une socket */
  int ds = socket(PF_INET, SOCK_STREAM, 0);

  if (ds == -1)
  {
    perror("Client : pb creation socket :");
    exit(1);
  }
  printf("Client : creation de la socket réussie \n");

  /* Etape 2 : Connection a notre serveur */
  struct sockaddr_in ss;
  ss.sin_family = AF_INET;
  ss.sin_addr.s_addr = inet_addr(argv[1]);
  ss.sin_port = htons(atoi(argv[2]));

  /*Etape 3  Demandes de connection au serveur*/
  if (connect(ds, (struct sockaddr *)&ss, sizeof(ss)) == -1)
  {
    perror("Client : probleme avec le connect :");
    close(ds);
    exit(0);
  }
  printf("Client : connection réussi\n\n");

  traitement(ds, atoi(argv[4]));

  /*int j;
  scanf("%d", &j);*/
  close(ds);
  printf("Client : terminé :)\n");
  return 0;
}
