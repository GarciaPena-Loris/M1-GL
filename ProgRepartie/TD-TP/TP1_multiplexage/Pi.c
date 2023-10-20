#include <netinet/in.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <pthread.h>
#include <string.h>

#include "fonctionTPC.h"

#define NOMBRE_MAX_DEMANDE 1000

struct infosPi {
    int numeroPi;
    struct sockaddr_in structSocketPi;
};

struct compteurVoisins {
    int nombreAccept;
    int nombreConnect;
};

int initialisation(char *adresseIPPconfig, char *portPconfig, struct sockaddr_in structAdresseServeurTCP, int numeroPi, int socketPiTCP, int* tabVoisins)
{
    // -- Etape 1 : Creation socker Pi UDP
    int socketPiUDP = socket(PF_INET, SOCK_DGRAM, 0);
    if (socketPiUDP == -1)
    {
        perror("❌ Pi : problème creation 🧦 :");
        exit(1);
    }
    printf("✅ Pi : Creation de la 🧦 Pi réussie.\n");

    // -- Etape 2 : Nommer la socket UDP
    struct sockaddr_in strctureSocketPiUDP;
    strctureSocketPiUDP.sin_family = AF_INET;
    strctureSocketPiUDP.sin_addr.s_addr = INADDR_ANY;
    strctureSocketPiUDP.sin_port = htons(0);

    int res = bind(socketPiUDP, (struct sockaddr*) &strctureSocketPiUDP, sizeof(strctureSocketPiUDP));
    if (res == -1) {
        perror("❌ Pi : probleme du bind :");
        exit(1);
    }
    printf("🏷️  Nommage de la socket réussi.\n");

    printf(" ----- 👋 Début des échanges avec Pconfig ----- \n");

    // -- Etape 1 : Designation de la socket pconfig
    struct sockaddr_in structureSocketPconfigUDP;
    structureSocketPconfigUDP.sin_family = AF_INET;
    structureSocketPconfigUDP.sin_addr.s_addr = inet_addr(adresseIPPconfig);
    structureSocketPconfigUDP.sin_port = htons(atoi(portPconfig));

    printf("\t✅ Pi : Désignation de la 🧦 Pconfig réussie.\n"); 

    // -- Etape 2 : Envois de la structure
    printf("\t -- 📨 Envois info Pi --\n");

    struct infosPi info;
    info.numeroPi = numeroPi;
    info.structSocketPi = structAdresseServeurTCP;

    printf("\t\t📨 Envois du numéro au serveur %s:%s\n", adresseIPPconfig, portPconfig);

    socklen_t sizeAdr = sizeof(struct sockaddr_in);

    int resSend = sendto(socketPiUDP, &info, sizeof(info),
     0, (struct sockaddr *)&structureSocketPconfigUDP, sizeAdr);
    if (resSend == -1)
    {
        perror("\t❌ Pi : problème avec le send to :"); 
        exit(1);
    }
    printf("\t\t✅ Nombre d'octets envoyés : %d\n", resSend);

    printf("\t-- Fin envoie numero Pi --\n\n");

    // -- Etape 3 : Recevoir nombre Accept et Connect
    printf("\t-- 📩 Recevoir nombre Accept et de Connect --\n");
    struct compteurVoisins compteurVoisins;

    struct sockaddr_in structSocketExpediteurUDP;
    ssize_t resRecv = recvfrom(socketPiUDP, &compteurVoisins, sizeof(compteurVoisins), 
        0, (struct sockaddr *)&structSocketExpediteurUDP, &sizeAdr);
    if (resRecv == -1)
    {
        perror("\t❌ Pi : problème avec le recvFrom :");
        exit(1);
    }

    printf("\t\t 🔗 Nombre de Connect a faire : %d\n", compteurVoisins.nombreConnect);
    printf("\t\t 📥 Nombre d'Accept a faire : %d\n", compteurVoisins.nombreAccept);

    printf("\t-- 🏆 Fin reception nombre Accept et de Connect --\n\n");

    // -- Etape 4 : Recevoir adresse de sockets des voisins
    printf("\t-- 🔗 Recevoir 🧦 des voisins pour se connecter --\n");

    tabVoisins = malloc(sizeof(int) * (compteurVoisins.nombreConnect + compteurVoisins.nombreAccept));

    // Pour chaque voisins auxquels je dois me connecter
    for (int i = 0; i < compteurVoisins.nombreConnect; i++) {
        struct sockaddr_in structSocketExpediteurUDP;
        struct sockaddr_in structSocketVoisinTCP;

        int resRecv = recvfrom(socketPiUDP, &structSocketVoisinTCP, sizeof(structSocketVoisinTCP), 
            0, (struct sockaddr *) &structSocketExpediteurUDP, &sizeAdr);
        if (resRecv == -1)
        {
            perror("\t❌ Pi : problème avec le recvFrom :");
            exit(1);
        }
        printf("\t\t✅ Nombre d'octets recu : %d\n", resRecv);

        // Créer une socket TCP
        int socketClientTCP = creerSocket();
        printf("\t\t🪛  Création de la socket réussie.\n");


        char *ipVoisin = inet_ntoa(structSocketVoisinTCP.sin_addr);
        int portVoisin = ntohs(structSocketVoisinTCP.sin_port);

        printf("\t\t🌐 Adresse voisin : %s:%d\n", ipVoisin, portVoisin);

        // Connect a cette adresse
        connectionSocket(socketClientTCP, structSocketVoisinTCP);
        printf("\t\t🛰️  Connection réussi.\n"); 
        printf("\t\t---\n");

        tabVoisins[i] = socketClientTCP;
    }

    printf("\t-- 🏆 Fin reception des 🧦 voisins --\n");

    // -- Etape 4 : Envois confirmation a  Pconfig
    printf("\t⏲️ Envois confirmation...\n");
    int conf = 1;
    resSend = sendto(socketPiUDP, &conf, sizeof(conf),
     0, (struct sockaddr *) &structureSocketPconfigUDP, sizeAdr);
    if (resSend == -1)
    {
        perror("\t❌ Pi : problème avec le send to :"); 
        exit(1);
    }
    printf("\t\t✅ Nombre d'octets envoyés : %d\n", resSend);


    // -- Etape 4 : Attente reception confirmation
    printf("\t⏳ Attente reception confirmation...\n");
    resRecv = recvfrom(socketPiUDP, &conf, sizeof(conf), 
        0, (struct sockaddr *) &structSocketExpediteurUDP, &sizeAdr);
    if (resRecv == -1)
    {
        perror("\t❌ Pi : problème avec le recvFrom :");
        exit(1);
    }
    printf("\t\t✅ Nombre d'octets recu : %zd\n", resRecv);


    int cls = close(socketPiUDP);
    if (cls == -1)
    {
        perror("❌ Pi : problème avec le close :");
        exit(1);
    }
    printf("\t🚪 Fermeture de la 🧦 UDP réussi.\n");

    printf("----- 👋 Fin des échanges avec Pconfig -----\n\n\n");

    // -- Etape 5 : Accepter demande des voisins

    printf("----- 📥 Accepter les demandes des voisins -----\n");

    for (int i = 0; i < compteurVoisins.nombreAccept; i++) {
        struct sockaddr_in structSocketVoisinTCP;
        int socketClientTCP = accepterDemande(socketPiTCP, &structSocketVoisinTCP);
        tabVoisins[compteurVoisins.nombreConnect + (i+1)] = socketClientTCP;

        printf("\t👋 Voisin n°%d accepté.\n", i);        
    }

    printf("----- 🏆 Fin d'acceptation des voisins -----\n\n");

    return compteurVoisins.nombreConnect + compteurVoisins.nombreAccept;
}


