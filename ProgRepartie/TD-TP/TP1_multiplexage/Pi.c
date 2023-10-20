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

int initialisation(char *adresseIPPconfig, char *portPconfig, struct sockaddr_in structAdresseServeurTCP, int numeroPi, int socketPiTCP, int *nombreVoisins)
{
    // -- Etape 1 : Creation socker Pi UDP
    int socketPiUDP = socket(PF_INET, SOCK_DGRAM, 0);
    if (socketPiUDP == -1)
    {
        perror("❌ Pi : problème creation 🧦 :");
        exit(1);
    }
    printf("\033[0;%dm[%d] ✅ Pi : Creation de la 🧦 Pi réussie.\033[0m\n", (30 + numeroPi), numeroPi);

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
    printf("\033[0;%dm[%d] 🏷️  Nommage de la socket réussi.\033[0m\n", (30 + numeroPi), numeroPi);

    // -- Etape 1 : Designation de la socket pconfig
    struct sockaddr_in structureSocketPconfigUDP;
    structureSocketPconfigUDP.sin_family = AF_INET;
    structureSocketPconfigUDP.sin_addr.s_addr = inet_addr(adresseIPPconfig);
    structureSocketPconfigUDP.sin_port = htons(atoi(portPconfig));

    // -- Etape 2 : Envois de la structure

    struct infosPi info;
    info.numeroPi = numeroPi;
    info.structSocketPi = structAdresseServeurTCP;

    printf("\033[0;%dm[%d] 📨 Envois de son numéro (%d) au serveur %s:%s\033[0m\n", (30 + numeroPi), numeroPi, numeroPi, adresseIPPconfig, portPconfig);

    socklen_t sizeAdr = sizeof(struct sockaddr_in);

    int resSend = sendto(socketPiUDP, &info, sizeof(info),
                         0, (struct sockaddr *)&structureSocketPconfigUDP, sizeAdr);
    if (resSend == -1)
    {
        perror("❌ Pi : problème avec le send to :");
        exit(1);
    }

    // -- Etape 3 : Recevoir nombre Accept et Connect
    printf("\033[0;%dm[%d]📩 Recevoir nombre Accept et de Connect\033[0m\n", (30 + numeroPi), numeroPi);
    struct compteurVoisins compteurVoisins;

    struct sockaddr_in structSocketExpediteurUDP;
    ssize_t resRecv = recvfrom(socketPiUDP, &compteurVoisins, sizeof(compteurVoisins),
                               0, (struct sockaddr *)&structSocketExpediteurUDP, &sizeAdr);
    if (resRecv == -1)
    {
        perror("❌ Pi : problème avec le recvFrom :");
        exit(1);
    }

    printf("\033[0;%dm[%d] \t 🔗 Nombre de Connect a faire : %d\033[0m\n", (30 + numeroPi), numeroPi, compteurVoisins.nombreConnect);
    printf("\033[0;%dm[%d] \t 📥 Nombre d'Accept a faire : %d\033[0m\n", (30 + numeroPi), numeroPi, compteurVoisins.nombreAccept);

    *nombreVoisins = (int)compteurVoisins.nombreAccept + compteurVoisins.nombreConnect;

    // -- Etape 4 : Recevoir adresse de sockets des voisins
    if (compteurVoisins.nombreConnect > 0)
        printf("\033[0;%dm[%d] 🔗 Reception et connexion des %d voisins\033[0m\n", (30 + numeroPi), numeroPi, compteurVoisins.nombreConnect);

    int *tabSocketsVoisins = (int *)malloc(*nombreVoisins * sizeof(int));
    struct sockaddr_in *tabStuctureSocketVoisins = malloc(sizeof(struct sockaddr_in) * (compteurVoisins.nombreConnect + compteurVoisins.nombreAccept));

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

        // char *ipVoisin = inet_ntoa(structSocketVoisinTCP.sin_addr);
        // int portVoisin = ntohs(structSocketVoisinTCP.sin_port);
        // printf("\033[0;%dm[%d] \t🌐  Reception de la stuct du voisin n°%d (%s:%d) réussi.\033[0m\n", (30 + numeroPi), numeroPi, i, ipVoisin, portVoisin);

        tabStuctureSocketVoisins[i] = structSocketVoisinTCP;
    }

    // Envois les connects pour chaque voisins
    for (int i = 0; i < compteurVoisins.nombreConnect; i++)
    {
        int socketClientTCP = creerSocket();

        // Connect a cette adresse
        connectionSocket(socketClientTCP, tabStuctureSocketVoisins[i]);

        printf("\033[0;%dm[%d] \t🛰️  Connection au voisin n°%d réussi.\033[0m\n", (30 + numeroPi), numeroPi, i);
    }

    if (compteurVoisins.nombreConnect > 0)
        printf("\033[0;%dm[%d] 🏆 Fin reception des 🧦 voisins\033[0m\n", (30 + numeroPi), numeroPi);

    // -- Etape 4 : Envois confirmation a Pconfig
    printf("\033[0;%dm[%d] ⏲️ Envois confirmation à Pconfig.\033[0m\n", (30 + numeroPi), numeroPi);
    char conf = 'c';
    resSend = sendto(socketPiUDP, &conf, sizeof(conf),
                     0, (struct sockaddr *)&structureSocketPconfigUDP, sizeAdr);
    if (resSend == -1)
    {
        perror("❌ Pi : problème avec le send to :");
        exit(1);
    }

    // -- Etape 4 : Attente reception confirmation
    printf("\033[0;%dm[%d] ⏳ Attente reception confirmation...\033[0m\n", (30 + numeroPi), numeroPi);
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
        printf("\033[0;%dm[%d] 📥 Accepter les demandes des %d voisins\033[0m\n", (30 + numeroPi), numeroPi, compteurVoisins.nombreAccept);

    for (int i = 0; i < compteurVoisins.nombreAccept; i++)
    {
        struct sockaddr_in structSocketVoisinTCP;
        int socketClientTCP = accepterDemande(socketPiTCP, &structSocketVoisinTCP);
        tabSocketsVoisins[compteurVoisins.nombreConnect + (i + 1)] = socketClientTCP;

        printf("\033[0;%dm[%d] \t👋 Voisin n°%d du Pi %d accepté.\033[0m\n", (30 + numeroPi), numeroPi, numeroPi, i);
    }

    if (compteurVoisins.nombreAccept > 0)
        printf("\033[0;%dm[%d] 🏆 Fin d'acceptation des voisins\033[0m\n\033[0m\n", (30 + numeroPi), numeroPi);

    printf("\033[0;%dm[%d] 🥳🎉🎉🎉 Tous les voisins sont connectée !! 🎉🎉🎉🥳\033[0m\n\033[0m\n", (30 + numeroPi), numeroPi);

    return tabSocketsVoisins;
}

