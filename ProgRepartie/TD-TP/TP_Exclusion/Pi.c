#include <netinet/in.h>
#include <stddef.h>
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

#include "fonctionTCP.h"

#define MAX_RANDOM 10

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

int *initialisationReseau(char *adresseIPPconfig, char *portPconfig, struct sockaddr_in structAdresseServeurTCP, int numeroPi, int socketPiTCP, int *nombreVoisins)
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


// ###############################################################################
// Agrawala
// ###############################################################################

struct Message {
    char* typei;
    int datej;
    int numeroi;
};

// ################### Mutex

typedef struct {
    int* attendusi;
    int tailleAttendusi;
    int* differesi;
    int tailleDifferesi;
    char* etati;
    int hi;
    int lasti;
    int prioritairei;
    pthread_cond_t * cond; // condition
    pthread_mutex_t * verrou; // mutex 
} ExclusionMutuelle;

// ###################

// ################### Parametre fonction

typedef struct {
    int numeroPi;                   // Numéro du processus
    int nombreVoisins;              // Nombre de voisins
    int* tabSocketsVoisins;         // Tableau des sockets des voisins
    ExclusionMutuelle* exclusionMutex; // Pointeur vers la structure ExclusionMutuelle
} Parametres;

typedef struct {
    Parametres* parametre;
    struct Message message;
} ParametresThread;

// ###################



// ################### Fonctions message
int envoyerMessage(int socketVoisin, struct Message message) {
    // Envoi de la taille du message
    ssize_t tailleMessage = sizeof(struct Message);
    ssize_t resSendTCPsize = sendTCP(socketVoisin, &tailleMessage, sizeof(tailleMessage));
    if (resSendTCPsize == 0 || resSendTCPsize == -1) {
        perror("Erreur lors de l'envoi de la taille du message");
        return -1;
    }
    printf("Taille du message envoyé : %zu\n", tailleMessage);

    // Envoi du message
    ssize_t resSendTCP = sendTCP(socketVoisin, &message, tailleMessage);
    if (resSendTCP == 0 || resSendTCP == -1) {
        perror("Erreur lors de l'envoi du message");
        return -1;
    }

    return 0;
}

// ###################

// ################### Fonctions tableau

// Fonction pour enlever un élément du tableau
void enleverElementDuTableau(int* tableau, int* taille, int element) {
    int i, j;
    for (i = 0; i < *taille; i++) {
        if (tableau[i] == element) {
            // Décalage des éléments suivants vers la gauche pour remplir le trou
            for (j = i; j < *taille - 1; j++) {
                tableau[j] = tableau[j + 1];
            }
            (*taille)--;
            break; // On a trouvé l'élément, on peut sortir de la boucle
        }
    }
}

// ###################


void demanderAcces(void * parametres) {
    Parametres * args = (Parametres *) parametres;
    int numeroPi = args->numeroPi;
    ExclusionMutuelle * exclusionMutex = args->exclusionMutex;

    printf("\033[0;%dm[%d] --- 🔐 Debut de la demande d'accès a mes %d voisins. ---\033[0m\n", (30 + numeroPi), numeroPi, args->nombreVoisins);

    // Initialisation des variable 
    pthread_mutex_lock(exclusionMutex->verrou);
    printf("\033[0;%dm[%d] 🔒 J'ai vérouillé pour remplir.\033[0m\n", (30 + numeroPi), numeroPi);
    exclusionMutex->etati = "demandeur";
    exclusionMutex->hi++;
    exclusionMutex->lasti = exclusionMutex->hi;

    // Initialisation de attenudsi
    for (int i = 0; i < args->nombreVoisins; i++) {
        if (args->tabSocketsVoisins[i] != numeroPi) {
            exclusionMutex->attendusi[i] = args->tabSocketsVoisins[i];
            exclusionMutex->tailleAttendusi++;
        }
    }

    for (int j = 0; j < args->nombreVoisins; j++) {
        struct Message demande;
        demande.typei = "REQUEST";
        demande.datej = exclusionMutex->lasti;
        demande.numeroi = numeroPi;

        // Envoyer le Message à Pj
        printf("\033[0;%dm[%d] 📨 Envois de la REQUEST au voisin %d.\033[0m\n", (30 + numeroPi), numeroPi, args->tabSocketsVoisins[j]);
        printf("demande.typei : '%s'\n", demande.typei);
        printf("demande.datej : '%d'\n", demande.datej);
        printf("demande.numeroi : '%d'\n", demande.numeroi);

        int res = envoyerMessage(args->tabSocketsVoisins[j], demande);
        if (res == -1 || res == 0) {
            // Gestion de l'erreur
        }
    }

    printf("\033[0;%dm[%d] 💤 J'attend que attendui soit vide...\033[0m\n", (30 + numeroPi), numeroPi);
    pthread_cond_wait(exclusionMutex->cond, exclusionMutex->verrou);
    printf("\033[0;%dm[%d] 🥱 : Je me réveille.\033[0m\n", (30 + numeroPi), numeroPi);

    exclusionMutex->etati = "dedans";

    pthread_mutex_unlock(exclusionMutex->verrou);
    printf("\033[0;%dm[%d] 🔑 : Je suis libre !\033[0m\n", (30 + numeroPi), numeroPi);

    printf("\033[0;%dm[%d] --- 🏁 Fin de la demande d'accès. ---\033[0m\n", (30 + numeroPi), numeroPi);
}