struct paramsFonctionThread {
  int idThread;
  struct sockaddr_in voisin;
};

void* diffusion_message(void* params) {

    struct paramsFonctionThread* args = (struct paramsFonctionThread *) params;
    int idThread = args->idThread;
    struct sockaddr_in voisin = args->voisin;

    char* message = "Bonjour je suis un message super sympa ! 💭";

    printf("--- 💬 Envois du message au voisin n°%d ---\n", idThread);
    printf("-- 📏 Envoi de la taille du message --\n");

    /*
    int tailleMessage = strlen(message);
    ssize_t resSendTCPsize = sendTCP(voisin, &tailleMessage, sizeof(tailleMessage));
    if (resSendTCPsize == 0 || resSendTCPsize == -1) {
        printf("\t❌ Pi : Arret de la boucle.\n");
    }

    printf("\tNombre d'octets envoyés : %zd\n", resSendTCPsize);
    printf("\tMessage envoyé : '%d'\n", tailleMessage);

    printf("  -- 💑 Envoi du couple --\n");
    ssize_t resSendTCP = sendTCP(socketSuivant, &couple, sizeof(couple));
    if (resSendTCP == 0 || resSendTCP == -1) {
        printf("\t❌ Pi : Arret de la boucle.\n");
    } 
    printf("\tCouple envoyé - Numero Pi : %d, Compteur Pi : %d\n", couple.numeroPi, couple.compteur_pi);
    printf("\tNombre d'octets envoyés : %zd\n", resSendTCP);
    */

    printf("----- 🏆 Fin envoie du couple au suivant ------\n\n");

}

