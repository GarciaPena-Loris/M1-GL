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

struct Couple {
    int numero_pi;
    int compteur_pi;
};

struct sockaddr_in initialisation(char *adresseIP_pconfig, char *port_pconfig, int port_pi, int numero_pi)
{
    // -- Etape 1 : Creation socker pi 
    int socket_pi = socket(PF_INET, SOCK_DGRAM, 0);

    if (socket_pi == -1)
    {
        perror("❌ Pi : problème creation 🧦 :");
        exit(1);
    }

    printf("✅ Pi : Creation de la 🧦 Pi réussie.\n");

    // -- Etape 2 : Nommer la socket du client
    struct sockaddr_in strctureSocket_pi;
    strctureSocket_pi.sin_family = AF_INET;
    strctureSocket_pi.sin_addr.s_addr = INADDR_ANY;
    strctureSocket_pi.sin_port = htons(port_pi);

    int res = bind(socket_pi, (struct sockaddr*) &strctureSocket_pi, sizeof(strctureSocket_pi));
    if (res == -1) {
        perror("❌ Pi : probleme du bind :");
        exit(1);
    }

    printf("🏷️  Nommage de la socket réussi.\n");

    printf(" --- 👋 Début des échanges avec Pconfig --- \n");

    // -- Etape 1 : Designation de la socket pconfig
    struct sockaddr_in strctureSocket_pconfig;
    strctureSocket_pconfig.sin_family = AF_INET;
    strctureSocket_pconfig.sin_addr.s_addr = inet_addr(adresseIP_pconfig);
    strctureSocket_pconfig.sin_port = htons(atoi(port_pconfig));

    printf("🏆 Pi : Désignation de la 🧦 pconfig réussie.\n"); 

    // -- Etape 2 : Envois de son numero de processus
    printf("----- 📨 Envois numero Pi -----\n");

    printf("\t🧮 Numero Pi : '%d'\n", numero_pi);
    printf("\t🐖 Port du Pi : %d\n", port_pi);

    socklen_t sizeAdr = sizeof(struct sockaddr_in);

    printf("\t📨 Envois du numéro a l'ip : %s et au port %s\n", adresseIP_pconfig, port_pconfig);

    int resSend = sendto(socket_pi, &numero_pi, sizeof(numero_pi) + 1, 0, (struct sockaddr *)&strctureSocket_pconfig, sizeAdr);

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
    printf("\t🚪 Fermeture de la 🧦 réussi.\n");

    printf(" --- 👋 Fin des échanges avec Pconfig --- \n\n");

    return socket_suivant;
}



