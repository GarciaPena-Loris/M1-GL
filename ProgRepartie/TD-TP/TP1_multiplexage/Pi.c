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

struct infosPi
{
    int numeroPi;
    struct sockaddr_in structSocketPi;
};

struct compteurVoisins
{
    int nombreAccept;
    int nombreConnect;
};

int initialisation(char *adresseIPPconfig, char *portPconfig, struct sockaddr_in structAdresseServeurTCP, int numeroPi, int socketPiTCP, int *tabSocketsVoisins)
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

    int res = bind(socketPiUDP, (struct sockaddr *)&strctureSocketPiUDP, sizeof(strctureSocketPiUDP));
    if (res == -1)
    {
        perror("❌ Pi : probleme du bind :");
        exit(1);
    }
    printf("🏷️  Nommage de la socket réussi.\n");

    // -- Etape 1 : Designation de la socket pconfig
    struct sockaddr_in structureSocketPconfigUDP;
    structureSocketPconfigUDP.sin_family = AF_INET;
    structureSocketPconfigUDP.sin_addr.s_addr = inet_addr(adresseIPPconfig);
    structureSocketPconfigUDP.sin_port = htons(atoi(portPconfig));

    // -- Etape 2 : Envois de la structure

    struct infosPi info;
    info.numeroPi = numeroPi;
    info.structSocketPi = structAdresseServeurTCP;

    printf("📨 Envois de son numéro (%d) au serveur %s:%s\n", numeroPi, adresseIPPconfig, portPconfig);

    socklen_t sizeAdr = sizeof(struct sockaddr_in);

    int resSend = sendto(socketPiUDP, &info, sizeof(info),
                         0, (struct sockaddr *)&structureSocketPconfigUDP, sizeAdr);
    if (resSend == -1)
    {
        perror("❌ Pi : problème avec le send to :");
        exit(1);
    }

    // -- Etape 3 : Recevoir nombre Accept et Connect
    printf("-- 📩 Recevoir nombre Accept et de Connect --\n");
    struct compteurVoisins compteurVoisins;

    struct sockaddr_in structSocketExpediteurUDP;
    ssize_t resRecv = recvfrom(socketPiUDP, &compteurVoisins, sizeof(compteurVoisins),
                               0, (struct sockaddr *)&structSocketExpediteurUDP, &sizeAdr);
    if (resRecv == -1)
    {
        perror("❌ Pi : problème avec le recvFrom :");
        exit(1);
    }

    printf("\t 🔗 Nombre de Connect a faire : %d\n", compteurVoisins.nombreConnect);
    printf("\t 📥 Nombre d'Accept a faire : %d\n", compteurVoisins.nombreAccept);

    // -- Etape 4 : Mettre la socket en ecoute
    ecouterDemande(socketPiTCP, compteurVoisins.nombreAccept);

    // -- Etape 4 : Recevoir adresse de sockets des voisins
    if (compteurVoisins.nombreConnect > 0)
        printf("-- 🔗 Reception et connexion des %d voisins --\n", compteurVoisins.nombreConnect);

    tabSocketsVoisins = malloc(sizeof(int) * (compteurVoisins.nombreConnect + compteurVoisins.nombreAccept));

    // Pour chaque voisins auxquels je dois me connecter
    for (int i = 0; i < compteurVoisins.nombreConnect; i++)
    {
        struct sockaddr_in structSocketExpediteurUDP;
        struct sockaddr_in structSocketVoisinTCP;

        int resRecv = recvfrom(socketPiUDP, &structSocketVoisinTCP, sizeof(structSocketVoisinTCP),
                               0, (struct sockaddr *)&structSocketExpediteurUDP, &sizeAdr);
        if (resRecv == -1)
        {
            perror("❌ Pi : problème avec le recvFrom :");
            exit(1);
        }

        // Créer une socket TCP
        int socketClientTCP = creerSocket();

        char *ipVoisin = inet_ntoa(structSocketVoisinTCP.sin_addr);
        int portVoisin = ntohs(structSocketVoisinTCP.sin_port);

        // Connect a cette adresse
        connectionSocket(socketClientTCP, structSocketVoisinTCP);
        printf("🛰️  Connection du voisin %s:%d réussi.\n", ipVoisin, portVoisin);

        tabSocketsVoisins[i] = socketClientTCP;
    }

    if (compteurVoisins.nombreConnect > 0)
        printf("-- 🏆 Fin reception des 🧦 voisins --\n");

    // -- Etape 4 : Envois confirmation a Pconfig
    printf("⏲️ Envois confirmation à Pconfig.\n");
    int conf = 1;
    resSend = sendto(socketPiUDP, &conf, sizeof(conf),
                     0, (struct sockaddr *)&structureSocketPconfigUDP, sizeAdr);
    if (resSend == -1)
    {
        perror("❌ Pi : problème avec le send to :");
        exit(1);
    }

    // -- Etape 4 : Attente reception confirmation
    printf("⏳ Attente reception confirmation...\n");
    resRecv = recvfrom(socketPiUDP, &conf, sizeof(conf),
                       0, (struct sockaddr *)&structSocketExpediteurUDP, &sizeAdr);
    if (resRecv == -1)
    {
        perror("❌ Pi : problème avec le recvFrom :");
        exit(1);
    }

    int cls = close(socketPiUDP);
    if (cls == -1)
    {
        perror("❌ Pi : problème avec le close :");
        exit(1);
    }

    // -- Etape 5 : Accepter demande des voisins
    if (compteurVoisins.nombreAccept > 0)
        printf("📥 Accepter les demandes des %d voisins\n", compteurVoisins.nombreAccept);

    for (int i = 0; i < compteurVoisins.nombreAccept; i++)
    {
        struct sockaddr_in structSocketVoisinTCP;
        int socketClientTCP = accepterDemande(socketPiTCP, &structSocketVoisinTCP);
        tabSocketsVoisins[compteurVoisins.nombreConnect + (i + 1)] = socketClientTCP;

        printf("\t👋 Voisin n°%d du Pi %d accepté.\n", numeroPi, i);
    }

    if (compteurVoisins.nombreAccept > 0)
        printf("🏆 Fin d'acceptation des voisins\n\n");

    return compteurVoisins.nombreConnect + compteurVoisins.nombreAccept;
}