void libererAcces(void * parametres) {
    Parametres * args = (Parametres *) parametres;
    int numeroPi = args->numeroPi;
    ExclusionMutuelle * exclusionMutex = args->exclusionMutex;

    printf("\033[0;%dm[%d] --- 🔑 Debut de libération d'accès. ---\033[0m\n", (30 + numeroPi), numeroPi);

    pthread_mutex_lock(exclusionMutex->verrou);
    printf("\033[0;%dm[%d] 🔒 J'ai vérouillé pour libérer.\033[0m\n", (30 + numeroPi), numeroPi);

    exclusionMutex->etati = "dehors";

    // Envoi de PERMISSION à tous les processus dans differesi
    for (int i = 0; i < exclusionMutex->tailleDifferesi; i++) {
        struct Message permission;
        permission.typei = "PERMISSION";
        permission.numeroi = numeroPi;

        // Envoyer le Message à Pj
        printf("\033[0;%dm[%d] 📨 Envois de la PERMISSION.\033[0m\n", (30 + numeroPi), numeroPi);

        int res = envoyerMessage(args->tabSocketsVoisins[exclusionMutex->differesi[i]], permission);
        if (res == -1 || res == 0) {
            // Gestion de l'erreur
        }
    }
    

    // Remise à zéro de differesi
    exclusionMutex->differesi = malloc(sizeof(int) * args->nombreVoisins);
    exclusionMutex->tailleDifferesi = 0;

    pthread_mutex_unlock(exclusionMutex->verrou);
    printf("\033[0;%dm[%d] 🗝️ J'ai finis de libérer, je libère.\033[0m\n", (30 + numeroPi), numeroPi);

    printf("\033[0;%dm[%d] --- 🏁 Fin de la libération d'accès. ---\033[0m\n", (30 + numeroPi), numeroPi);
}

void* traiterReceptionRequest(void * parametres) {
    ParametresThread * argsThread = (ParametresThread *) parametres;
    Parametres* args = argsThread->parametre;
    struct Message messageRecu = argsThread->message;

    int numeroPi = args->numeroPi;
    ExclusionMutuelle * exclusionMutex = args->exclusionMutex;

    printf("\033[0;%dm[%d] --- 🔐 Debut du traitement de la request. ---\033[0m\n", (30 + numeroPi), numeroPi);

    // Initialisation des variable 
    pthread_mutex_lock(exclusionMutex->verrou);
    printf("\033[0;%dm[%d] 🔒 J'ai vérouillé pour modifier.\033[0m\n", (30 + numeroPi), numeroPi);

    exclusionMutex->hi = (exclusionMutex->hi > messageRecu.datej) ? exclusionMutex->hi : messageRecu.datej;
    if (!strcmp(exclusionMutex->etati, "dehors")) {
        if (exclusionMutex->lasti < messageRecu.datej || 
            (exclusionMutex->lasti == messageRecu.datej && args->numeroPi < messageRecu.numeroi)) {
            exclusionMutex->prioritairei = 1;
        } else {
            exclusionMutex->prioritairei = 0;
        }
    } else {
        exclusionMutex->prioritairei = 0;
    }


    if (exclusionMutex->prioritairei == 1) {
        exclusionMutex->differesi[exclusionMutex->tailleDifferesi] = exclusionMutex->lasti;
        exclusionMutex->tailleDifferesi++;
    } else {
        // Envoyer <PERMISSION, i> à Pj
        struct Message permission;
        permission.typei = "PERMISSION";
        permission.numeroi = args->numeroPi;

        printf("\033[0;%dm[%d] 📨 Envois de la PERMISSION.\033[0m\n", (30 + numeroPi), numeroPi);

        envoyerMessage(args->tabSocketsVoisins[messageRecu.numeroi], permission);

    }

    printf("\033[0;%dm[%d] 🗝️ J'ai finis de traiter la request, je libère.\033[0m\n", (30 + numeroPi), numeroPi);
    pthread_mutex_unlock(exclusionMutex->verrou);

    printf("\033[0;%dm[%d] --- 🏁 Fin du traitement de la reception ---\033[0m\n", (30 + numeroPi), numeroPi);
}

