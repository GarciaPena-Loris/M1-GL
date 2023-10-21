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
        printf("\033[0;%dm[%d] 📥 Accepter les demandes des %d voisins\033[0m\n", (30 + numeroPi), numeroPi, compteurVoisins.nombreAccept);

    for (int i = 0; i < compteurVoisins.nombreAccept; i++)
    {
        struct sockaddr_in structSocketVoisinTCP;
        int socketClientTCP = accepterDemande(socketPiTCP, &structSocketVoisinTCP);
        tabSocketsVoisins[compteurVoisins.nombreConnect + i] = socketClientTCP;

        printf("\033[0;%dm[%d] \t👋 Voisin n°%d du Pi %d accepté.\033[0m\n", (30 + numeroPi), numeroPi, numeroPi, i);
    }

    if (compteurVoisins.nombreAccept > 0)
        printf("\033[0;%dm[%d] 🏆 Fin d'acceptation des voisins\033[0m\n\033[0m\n", (30 + numeroPi), numeroPi);

    printf("\033[0;%dm[%d] 🥳🎉🎉🎉 Tous les voisins sont connectée !! 🎉🎉🎉🥳\033[0m\n\033[0m\n", (30 + numeroPi), numeroPi);

    close(socketPiTCP);

    return tabSocketsVoisins;
}

struct paramsDiffusionThread
{
    int idThread;
    int numeroPi;
    int socketVoisin;
    int message;
};