struct paramsFonctionThread
{
    int idThread;
    int numeroPi;
    int socketVoisin;
};

void *diffusion_message(void *params)
{
    struct paramsFonctionThread *args = (struct paramsFonctionThread *)params;
    int idThread = args->idThread;
    int numeroPi = args->numeroPi;
    int socketVoisin = args->socketVoisin;

    char *message = "Bonjour je suis un message super sympa ! 💭";

    printf("\033[0;%dm[%d] --- 💬 Envois du message au voisin n°%d ---\033[0m\n", (30 + numeroPi), numeroPi, idThread);
    printf("\033[0;%dm[%d] -- 📏 Envoi de la taille du message --\033[0m\n", (30 + numeroPi), numeroPi);

    int tailleMessage = strlen(message);
    ssize_t resSendTCPsize = sendTCP(socketVoisin, &tailleMessage, sizeof(tailleMessage));
    if (resSendTCPsize == 0 || resSendTCPsize == -1)
    {
        printf("\033[0;%dm[%d] ❌ Pi : Probleme lors du sendTCP.\033[0m\n", (30 + numeroPi), numeroPi);
    }

    printf("\033[0;%dm[%d] \tMessage envoyé : '%d'\033[0m\n", (30 + numeroPi), numeroPi, tailleMessage);

    printf("\033[0;%dm[%d] -- 💬 Envois du message  --\033[0m\n", (30 + numeroPi), numeroPi);
    ssize_t resSendTCP = sendTCP(socketVoisin, message, tailleMessage);
    if (resSendTCP == 0 || resSendTCP == -1)
    {
        printf("\033[0;%dm[%d] ❌ Pi : Probleme lors du sendTCP.\033[0m\n", (30 + numeroPi), numeroPi);
    }
    printf("\033[0;%dm[%d] \tMessage envoyé : '%s'\033[0m\n", (30 + numeroPi), numeroPi, message);

    printf("\033[0;%dm[%d] --- 🏆 Fin envoie du message au voisin n°%d ---\033[0m\n", (30 + numeroPi), numeroPi, idThread);
}