int traitementClassique(int port_pi, int numero_pi, struct sockaddr_in structureSocket_suivant)
{
    printf(" --- 📲 Connection avec les voisins --- \n");

    // --- Etape 1 : Creation des socket
    int socketPi = creerSocket();

    nommerSocket(socketPi, port_pi);

    printf("\t⌚ Pi : En attente de la connection du precedent...\n");

    ecouterDemande(socketPi);

    // printf("\t✅ Pi : Connection du \033[1mprecedent\033[0m reussi.\n");

    int socketSuivant = creerSocket();
    
    printf("\t⌚ Pi : En attente d'acceptation du suivant...\n");

    connectionSocket(socketSuivant, structureSocket_suivant);

    // --- Etape 2 : Envoi demande connection au suivant
    struct sockaddr_in adresseClient;
    int socketPrecedent = accepterDemande(socketPi, &adresseClient);
    if (socketPrecedent == -1) {
        printf("\t❌ Pi : Annulation traitement avec le precedent\n");
    }
    printf("✅ Pi : Connection au \033[1msuivant\033[0m reussi.\n");

    printf("🏆 Pi : Initialisation des 🧦 réussie.\n");

    printf(" --- 👋 Début des échanges avec les voisins --- \n");

    struct Couple couple;
    couple.numero_pi = numero_pi;
    couple.compteur_pi = 1;

    while (1) {
        // --- Etape 3 : Envoi du couple au suivant
        printf("----- 📨 Envois du couple Pi suivant -----\n");
        printf("  -- 📏 Envoi de la taille du message --\n");

        int tailleMessage = sizeof(couple);
        ssize_t resSendTCPsize = sendTCP(socketSuivant, &tailleMessage, sizeof(tailleMessage));
        if (resSendTCPsize == 0 || resSendTCPsize == -1) {
            printf("\t❌ Pi : Arret de la boucle.\n");
            break;
        }

        printf("\tNombre d'octets envoyés : %zd\n", resSendTCPsize);
        printf("\tMessage envoyé : '%d'\n", tailleMessage);

        printf("  -- 💑 Envoi du couple --\n");
        ssize_t resSendTCP = sendTCP(socketSuivant, &couple, sizeof(couple));
        if (resSendTCP == 0 || resSendTCP == -1) {
            printf("\t❌ Pi : Arret de la boucle.\n");
            break;
        } 
        printf("\tCouple envoyé - Numero Pi : %d, Compteur Pi : %d\n", couple.numero_pi, couple.compteur_pi);
        printf("\tNombre d'octets envoyés : %zd\n", resSendTCP);

        printf("----- 🏆 Fin envoie du couple au suivant ------\n\n");

        // --- Etape 4 : Recevoir le couple du precedent
        printf("----- 📩 Réception du couple du voisin précédent -----\n");
        printf("  -- 📏 Réception de la taille du message --\n");

        ssize_t resRecvTCPsize = recvTCP(socketPrecedent, &tailleMessage, sizeof(tailleMessage));

        if (resSendTCPsize == 0 || resSendTCPsize == -1) {
            printf("\t❌ Pi : Arret de la boucle.\n");
            break;
        }
        printf("\tNombre d'octets reçus : %zd\n", resRecvTCPsize);
        printf("\tMessage reçu : '%d'\n", tailleMessage);

        printf("  -- 💑 Réception du couple --\n");
        struct Couple coupleRecu;
        ssize_t resRecvTCP = recvTCP(socketPrecedent, &coupleRecu, sizeof(coupleRecu));

        if (resRecvTCP == 0 || resRecvTCP == -1) {
            printf("\t❌ Pi : Arret de la boucle.\n");
            break;
        }

        printf("\tNombre d'octets reçus : %zd\n", resRecvTCP);
        printf("\tCouple reçu - Numero Pi : %d, Compteur Pi : %d\n", coupleRecu.numero_pi, coupleRecu.compteur_pi);

        // Etape 5 : Verifier numero Pi reçu :
        if (coupleRecu.numero_pi == numero_pi) {
    
            printf("🎉🎉 Le couple a effectué un tour complet 🎉🎉\n");
            printf("----- 🏆 Fin des echanges avec les voisins ------\n\n");

            // --- Etape 6 : Fermeture des socket
            closeSocket(socketSuivant);
            closeSocket(socketPrecedent);

            return coupleRecu.compteur_pi;
        }
        else {
            couple = coupleRecu;
            couple.compteur_pi++;
        }
    }

    return 0;
}

int main(int argc, char *argv[])
{
    /* Je passe en paramètre le numéro de port et le numero du processus.*/
    if (argc != 5)
    {
        printf("utilisation : %s IP_pconfig port_pconfig port_pi numero_pi\n", argv[0]);
        exit(1);
    }
    char *adresseIP_pconfig = argv[1];
    char *port_pconfig = argv[2];
    int port_pi = atoi(argv[3]);
    int numero_pi = atoi(argv[4]);

    struct sockaddr_in socket_suivant = initialisation(adresseIP_pconfig, port_pconfig, port_pi, numero_pi);

    int nombre_pi = traitementClassique(port_pi, numero_pi, socket_suivant);

    printf("🏁 Fin du programme, on sait qu'il y a %d Pi dans notre cercle\n", nombre_pi);

    return 0;
}