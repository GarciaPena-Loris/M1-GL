#include <stdio.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/shm.h>
#include <sys/msg.h>
#include <sys/stat.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>

#include "fonctionTPC.h"

typedef struct accesZone {
    long etiquette;
    int msg;
} accesZone;


void afficheTableauPartage(int nbZone, int numProc, int * memoirePartagee[2]) {
    printf("\033[%im [%i] Tableau partagee :\033[0m [ ", numProc + 31, numProc+1); 
    for(int i = 0; i < nbZone - 1; i++) {
        for(int j=0; j < 2; j++) {
            printf("%d, ", memoirePartagee[i][j]);
        }
    } 
    printf("%d ] \n", memoirePartagee[nbZone - 1][0]);
    printf("%d ] \n", memoirePartagee[nbZone - 1][1]);
}

void traitementClassique(int socketPi, char* ip_pi, int port_pi, int * memoirePartagee[2], int nb_pi, int fileId) {
    // 1 - Recuperer le numero de processus
    // 2 - Ajouter dans la memoire partagee <IP, port>
    // 3 - Envoyer dans la file de message au suivant qu'on a bien mis ce qu'il faut dans la memoire
    // 4 - Attendre de recevoir le message du precedent (voir exception quand c'est le premier)
    // 5 - Envoyer le trio a pi
    printf("----- 1 -Recevoir numero precessus pi -----\n");

    printf("  --Recevoir la taille du message--\n");

    int tailleMessage;
    ssize_t resRecvTCPsize = recvTCP(socketPi, &tailleMessage, sizeof(tailleMessage));

    printf("\tMessage recus : '%d'\n", tailleMessage);
    printf("\tNombre d'octet : '%ld'\n\n", resRecvTCPsize);

    printf("  --Recevoir le message de taille %d--\n", tailleMessage);

    char messageRecu[300];
    ssize_t resRecvTCP = recvTCP(socketPi, messageRecu, tailleMessage);

    printf("\tMessage recus : '%s'\n", messageRecu);
    printf("\tNombre d'octet recus : '%ld'\n\n", resRecvTCP);

    int numeroProcessus_pi = atoi(messageRecu);

    printf("-----Fin reception IP voisin -----\n");

    // 2 - Ajouter dans la memoire partagee le trio IP, <IP, port>
    printf("----- 2 - Ajouter dans la memoire partagee <IP, port> -----\n");

    memoirePartagee[numeroProcessus_pi -1][0] = atoi(ip_pi);
    memoirePartagee[numeroProcessus_pi -1][1] = port_pi;
    
    // Affiche le tableau
    afficheTableauPartage(nb_pi, numeroProcessus_pi, memoirePartagee);

    // 3 - Envoyer dans la file de message au suivant qu'on a bien mis ce qu'il faut dans la memoire
    printf("----- 3 - Envois dans la file de message -----\n");

    printf("\033[%im [%i] 📤 Envois de la demande d'accés a la ressource \033[0m\n", numeroProcessus_pi + 31, numeroProcessus_pi+1);
    accesZone envois;

    if (numeroProcessus_pi == nb_pi) {
        envois.etiquette = 1;
    }
    else {
        envois.etiquette = numeroProcessus_pi+1;
    }
    envois.msg = 0;
    int res_send = msgsnd(fileId, &envois, 0, 0);
    if (res_send == -1) {
        perror("❌ Erreur lors de la demande d'accès de la variable partagée ");
        exit(1);
    }
    printf("\033[%im [%i] 📬 Demande de la ressource envoyé avec succés !\033[0m \n", numeroProcessus_pi + 31, numeroProcessus_pi+1);

    // 4 - Attendre de recevoir le message du precedent (voir exception quand c'est le premier)
    printf("----- 4 - Recois validation du precedent -----\n");
    accesZone verif;
    int res_rcv = msgrcv(fileId, &verif, 0, numeroProcessus_pi, 0);
    if (res_rcv == -1) {
        perror("❌ Erreur lors de la réception de la variable partagée ");
        exit(1);
    }
    printf("\033[%im [%i] 📩 Message récupéré avec succès ! \033[0m \n", numeroProcessus_pi + 31, numeroProcessus_pi+1);

    // 5 - Envoyer <ip, port> au suivant

    memoirePartagee[numeroProcessus_pi][0];

    printf("----- 5 - Envois <ip, port> au suivant -----\n");

    printf("----- Envois IP -----\n");

    printf("IP : '%s'\n", numero_pi);
    printf("Taille Message : %ld\n", strlen(numero_pi));

    // --- Etape 2 : Envois de la taille du message
    printf("-----Envoyer messages-----\n");
    printf("  --Envois de la taille--\n");
    int tailleMessage = strlen(numero_pi) + 1;
    ssize_t resSendTCPsize = sendTCP(socketPconfig, &tailleMessage, sizeof(tailleMessage));

    printf("\tMessage envoyé : %d\n", tailleMessage);
    printf("\tNombre d'octets envoyés : %zd\n", resSendTCPsize);

    // --- Etape 3 : Envois du message
    printf("  --Envois du message--\n");
    ssize_t resSendTCP = sendTCP(socketPconfig, numero_pi, strlen(numero_pi) + 1);

    printf("\tMessage envoyé : %s\n", numero_pi);
    printf("\tNombre d'octets envoyés : %zd\n", resSendTCP);

    printf("----- Fin envoie numero Pi -----\n\n");

    printf("----- Envois numero Pi -----\n");

    printf("Numero Pi : '%s'\n", numero_pi);
    printf("Taille Message : %ld\n", strlen(numero_pi));

    // --- Etape 2 : Envois de la taille du message
    printf("-----Envoyer messages-----\n");
    printf("  --Envois de la taille--\n");
    int tailleMessage = strlen(numero_pi) + 1;
    ssize_t resSendTCPsize = sendTCP(socketPconfig, &tailleMessage, sizeof(tailleMessage));

    printf("\tMessage envoyé : %d\n", tailleMessage);
    printf("\tNombre d'octets envoyés : %zd\n", resSendTCPsize);

    // --- Etape 3 : Envois du message
    printf("  --Envois du message--\n");
    ssize_t resSendTCP = sendTCP(socketPconfig, numero_pi, strlen(numero_pi) + 1);

    printf("\tMessage envoyé : %s\n", numero_pi);
    printf("\tNombre d'octets envoyés : %zd\n", resSendTCP);

    printf("----- Fin envoie numero Pi -----\n\n");


}

