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

int *initialisation(char *adresseIPPconfig, char *portPconfig, struct sockaddr_in structAdresseServeurTCP, int numeroPi, int socketPiTCP, int *nombreVoisins)
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
    printf("\033[0;%dm[%d] 🏷️ Nommage de la socket réussi.\033[0m\n", (30 + numeroPi), numeroPi);

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
    printf("\033[0;%dm[%d] 📩 Recevoir nombre Accept et de Connect\033[0m\n", (30 + numeroPi), numeroPi);
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

        tabStuctureSocketVoisins[i] = structSocketVoisinTCP;
    }

    // Envois les connects pour chaque voisins
    for (int i = 0; i < compteurVoisins.nombreConnect; i++)
    {
        int socketClientTCP = creerSocket();

        // Connect a cette adresse
        connectionSocket(socketClientTCP, tabStuctureSocketVoisins[i]);

        printf("\033[0;%dm[%d] \t🛰️  Connection au voisin n°%d réussi.\033[0m\n", (30 + numeroPi), numeroPi, i);

        tabSocketsVoisins[i] = socketClientTCP;
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
        printf("\033[0;%dm[%d] 📥 Accepter les demandes de %d voisins\033[0m\n", (30 + numeroPi), numeroPi, compteurVoisins.nombreAccept);

    for (int i = 0; i < compteurVoisins.nombreAccept; i++)
    {
        struct sockaddr_in structSocketVoisinTCP;
        int socketClientTCP = accepterDemande(socketPiTCP, &structSocketVoisinTCP);
        tabSocketsVoisins[compteurVoisins.nombreConnect + i] = socketClientTCP;

        printf("\033[0;%dm[%d] \t👋 Voisin n°%d.\033[0m\n", (30 + numeroPi), numeroPi, (i + 1));
    }

    printf("\033[0;%dm[%d] 🏘️ Tous les voisins sont connectée !\033[0m\n\033[0m\n", (30 + numeroPi), numeroPi);

    close(socketPiTCP);
    free(tabStuctureSocketVoisins);

    return tabSocketsVoisins;
}

struct paramsDiffusionThread
{
    int idThread;
    int numeroPi;
    int socketVoisin;
    int message;
    int typeEnvois;
};

void *diffusionMessage(void *params)
{
    struct paramsDiffusionThread *args = (struct paramsDiffusionThread *)params;
    int idThread = args->idThread;
    int numeroPi = args->numeroPi;
    int socketVoisin = args->socketVoisin;
    int message = args->message;
    int typeEnvois = args->typeEnvois;

    if (typeEnvois == 0)
        printf("\033[0;%dm[%d][🔄]\t 💬 Envois du message '%d' sur la 🧦 n°%d du voisin n°%d du Pi n°%d.\033[0m\n", (30 + numeroPi), numeroPi, message, socketVoisin, idThread, numeroPi);
    else
        printf("\033[0;%dm[%d][➡️]\t 💬 Envois du message '%d' sur la 🧦 n°%d du voisin n°%d du Pi n°%d.\033[0m\n", (30 + numeroPi), numeroPi, message, socketVoisin, idThread, numeroPi);

    int tailleMessage = sizeof(message);
    ssize_t resSendTCPsize = sendTCP(socketVoisin, &tailleMessage, sizeof(tailleMessage));
    if (resSendTCPsize == 0 || resSendTCPsize == -1)
    {
        if (typeEnvois == 0)
            printf("\033[0;%dm[%d][🔄] ❌ Pi : Probleme lors de l'envois de la taille.\033[0m\n", (30 + numeroPi), numeroPi);
        else
            printf("\033[0;%dm[%d][➡️] ❌ Pi : Probleme lors de l'envois de la taille.\033[0m\n", (30 + numeroPi), numeroPi);
        exit(1);
    }

    ssize_t resSendTCP = sendTCP(socketVoisin, &message, tailleMessage);
    if (resSendTCP == 0 || resSendTCP == -1)
    {
        if (typeEnvois == 0)
            printf("\033[0;%dm[%d][🔄] ❌ Pi : Probleme lors de l'envois du message.\033[0m\n", (30 + numeroPi), numeroPi);
        else
            printf("\033[0;%dm[%d][➡️] ❌ Pi : Probleme lors de l'envois du message.\033[0m\n", (30 + numeroPi), numeroPi);
        exit(1);
    }

    return 0;
}

struct paramsEnvoisThread
{
    int intervaleTemps;
    int *tabSocketsVoisins;
    int nombreVoisins;
    int numeroPi;
};