void messageMultiplexe(int numeroPi, int *tabSocketsVoisins, int nombreVoisins, int intervaleTemps)
{

    printf("\033[0;%dm[%d] ----- 📨 Envois d'un message aux %d voisins toutes les %d sec -----\033[0m\n", (30 + numeroPi), numeroPi, nombreVoisins, intervaleTemps);

    int compteur = 0;
    while (1)
    {
        sleep(intervaleTemps);

        // ---- Envois du message a tout les voisins
        pthread_t *threads = malloc(sizeof(pthread_t) * nombreVoisins);
        struct paramsFonctionThread *params = malloc(sizeof(struct paramsFonctionThread));
        printf("\033[0;%dm[%d] Alo 0 : %d \033[0m\n", (30 + numeroPi), numeroPi, nombreVoisins);
        printf("\033[0;%dm[%d] taille %d \033[0m\n", (30 + numeroPi), numeroPi, sizeof(tabSocketsVoisins));

        for (int i = 0; i < nombreVoisins; i++)
        {
            printf("\033[0;%dm[%d] Alo 1 : %d \033[0m\n", (30 + numeroPi), numeroPi, i);
            printf("\033[0;%dm[%d] socket : %d \033[0m\n", (30 + numeroPi), numeroPi, tabSocketsVoisins[i]);
            int socketVoisin = tabSocketsVoisins[i];
            params->idThread = i;
            params->numeroPi = numeroPi;
            params->socketVoisin = socketVoisin;

            printf("\033[0;%dm[%d] Alo 2 : %d \033[0m\n", (30 + numeroPi), numeroPi, i);

            if (pthread_create(&threads[i], NULL,
                               diffusion_message, params) != 0)
            {
                perror("❌ Pi : problème à la creation du thread");
                free(params);
                exit(1);
            }
            printf("\033[0;%dm[%d] Alo 3 : %d \033[0m\n", (30 + numeroPi), numeroPi, i);

            if (i < nombreVoisins - 1)
                printf("\033[0;%dm[%d] -----\033[0m\n", (30 + numeroPi), numeroPi);
        }
        printf("\033[0;%dm[%d] Alo 4 \033[0m\n", (30 + numeroPi), numeroPi);

        for (int i = 0; i < nombreVoisins; i++)
        {
            pthread_join(threads[i], NULL);
        }
        free(threads);

        compteur++;
        if (compteur == 3)
            break;
    }

    printf("\033[0;%dm[%d] ✅ Fin d'affichage des voisins.\033[0m\n", (30 + numeroPi), numeroPi);
}

int main(int argc, char *argv[])
{
    /* Je passe en paramètre le numéro de port et le numero du processus.*/
    if (argc != 5)
    {
        printf("utilisation : %s IPPconfig portPconfig intervaleTemps numeroPi", argv[0]);
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

    // -- Etape 4 : Mettre la socket en ecoute
    ecouterDemande(socketPiTCP, 1000);

    int *tabSocketsVoisins;
    int nombreVoisins;

    tabSocketsVoisins = initialisation(adresseIPPconfig, portPconfig, structAdresseServeurTCP, numeroPi, socketPiTCP, &nombreVoisins);

    messageMultiplexe(numeroPi, tabSocketsVoisins, nombreVoisins, intervaleTemps);

    printf("\033[0;%dm[%d] 🏁 Fin du Pi n°%d !\033[0m\n", (30 + numeroPi), numeroPi, numeroPi);

    return 0;
}