void *diffusionMessage(void *params)
{
    struct paramsDiffusionThread *args = (struct paramsDiffusionThread *)params;
    int idThread = args->idThread;
    int numeroPi = args->numeroPi;
    int socketVoisin = args->socketVoisin;
    int message = args->message;
    // char *message = "Bonjour je suis un message super sympa ! 💭";

    printf("\033[0;%dm[%d][%d] --- 💬 Envois du message au voisin n°%d ---\033[0m\n", (30 + numeroPi), numeroPi, idThread, idThread);
    printf("\033[0;%dm[%d][%d] -- 📏 Envoi de la taille du message --\033[0m\n", (30 + numeroPi), numeroPi, idThread);

    int tailleMessage = sizeof(message);
    ssize_t resSendTCPsize = sendTCP(socketVoisin, &tailleMessage, sizeof(tailleMessage));
    if (resSendTCPsize == 0 || resSendTCPsize == -1)
    {
        printf("\033[0;%dm[%d][%d] ❌ Pi : Probleme lors du sendTCP.\033[0m\n", (30 + numeroPi), numeroPi, idThread);
        exit(1);
    }

    printf("\033[0;%dm[%d][%d] \tMessage envoyé : '%d'\033[0m\n", (30 + numeroPi), numeroPi, idThread, tailleMessage);

    printf("\033[0;%dm[%d][%d] -- 💬 Envois du message  --\033[0m\n", (30 + numeroPi), numeroPi, idThread);
    ssize_t resSendTCP = sendTCP(socketVoisin, &message, tailleMessage);
    if (resSendTCP == 0 || resSendTCP == -1)
    {
        printf("\033[0;%dm[%d][%d] ❌ Pi : Probleme lors du sendTCP.\033[0m\n", (30 + numeroPi), numeroPi, idThread);
        exit(1);
    }
    printf("\033[0;%dm[%d][%d] \tMessage envoyé : '%d'\033[0m\n", (30 + numeroPi), numeroPi, idThread, message);

    printf("\033[0;%dm[%d][%d] --- 🏆 Fin envoie du message au voisin n°%d ---\033[0m\n", (30 + numeroPi), numeroPi, idThread, idThread);
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

    pthread_t *threads = malloc(sizeof(pthread_t) * nombreVoisins);
    while (1)
    {
        sleep(intervaleTemps);

        for (int i = 0; i < nombreVoisins; i++)
        {
            struct paramsDiffusionThread *paramsDiffusion = malloc(sizeof(struct paramsDiffusionThread));
            paramsDiffusion->idThread = i + 1;
            paramsDiffusion->numeroPi = numeroPi;
            paramsDiffusion->socketVoisin = tabSocketsVoisins[i];
            paramsDiffusion->message = numeroPi; // Temporaire

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
    }
}

void messageMultiplexe(int numeroPi, int *tabSocketsVoisins, int nombreVoisins, int intervaleTemps)
{

    printf("\033[0;%dm[%d] ----- 📨 Envois d'un message aux %d voisins toutes les %d sec -----\033[0m\n", (30 + numeroPi), numeroPi, nombreVoisins, intervaleTemps);
    // Appel de la fonction envoisPeriodique
    struct paramsEnvoisThread *paramsEnvois = malloc(sizeof(struct paramsEnvoisThread));
    paramsEnvois->intervaleTemps = intervaleTemps;
    paramsEnvois->nombreVoisins = nombreVoisins;
    paramsEnvois->tabSocketsVoisins = tabSocketsVoisins;
    paramsEnvois->numeroPi = numeroPi;

    pthread_t threads;

    if (pthread_create(&threads, NULL, envoisPeriodique, paramsEnvois) != 0)
    {
        perror("❌ Pi : problème à la creation du thread");
        free(paramsEnvois);
        exit(1);
    }
    pthread_join(threads, NULL);

    int compteur = 0;
    while (1)
    {

        // ---- Envois du message a tout les voisins
        pthread_t *threads = malloc(sizeof(pthread_t) * nombreVoisins);
        struct paramsDiffusionThread *params = malloc(sizeof(struct paramsDiffusionThread));

        fd_set set, settmp, setthr; // on créer tableau multiplexage
        FD_ZERO(&set);              // on l'initialise a 0
        int max = 0;
        for (int i = 0; i < nombreVoisins; i++) // on met les socket voisin à scruter
        {
            FD_SET(tabSocketsVoisins[i], &set);
            if (tabSocketsVoisins[i] > max)
            {
                max = tabSocketsVoisins[i];
            }
        }
        int message;
        int taillemessage;
        struct timeval tempsMax;
        tempsMax.tv_sec = 11;
        tempsMax.tv_usec = 0;

        while (1)
        {
            settmp = set;
            int slct = select(max + 1, &settmp, NULL, NULL, *tempsMax); // on attend un événement sur une socket
            if (slct == 0)
            {
                printf("Aucun message n'a été reçu\n");
            }
            else if (slct == -1)
            {
                printf("Erreur lors du select\n");
            }
            printf("%d socket ont reçu un message\n", slct);

            for (int df = 2; df <= max; df++) // on commence a 2 car on ne veut pas traiter la socket 0 et 1
            {
                if (FD_ISSET(df, &settmp)) // la socket df a recu un message
                {
                    printf("  --Recevoir la taille du message--\n");

                    int tailleMessage;
                    recvTCP(df, &tailleMessage, sizeof(tailleMessage));

                    printf("\tMessage: '%d'\n", tailleMessage);

                    printf("  --Recevoir le message de taille %d--\n", tailleMessage);
                    recvTCP(df, message, tailleMessage);

                    printf("\tMessage : '%d'\n", message);
                    setthr = settmp; // on copie encore le tableau de multiplexage pour traiter ce message car on va mettre a 0 les socket qui n'ont pas recu ce message
                                     // mais on doit garder en mémoire les autres socket qui ont recu un autre message
                    for (int z = df + 1; z <= max; z++)
                    {
                        if (FD_ISSET(z, &settmp))
                        {
                            FD_CLR(z, &setthr);
                        } // on met a 0 toutes les socket qui ne sont pas celle qui vient de recevoir le message
                    }     // on commence a df+1 car on veut garder a 1 la socket qui a recu le message pour ne pas renvoyer au même

                    int numThread = 0;
                    for (int i = 2; i < max; i++)
                    {
                        if (!FD_ISSET(i, &setthr))
                        {
                            params->idThread = numThread + 1;
                            int socketVoisin = tabSocketsVoisins[numThread];

                            params->numeroPi = numeroPi;
                            params->socketVoisin = socketVoisin;
                            params->message = message;

                            if (pthread_create(&threads[numThread], NULL, diffusionMessage, params) != 0)
                            {
                                perror("❌ Pi : problème à la creation du thread");
                                free(params);
                                exit(1);
                            }

                            if (i < nombreVoisins - 1)
                                printf("\033[0;%dm[%d] -----\033[0m\n", (30 + numeroPi), numeroPi);

                            numThread++;
                        }
                    }

                    for (int i = 0; i < nombreVoisins; i++)
                    {
                        pthread_join(threads[i], NULL);
                    }

                    compteur++;
                    if (compteur == 3)
                        break;
                }
            }
            free(threads);
        }
    }

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