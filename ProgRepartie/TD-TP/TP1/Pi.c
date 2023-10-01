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

struct sockaddr_in initialisation(char *adresseIP_pconfig, char *port_pconfig, int numero_pi)
{
    // -- Etape 1 : Création socket client
    int socket_pi = socket(PF_INET, SOCK_DGRAM, 0);

    if (socket_pi == -1)
    {
        perror("❌ Pi : problème creation 🧦 :");
        exit(1);
    }

    printf("✅ Pi : Creation de la 🧦 Pi réussie.\n");

    // -- Etape 2 : Designation de la socket pconfig
    struct sockaddr_in socket_pconfig;
    socket_pconfig.sin_family = AF_INET;
    socket_pconfig.sin_addr.s_addr = inet_addr(adresseIP_pconfig);
    socket_pconfig.sin_port = htons(atoi(port_pconfig));

    printf("✅ Pi : Désignation de la 🧦 pconfig réussie.\n");

    // -- Etape 3 : Envois de son numero de processus
    printf("----- 📨 Envois numero Pi -----\n");

    printf("\t🧮 Numero Pi : '%d'\n", numero_pi);

    socklen_t sizeAdr = sizeof(struct sockaddr_in);

    printf("\t📨 Envois du numéro a l'ip : %s et au port %s\n", adresseIP_pconfig, port_pconfig);

    int resSend = sendto(socket_pi, &numero_pi, sizeof(numero_pi) + 1, 0, (struct sockaddr *)&socket_pconfig, sizeAdr);

    if (resSend == -1)
    {
        perror("\t❌ Pi : problème avec le send to :");
        exit(1);
    }
    printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);

    printf("----- Fin envoie numero Pi -----\n\n");

    // ---------------------------------------------------------------------

    printf("----- 📩 Recevoir 🧦 voisin -----\n");

    struct sockaddr_in sockExpediteur;
    socklen_t lgAdr = sizeof(struct sockaddr_in);

    struct sockaddr_in socket_suivant;
    ssize_t resRecv = recvfrom(socket_pi, &socket_suivant, sizeof(socket_suivant), 0, (struct sockaddr *)&sockExpediteur, &lgAdr);
    if (resRecv == -1)
    {
        perror("\t❌ Pi : problème avec le recvFrom :");
        exit(1);
    }

    char *ip_pi_suivant = inet_ntoa(socket_suivant.sin_addr);
    int port_pi_suivant = ntohs(socket_suivant.sin_port);

    printf("\t📮 Ip du Pi suivant : %s\n", ip_pi_suivant);
    printf("\t🐖 Port du Pi suivant : %d\n", port_pi_suivant);

    printf("----- 🏆 Fin reception 🧦 voisin -----\n");

    int cls = close(socket_pi);
    if (cls == -1)
    {
        perror("❌ Pi : problème avec le close :");
        exit(1); // je choisis ici d'arrêter le programme
    }
    printf("🚪 Fermeture de la 🧦 réussi.\n");

    printf(" --- 👋 Fin des échanges avec Pconfig --- \n");

    return socket_suivant;
}

void traitementClassique(int socketPconfig)
{
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

int main(int argc, char *argv[])
{
    /* Je passe en paramètre le numéro de port et le numero du processus.*/
    if (argc != 4)
    {
        printf("utilisation : %s IP_pconfig port_pconfig numero_pi\n", argv[0]);
        exit(1);
    }
    char *adresseIP_pconfig = argv[1];
    char *port_pconfig = argv[2];
    int numero_pi = atoi(argv[3]);

    struct sockaddr_in socket_suivant = initialisation(adresseIP_pconfig, port_pconfig, numero_pi);

    printf("Finiiis \n ");

    printf("Pi terminé.\n");

    return 0;
}