// Fonction pour traiter la réception d'un message PERMISSION
void* traiterReceptionPermission(void * parametres) {
    ParametresThread * argsThread = (ParametresThread *) parametres;
    Parametres* args = argsThread->parametre;
    struct Message messageRecu = argsThread->message;

    int numeroPi = args->numeroPi;
    ExclusionMutuelle * exclusionMutex = args->exclusionMutex;

    printf("\033[0;%dm[%d] --- 🔐 Debut du traitement de la permission. ---\033[0m\n", (30 + numeroPi), numeroPi);

    pthread_mutex_lock(exclusionMutex->verrou);
    printf("\033[0;%dm[%d] 🔒 J'ai vérouillé pour modifier attendusi.\033[0m\n", (30 + numeroPi), numeroPi);

    // Retirer Pj de attendusi
    enleverElementDuTableau(exclusionMutex->attendusi, &exclusionMutex->tailleAttendusi, args->tabSocketsVoisins[messageRecu.numeroi]);
    printf("\033[0;%dm[%d] 🗑️ J'ai enlevé [%d] de attendusi, sa taille vaut maintenant %d.\033[0m\n", (30 + numeroPi), numeroPi, args->tabSocketsVoisins[messageRecu.numeroi], exclusionMutex->tailleAttendusi);

    // Vérification Tableau attendusi
    if (exclusionMutex->tailleAttendusi == 0) {
        printf("\033[0;%dm[%d] 📢 Le tableau attendusi est vide, je libère !\033[0m\n", (30 + numeroPi), numeroPi);
        pthread_cond_broadcast(exclusionMutex->cond);
    }

    printf("\033[0;%dm[%d] 🗝️ J'ai finis de traiter la Permission, je libère.\033[0m\n", (30 + numeroPi), numeroPi);
    pthread_mutex_unlock(exclusionMutex->verrou);  // Déverrouillage du mutex après le traitement

    printf("\033[0;%dm[%d] --- 🏁 Fin du traitement de la Permission ---\033[0m\n", (30 + numeroPi), numeroPi);
}