void traitementClassique(int numeroPi, struct sockaddr_in* tabVoisins, int nombreVoisins, int intervaleTemps)
{
    
    printf("----- 📨 Envois d'un message aux %d voisins du Pi n°%d toutes les %d sec-----\n", nombreVoisins, numeroPi, intervaleTemps);

    while(1) {
        sleep(intervaleTemps);

        // ---- Envois du message a tout les voisins
        pthread_t threads[nombreVoisins];
        for (int i = 0; i < nombreVoisins; i++) {

            struct sockaddr_in voisin = tabVoisins[i];
            struct paramsFonctionThread *params = malloc(sizeof(struct paramsFonctionThread));
            params->idThread = i;
            params->voisin = voisin;

            if (pthread_create(&threads[i], NULL, 
                diffusion_message, params) != 0){
                perror("❌ Pi : problème à la creation du thread");
                exit(1);
            }

            if (i < nombreVoisins-1)
                printf("\t-----\n");
        }

        for (int i = 0; i < nombreVoisins; i++) {
            pthread_join(threads[i], NULL);
        }
    }

    printf("✅ Fin d'affichage des voisins.\n");

}

int main(int argc, char *argv[])
{
    /* Je passe en paramètre le numéro de port et le numero du processus.*/
    if (argc != 6)
    {
        printf("utilisation : %s IPPconfig portPconfig intervaleTemps portPi numeroPi\n", argv[0]);
        exit(1);
    }
    char *adresseIPPconfig = argv[1];
    char *portPconfig = argv[2];
    int intervaleTemps = atoi(argv[3]);
    int portPi = atoi(argv[4]);
    int numeroPi = atoi(argv[5]);

    // --- Etape 1 : Creation et mise en ecoute de la socket
    int socketPiTCP = creerSocket();

    struct sockaddr_in structAdresseServeurTCP = nommerSocket(socketPiTCP, portPi);

    ecouterDemande(socketPiTCP, NOMBRE_MAX_DEMANDE);

    int* tabVoisins = NULL;
    int nombreVoisins = initialisation(adresseIPPconfig, portPconfig, structAdresseServeurTCP, numeroPi, socketPiTCP, tabVoisins);


    //traitementClassique(numeroPi, tabVoisins, nombreVoisins, intervaleTemps);

    printf("🏁 Fin du Pi n°%d !\n", numeroPi);

    return 0;
}