int main(int argc, char *argv[]) {
    if (argc != 3){
        printf("utilisation : %s port_pconfig nb_pi\n", argv[0]);
        exit(1);
    }
    char* numeroPort = argv[1];
    int nb_pi = atoi(argv[2]);

    key_t cle = ftok("azerty", 1234);

    // -- Etape 1 : Création d'une mémoire partagée
    int memoirePartagee = shmget(cle, nb_pi * sizeof(int), IPC_CREAT | IPC_EXCL | 0600);
    if (memoirePartagee == -1){
        perror("❌ Erreur lors de la création de la memoire partagee - shmget : ");
        exit(-1);
    }

    printf("Creation segment de mémoire ok avec memoire id : %d \n", memoirePartagee);
    
    int (*p_att)[2];
    p_att = (int(*)[2])shmat(memoirePartagee, NULL, 0);
    if (p_att == (void *)-1) {
        perror("❌ Erreur lors de l'initialisation de la memoire partagee - shmat : ");
        exit(-1);
    }


    for(int i=0; i < nb_pi; i++){ // Initialisation des cases de la mémoire partagée
        for(int j=0; j < 2; j++) {
            p_att[i][j] = 0;
        }
    }

    if (shmdt(p_att) == -1){
        perror("❌ Erreur lors du detachement de la memoire partagee - shmdt : ");
        exit(-1);
    }

    // -- Etape 2 : Création d'une file de message
    int fileId = msgget(cle, IPC_CREAT|0666);
    if (fileId == -1) {
        perror("❌ Erreur lors de l'accès à la file de message ");
        exit(-1);
    }
    printf("ID de la file de message : %i\n", fileId);

    // -- Etape 1 : Création socket pconfig
    int socketPconfig = creerSocket();

    // -- Etape 2 : Nommage la socket pconfig
    nommerSocket(socketPconfig, numeroPort);

    // -- Etape intermediaire : affichage de l'ip
    afficherIPMachine();

    // -- Etape 3 : Ecoute des demandes de connections
    ecouterDemande(socketPconfig, numeroPort);

    printf("*************Mode concurent*************\n");
    printf("En attente de connexion d'un Pi...\n");

    // -- Etape 5 : Acceptation en boucle de clients
    while(1) {
        // -- Etape 6 : Acceptation d'un client
        struct sockaddr_in adresseClient;
        int socketPi = accepterDemande(socketPconfig, &adresseClient);
        if (socketPi == -1) {
            printf("Probleme d'acceptation\n");
        }
        else {
            // -- Etape 7 : Creation fils pour le client
            switch (fork()) {
                case -1 :
                    fprintf(stderr, "fork : Erreur de fork\n");
                    closeSocket(socketPi);
                    exit(EXIT_FAILURE);
                case 0 :
                    closeSocket(socketPconfig);

                    // -- Etape 8 : Traitement
                    printf("-----Debut de traitement avec le Pi d'ip : %s et de port : %d--\n\n", inet_ntoa(adresseClient.sin_addr), ntohs(adresseClient.sin_port));

                    traitementClassique(socketPi, inet_ntoa(adresseClient.sin_addr), ntohs(adresseClient.sin_port), p_att, nb_pi, fileId);

                    printf("--Fin de traiement avec le Pi d'ip : %s et de port : %d--\n\n", inet_ntoa(adresseClient.sin_addr), ntohs(adresseClient.sin_port));

                    exit(EXIT_SUCCESS);
                default :
                    closeSocket(socketPi);
                    printf("\nEn attente d'un autre Pi...\n");
            }
        }
    }

    // -- Etape 9 : Fermeture de la socket serveur
    closeSocket(socketPconfig);

    printf("Serveur : terminé.\n");

    return 0;
}