struct paramsFonctionThread
{
    int idThread;
    int socketVoisin;
};

void *diffusion_message(void *params)
{

    struct paramsFonctionThread *args = (struct paramsFonctionThread *)params;
    int idThread = args->idThread;
    int socketVoisin = args->socketVoisin;

    char *message = "Bonjour je suis un message super sympa ! 💭";

    printf("--- 💬 Envois du message au voisin n°%d ---\n", idThread);
    printf("-- 📏 Envoi de la taille du message --\n");

    int tailleMessage = strlen(message);
    ssize_t resSendTCPsize = sendTCP(socketVoisin, &tailleMessage, sizeof(tailleMessage));
    if (resSendTCPsize == 0 || resSendTCPsize == -1) {
        printf("❌ Pi : Probleme lors du sendTCP.\n");
    }

    printf("\tMessage envoyé : '%d'\n", tailleMessage);

    printf("-- 💬 Envois du message  --\n");
    ssize_t resSendTCP = sendTCP(socketVoisin, message, sizeof(message));
    if (resSendTCP == 0 || resSendTCP == -1) {
        printf("❌ Pi : Probleme lors du sendTCP.\n");
    }
        printf("\tMessage envoyé : '%s'\n", message);

    printf("--- 🏆 Fin envoie du message au voisin n°%d ---\n", idThread);
}

void messageMultiplexe(int numeroPi, int *tabSocketsVoisins, int nombreVoisins, int intervaleTemps)
{

    printf("----- 📨 Envois d'un message aux %d voisins du Pi n°%d toutes les %d sec-----\n", nombreVoisins, numeroPi, intervaleTemps);

    while (1)
    {
        sleep(intervaleTemps);

        // ---- Envois du message a tout les voisins
        pthread_t threads[nombreVoisins];
        for (int i = 0; i < nombreVoisins; i++)
        {

            int socketVoisin = tabSocketsVoisins[i];
            struct paramsFonctionThread *params = malloc(sizeof(struct paramsFonctionThread));
            params->idThread = i;
            params->socketVoisin = socketVoisin;

            if (pthread_create(&threads[i], NULL,
                               diffusion_message, params) != 0)
            {
                perror("❌ Pi : problème à la creation du thread");
                exit(1);
            }

            if (i < nombreVoisins - 1)
                printf("-----\n");
        }

        for (int i = 0; i < nombreVoisins; i++)
        {
            pthread_join(threads[i], NULL);
        }
    }

    printf("✅ Fin d'affichage des voisins.\n");
}

int main(int argc, char *argv[])
{
    /* Je passe en paramètre le numéro de port et le numero du processus.*/
    if (argc != 5)
    {
        printf("utilisation : %s IPPconfig portPconfig intervaleTemps numeroPi\n", argv[0]);
        exit(1);
    }
    char *adresseIPPconfig = argv[1];
    char *portPconfig = argv[2];
    int intervaleTemps = atoi(argv[3]);
    int numeroPi = atoi(argv[4]);

    // --- Etape 1 : Creation et mise en ecoute de la socket
    int socketPiTCP = creerSocket();

    nommerSocket(socketPiTCP, 0);

    struct sockaddr_in structAdresseServeurTCP;
    socklen_t size = sizeof(structAdresseServeurTCP);

    if (getsockname(socketPiTCP, (struct sockaddr *)&structAdresseServeurTCP, &size) == -1)
    {
        perror("❌ Pi : Erreur lors de la récupération de l'adresse de la socket");
        exit(EXIT_FAILURE);
    }

    int *tabSocketsVoisins = NULL;
    int nombreVoisins = initialisation(adresseIPPconfig, portPconfig, structAdresseServeurTCP, numeroPi, socketPiTCP, tabSocketsVoisins);

    messageMultiplexe(numeroPi, tabSocketsVoisins, nombreVoisins, intervaleTemps);

    printf("🏁 Fin du Pi n°%d !\n", numeroPi);

    return 0;
}