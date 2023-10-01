#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>

#include "fonctionTPC.h"


void initialisation(char* adresseIP_pconfig, char* port_pconfig, char* IP_pi, char* port_pi, char* numero_pi, in_addr_t IP_voisin, in_port_t port_voisin) {
    // -- Etape 1 : Création socket client  
    int socket_pi = socket(PF_INET, SOCK_DGRAM, 0);

    if (socket_pi == -1){
        perror("❌ Pi : problème creation socket :");
        exit(1);
    }

    printf("✅ Pi : Creation de la socket Pi réussie.\n");


    // -- Etape 2 : Designation de la socket pconfig
    struct sockaddr_in socket_pconfig;
    socket_pconfig.sin_family = AF_INET;
    socket_pconfig.sin_addr.s_addr = inet_addr(adresseIP_pconfig);
    socket_pconfig.sin_port = htons(atoi(port_pconfig));

    printf("✅ Pi : Désignation de la socket pconfig réussie.\n");

    // -- Etape 3 : Envois de son numero de processus
    printf("----- 📨 Envois numero Pi -----\n");

    printf("\tNumero Pi : '%s'\n", numero_pi);

    socklen_t sizeAdr = sizeof(struct sockaddr_in);

    printf("\t📨 Envois du numéro a l'ip : %s et au port %s\n", adresseIP_pconfig, port_pconfig);

    int resSend = sendto(socket_pi, &numero_pi, strlen(numero_pi)+1, 0, (struct sockaddr *) &socket_pconfig, sizeAdr) ;
    
    if (resSend == -1) {
        perror("\t❌ Pi : problème avec le send to :");
        exit(1);
    }
    printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);

    printf("----- Fin envoie numero Pi -----\n\n");

    // ---------------------------------------------------------------------

    printf("----- 📩 Recevoir IP voisin -----\n");

    struct sockaddr_in sockExpediteur;
    socklen_t lgAdr = sizeof(struct sockaddr_in);

    in_addr_t ip_suivant = 0;
    ssize_t resRecv = recvfrom(socket_pi, &ip_suivant, sizeof(ip_suivant), 0, (struct sockaddr *) &sockExpediteur, &lgAdr);
    if (resRecv == -1) {
        perror("\t❌ Pi : problème avec le recvFrom :");
        exit(1);
    }

    printf("\tMessage reçus : %d\n", ip_suivant);

    IP_voisin = ip_suivant;

    printf("-----Fin reception IP voisin -----\n");

    // ---------------------------------------------------------------------

    printf("----- 📩 Recevoir port voisin -----\n");

    in_port_t port_suivant = 0;
    resRecv = recvfrom(socket_pi, &ip_suivant, sizeof(ip_suivant), 0, (struct sockaddr *) &sockExpediteur, &lgAdr);
    if (resRecv == -1) {
        perror("\t❌ Pi : problème avec le recvFrom :");
        exit(1);
    }

    printf("\tMessage reçus : %d\n", ip_suivant);

    port_voisin = port_suivant;

    printf("-----Fin reception port voisin -----\n");

    int cls = close(socket_pi);
    if (cls == -1) {
        perror("❌ Pi : problème avec le close :");
        exit(1); // je choisis ici d'arrêter le programme
    }
    printf("✅ Fermeture de la socket réussi.\n");

    printf("*************Fin des échanges avec Pconfig*************\n");

}

void traitementClassique(int socketPconfig) {
  // --- Etape 1 : Reception taille message
  printf("-----Recevoir message-----\n");
  printf("  --Recevoir la taille du message--\n");

  int tailleMessage;
  ssize_t resRecvTCPsize = recvTCP(socketPconfig, &tailleMessage, sizeof(tailleMessage));

  printf("\tMessage recus : '%d'\n", tailleMessage);
  printf("\tNombre d'octet recus : '%ld'\n\n", resRecvTCPsize);

  // --- Etape 2 : Reception du message
  printf("  --Recevoir le message de taille %d--\n", tailleMessage);

  char messageRecu[30000];
  ssize_t resRecvTCP = recvTCP(socketPconfig, messageRecu, tailleMessage);

  printf("\tMessage recus : '%s'\n", messageRecu);
  printf("\tNombre d'octet recus : '%ld'\n\n", resRecvTCP);

  printf("-----Fin reception message-----\n");

  // --- Etape 3 : Envois taille message recu
  printf("-----Envoyer message------\n");

  int resSendTCP = sendTCP(socketPconfig, &resRecvTCP, sizeof(int));
     
  printf("\tMessage envoyé : %ld\n", resRecvTCP);
  printf("\tNombre d'octets envoyés : %d\n", resSendTCP);
   
 
  printf("-----Fin envoie message------\n");

  // --- Etape 4 : Fermeture socketPconfig
  closeSocket(socketPconfig);
  return;
}

int main(int argc, char *argv[]) {
    /* Je passe en paramètre le numéro de port et le numero du processus.*/
    if (argc != 6){
        printf("utilisation : %s IP_pi port_pi IP_pconfig port_pconfig numero_pi\n", argv[0]);
        exit(1);
    }
    char* IP_pi = argv[1];
    char* port_pi = argv[2];
    char* adresseIP_pconfig = argv[3];
    char* port_pconfig = argv[4];
    char* numero_pi = argv[5];

    in_addr_t IP_voisin = 0;
    in_port_t port_voisin = 0;

    initialisation(adresseIP_pconfig, port_pconfig, IP_pi, port_pi, numero_pi, IP_voisin, port_voisin);

    printf("Finiiis");

    

    printf("Pi terminé.\n");

    return 0;
}