void *envoisPeriodique(void *params)
{
    struct paramsEnvoisThread *args = (struct paramsEnvoisThread *)params;
    int intervaleTemps = args->intervaleTemps;
    int nombreVoisins = args->nombreVoisins;
    int *tabSocketsVoisins = args->tabSocketsVoisins;
    int numeroPi = args->numeroPi;

    printf("\033[0;%dm[%d][🔄] 📨 Envois d'un message aux %d voisins toutes les %d sec \033[0m\n", (30 + numeroPi), numeroPi, nombreVoisins, intervaleTemps);

    pthread_t *threads = malloc(sizeof(pthread_t) * nombreVoisins);
    int compteur = 0;
    while (1)
    {
        sleep(intervaleTemps);

        if (compteur == 0)
            printf("\033[0;%dm[%d][🔄] 🎯 Premier envois du message aux %d voisins :\033[0m\n", (30 + numeroPi), numeroPi, nombreVoisins);
        else
            printf("\033[0;%dm[%d][🔄] 🎯 %deme envois du message aux %d voisins :\033[0m\n", (30 + numeroPi), numeroPi, compteur, nombreVoisins);

        for (int i = 0; i < nombreVoisins; i++)
        {
            struct paramsDiffusionThread *paramsDiffusion = malloc(sizeof(struct paramsDiffusionThread));
            paramsDiffusion->idThread = i + 1;
            paramsDiffusion->numeroPi = numeroPi;
            paramsDiffusion->socketVoisin = tabSocketsVoisins[i];
            paramsDiffusion->message = rand() % 10;
            paramsDiffusion->typeEnvois = 0;

            if (pthread_create(&threads[i], NULL, diffusionMessage, paramsDiffusion) != 0)
            {
                perror("❌ Pi : problème à la creation du thread");
                free(paramsDiffusion);
                exit(1);
            }
        }

        for (int i = 0; i < nombreVoisins; i++)
        {
            pthread_join(threads[i], NULL);
        }

        compteur++;
        printf("\033[0;%dm[%d][🔄] ⏳ Attente de %d secondes...\033[0m\n", (30 + numeroPi), numeroPi, intervaleTemps);
    }
}

int estPresent(int element, int *tableau, int taille)
{
    for (int i = 0; i < taille; i++)
    {
        if (tableau[i] == element)
        {
            return 1; // L'élément est trouvé dans le tableau
        }
    }
    return 0; // L'élément n'est pas trouvé dans le tableau
}