void* processusReception(void* parametres) {
    Parametres * args = (Parametres *) parametres;
    int numeroPi = args->numeroPi;

    // --- Mise en place du multiplexage
    fd_set set, setCopie;
    FD_ZERO(&set);

    // --- On ajoute les sockets des voisins dans le tableau de multiplexage
    int max = 0;
    for (int i = 0; i < args->nombreVoisins; i++)
    {
        FD_SET(args->tabSocketsVoisins[i], &set);
        if (args->tabSocketsVoisins[i] > max)
            max = args->tabSocketsVoisins[i];
    }

    // -- Boucle de reception des messages
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

        printf("\033[0;%dm[%d] Message recu !!\033[0m\n", (30 + numeroPi), numeroPi);

        // --- On parcours le tableau de multiplexage pour savoir quelle socket a recu un message
        for (int descripteurSocket = 2; descripteurSocket <= max; descripteurSocket++)
        {
            struct Message messageRecu;
            int tailleMessage;

            if (FD_ISSET(descripteurSocket, &setCopie))
            {
                ssize_t resRecvTCPsize = recvTCP(descripteurSocket, &tailleMessage, sizeof(tailleMessage));
                if (resRecvTCPsize == 0 || resRecvTCPsize == -1) {
                    perror("Erreur lors de la réception de la taille du message");
                    exit(1);
                }

                printf("\033[0;%dm[%d] Taille : %d\033[0m\n", (30 + numeroPi), numeroPi, tailleMessage);

                messageRecu.typei = malloc(tailleMessage);

                // Réception du message
                ssize_t resRecvTCP = recvTCP(descripteurSocket, (struct Message *)&messageRecu, sizeof(struct Message));
                if (resRecvTCP == 0 || resRecvTCP == -1) {
                    perror("Erreur lors de la réception du message");
                    exit(1);
                }

                printf("message.typei : '%s'\n", messageRecu.typei);
                printf("message.datej : '%d'\n", messageRecu.datej);
                printf("message.numeroi : '%d'\n", messageRecu.numeroi);

                printf("\033[0;%dm[%d]\t 📃 Type message reçus du processus %d : '%s'.\033[0m\n", (30 + numeroPi), numeroPi, messageRecu.numeroi, messageRecu.typei);

                pthread_t threads;
                ParametresThread parametresThread;
                parametresThread.parametre = args;
                parametresThread.message = messageRecu;
                if (strcmp(messageRecu.typei, "REQUEST")) {
                    if (pthread_create(&threads, NULL, traiterReceptionRequest, &parametresThread) != 0) {
                        perror("❌ Pi : Erreur creation thread request");
                    }
                }
                else if (strcmp(messageRecu.typei, "PERMISSION")) {
                    if (pthread_create(&threads, NULL, traiterReceptionPermission, &parametresThread) != 0) {
                        perror("❌ Pi : Erreur creation thread permission");
                    }
                }
                else {
                    printf("C'est une demande de type ERREUR !\n");
                } 
            }
        }
    }
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

    tabSocketsVoisins = initialisationReseau(adresseIPPconfig, portPconfig, structAdresseServeurTCP, numeroPi, socketPiTCP, &nombreVoisins);


    // Agrawala Initialisation
    printf("\033[0;%dm[%d] 📦 Allocation des ressources\033[0m\n", (30 + numeroPi), numeroPi);


    // Verrou
    ExclusionMutuelle exclusionMutex;

    // Création du verrou et des variable conditionnelle
    pthread_mutex_t verrou;
    pthread_cond_t cond;
    pthread_mutex_init(&verrou, NULL);
    pthread_cond_init(&cond, NULL);


    exclusionMutex.verrou = &verrou;
    exclusionMutex.cond = &cond;
    exclusionMutex.attendusi = malloc(sizeof(int) * nombreVoisins);
    exclusionMutex.tailleAttendusi = 0;

    exclusionMutex.differesi = malloc(sizeof(int) * nombreVoisins);
    exclusionMutex.tailleDifferesi = 0;


    exclusionMutex.etati = "dehors";
    exclusionMutex.hi = 0;
    exclusionMutex.lasti = 0;
    exclusionMutex.prioritairei = 0;


    // Initialisation des parametres
    Parametres parametres;
    parametres.numeroPi = numeroPi;
    parametres.nombreVoisins = nombreVoisins;
    parametres.tabSocketsVoisins = tabSocketsVoisins;
    parametres.exclusionMutex = &exclusionMutex;

    printf("\033[0;%dm[%d] 🚚 Allocation des ressources terminé \033[0m\n", (30 + numeroPi), numeroPi);


    srand(time(0));

    // Thread de reception
    pthread_t threads;
    if (pthread_create(&threads, NULL, processusReception, &parametres) != 0) {
        perror("❌ Pi : Erreur creation thread");
    }

    while(1) {
        printf("\033[0;%dm[%d] ⏳ J'attends %d secondes avant de faire ma demande 👉👈...\033[0m\n", (30 + numeroPi), numeroPi, intervaleTemps);
        // Attente
        sleep(intervaleTemps);

        // Agrawala Demande d'acces
        demanderAcces(&parametres);

        // Calcul de 5 seconde
        sleep(5);

        // Agrawala Liberation d'acces
        libererAcces(&parametres);
    }

    printf("\033[0;%dm[%d] 🏁 Fin du Pi n°%d !\033[0m\n", (30 + numeroPi), numeroPi, numeroPi);

    return 0;
}