void messageMultiplexe(int numeroPi, int *tabSocketsVoisins, int nombreVoisins, int intervaleTemps)
{
    printf("\033[0;%dm[%d] --- 🦑 Debut de la partie Multiplexage. --- \033[0m\n", (30 + numeroPi), numeroPi);

    // --- Etape 1 : Mise en place de l'envois periodique
    struct paramsEnvoisThread *paramsEnvois = malloc(sizeof(struct paramsEnvoisThread));
    paramsEnvois->intervaleTemps = intervaleTemps;
    paramsEnvois->nombreVoisins = nombreVoisins;
    paramsEnvois->tabSocketsVoisins = tabSocketsVoisins;
    paramsEnvois->numeroPi = numeroPi;

    pthread_t thread;
    if (pthread_create(&thread, NULL, envoisPeriodique, paramsEnvois) != 0)
    {
        perror("❌ Pi : problème à la creation du thread");
        free(paramsEnvois);
        exit(1);
    }

    // --- Etape 2 : Mise en place de la reception des messages
    int *tabMessagesRecus = malloc(sizeof(int) * nombreVoisins);
    int nombreMessagesRecus = 0;

    // --- Creation thread pour chaque voisin pour renvoyer les messages
    pthread_t *threads = malloc(sizeof(pthread_t) * nombreVoisins);
    struct paramsDiffusionThread *params = malloc(sizeof(struct paramsDiffusionThread));

    // --- Mise en place du multiplexage
    fd_set set, setCopie;
    FD_ZERO(&set);

    // --- On ajoute les sockets des voisins dans le tableau de multiplexage
    int max = 0;
    for (int i = 0; i < nombreVoisins; i++)
    {
        FD_SET(tabSocketsVoisins[i], &set);
        if (tabSocketsVoisins[i] > max)
            max = tabSocketsVoisins[i];
    }

    // -- Boucle de reception des messages
    int message;
    int tailleMessage;
    int compteur;
    while (1)
    {
        // --- On copie le tableau de multiplexage pour ne pas perdre les sockets qui ont recu un message
        setCopie = set;
        // --- On attend qu'un message (ou plusieur) soit recu
        printf("\033[0;%dm[%d] ⏳ Attente de reception d'un message sur une des sockets...\033[0m\n", (30 + numeroPi), numeroPi);

        int resSelect = select(max + 1, &setCopie, NULL, NULL, NULL);
        if (resSelect == -1)
        {
            perror("❌ Pi : problème avec le select :");
            exit(1);
        }
        if (resSelect == 1)
            printf("\033[0;%dm[%d] 📬 1 socket à reçue un message.\033[0m\n", (30 + numeroPi), numeroPi);
        else
            printf("\033[0;%dm[%d] 📬 %d sockets ont reçue un message;\033[0m\n", (30 + numeroPi), numeroPi, resSelect);

        // --- On parcours le tableau de multiplexage pour savoir quelle socket a recu un message
        compteur = 0;
        for (int descripteurSocket = 2; descripteurSocket <= max && compteur < resSelect; descripteurSocket++)
        {
            if (FD_ISSET(descripteurSocket, &setCopie))
            {
                if (compteur == 0)
                    printf("\033[0;%dm[%d]\t 📭 Reception du premier message sur la 🧦 n°%d.\033[0m\n", (30 + numeroPi), numeroPi, descripteurSocket);
                else
                    printf("\033[0;%dm[%d]\t 📭 Reception du %deme message sur la 🧦 n°%d.\033[0m\n", (30 + numeroPi), numeroPi, compteur, descripteurSocket);

                ssize_t resRecvTCPsize = recvTCP(descripteurSocket, &tailleMessage, sizeof(tailleMessage));
                if (resRecvTCPsize == 0 || resRecvTCPsize == -1)
                {
                    printf("\033[0;%dm[%d]❌ Pi : Probleme lors de la reception de la taille.\033[0m\n", (30 + numeroPi), numeroPi);
                    //exit(1); a faire
                }
                ssize_t resRecvTCP = recvTCP(descripteurSocket, &message, tailleMessage);
                if (resRecvTCP == 0 || resRecvTCP == -1)
                {
                    printf("\033[0;%dm[%d]❌ Pi : Probleme lors de la reception du message.\033[0m\n", (30 + numeroPi), numeroPi);
                    //exit(1); a faire
                }

                printf("\033[0;%dm[%d]\t 💬 Message reçus : '%d'.\033[0m\n", (30 + numeroPi), numeroPi, message);
                tabMessagesRecus[nombreMessagesRecus] = message;
                nombreMessagesRecus++;

                if (nombreMessagesRecus % nombreVoisins == 0)
                {
                    tabMessagesRecus = realloc(tabMessagesRecus, sizeof(int) * (nombreMessagesRecus + nombreVoisins));
                }

                // --- On renvois le message a tous les voisins sauf celui qui a recu le message si on avait pas deja recu ce message
                if (!estPresent(message, tabMessagesRecus, nombreMessagesRecus)) {
                    if (nombreMessagesRecus == 1)
                        printf("\033[0;%dm[%d] 📢 1er message reçus, on le diffuse aux voisins :\033[0m\n", (30 + numeroPi), numeroPi);
                    else
                        printf("\033[0;%dm[%d] 📢 %deme message reçus, on le diffuse aux voisins :\033[0m\n", (30 + numeroPi), numeroPi, nombreMessagesRecus);

                    for (int i = 0; i < nombreVoisins; i++)
                    {
                        if (tabSocketsVoisins[i] != descripteurSocket)
                        {
                            params->idThread = i + 1;
                            params->numeroPi = numeroPi;
                            params->socketVoisin = tabSocketsVoisins[i];
                            params->message = message;
                            params->typeEnvois = 1;

                            printf("\033[0;%dm[%d]\t 📡 Rediffusion du message sur la 🧦 n°%d.\033[0m\n", (30 + numeroPi), numeroPi, tabSocketsVoisins[i]);

                            if (pthread_create(&threads[i], NULL, diffusionMessage, params) != 0)
                            {
                                perror("❌ Pi : problème à la creation du thread");
                                free(params);
                                exit(1);
                            }
                        }
                    }
                }
                else {
                    printf("\033[0;%dm[%d] 🚫 Message déjà reçus, on ne le diffuse pas.\033[0m\n", (30 + numeroPi), numeroPi);
                }
            }
            compteur++;
        }
    }
    free(threads);

    printf("\033[0;%dm[%d] ✅ Fin d'affichage des voisins.\033[0m\n", (30 + numeroPi), numeroPi);
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

    // -- Etape 4 : Mettre la socket en ecoute
    ecouterDemande(socketPiTCP, 1000);

    int *tabSocketsVoisins;
    int nombreVoisins;

    tabSocketsVoisins = initialisation(adresseIPPconfig, portPconfig, structAdresseServeurTCP, numeroPi, socketPiTCP, &nombreVoisins);

    messageMultiplexe(numeroPi, tabSocketsVoisins, nombreVoisins, intervaleTemps);

    printf("\033[0;%dm[%d] 🏁 Fin du Pi n°%d !\033[0m\n", (30 + numeroPi), numeroPi, numeroPi);

    return 0;
}