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

#define NOMBRE_VOISIN_MAX 10

struct infosPi {
    int numero_pi;
    int port_pi;
};

struct envoiPi {
    int valide;
    struct sockaddr_in socketAdressePi;
};

int initialisation(char *adresseIP_pconfig, char *port_pconfig, int port_pi, int numero_pi, struct sockaddr_in* tab_voisins)
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
    printf("----- 📨 Envois info Pi -----\n");

    printf("\t🧮 Numero Pi : '%d'\n", numero_pi);
    printf("\t🐖 Port du Pi : %d\n", port_pi);

    struct infosPi info;
    info.numero_pi = numero_pi;
    info.port_pi = port_pi;

    printf("\t📨 Envois du numéro a l'ip : %s et au port %s\n", adresseIP_pconfig, port_pconfig);

    socklen_t sizeAdr = sizeof(struct sockaddr_in);
    int resSend = sendto(socket_pi, &info, sizeof(info) + 1, 0, (struct sockaddr *)&strctureSocket_pconfig, sizeAdr);
    if (resSend == -1)
    {
        perror("\t❌ Pi : problème avec le send to :"); 
        exit(1);
    }
    printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);

    printf("----- Fin envoie numero Pi -----\n\n");

    // ---------------------------------------------------------------------

    printf("----- 📩 Recevoir 🧦 des voisins -----\n");

    int nombre_max_voisins = NOMBRE_VOISIN_MAX;
    
    int nombreVoisin = 0;

    struct envoiPi envoiPi;
    do {
        struct sockaddr_in sockExpediteur;
        socklen_t lgAdr = sizeof(struct sockaddr_in);

        ssize_t resRecv = recvfrom(socket_pi, &envoiPi, sizeof(envoiPi), 0, (struct sockaddr *)&sockExpediteur, &lgAdr);
        if (resRecv == -1)
        {
            perror("\t❌ Pi : problème avec le recvFrom :");
            exit(1);
        }

        char *ip_pi_suivant = inet_ntoa(envoiPi.socketAdressePi.sin_addr);
        int port_pi_suivant = ntohs(envoiPi.socketAdressePi.sin_port);

        printf("\t📮 Ip du Pi suivant : %s\n", ip_pi_suivant);
        printf("\t🐖 Port du Pi suivant : %d\n", port_pi_suivant);
        printf("\t-----\n");

        if (nombreVoisin < nombre_max_voisins) {
            tab_voisins[nombreVoisin] = envoiPi.socketAdressePi;
            nombreVoisin++;
        }
        else {
            nombre_max_voisins += 10;
            tab_voisins = realloc(tab_voisins, nombre_max_voisins * sizeof(struct sockaddr_in));
            tab_voisins[nombreVoisin] = envoiPi.socketAdressePi;
            nombreVoisin++;
        } 
    } while (envoiPi.valide == 1);
    nombreVoisin--;    

    int cls = close(socket_pi);
    if (cls == -1)
    {
        perror("❌ Pi : problème avec le close :");
        exit(1); // je choisis ici d'arrêter le programme
    }
    printf("\t🚪 Fermeture de la 🧦 réussi.\n");

    printf("----- 🏆 Fin reception des 🧦 voisins -----\n");

    printf(" --- 👋 Fin des échanges avec Pconfig --- \n\n");

    return nombreVoisin;
}
/*
void connexionVoisins(int port_pi, struct sockaddr_in* tab_voisins, int nombre_voisins) {
     printf(" --- 📲 Connection avec les voisins --- \n");

    // --- Etape 1 : Creation des socket
    int socketPi = creerSocket();

    nommerSocket(socketPi, port_pi);

    printf("\t⌚ Pi : Mise en écoute de la socket;\n");

    ecouterDemande(socketPi);

    printf("\t👂 Pi : Attente de 2 secondes ⌚...\n");
    sleep(2);
    printf("\t✅ Pi : Attente terminée.\n");
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
}
*/

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
    printf("\tCouple envoyé - Numero Pi : %d, Compteur Pi : %d\n", couple.numero_pi, couple.compteur_pi);
    printf("\tNombre d'octets envoyés : %zd\n", resSendTCP);
    */

    printf("----- 🏆 Fin envoie du couple au suivant ------\n\n");

}

void traitementClassique(int numero_pi, struct sockaddr_in* tab_voisins, int nombre_voisins, int intervale_temps)
{
    
    printf("----- 📨 Envois d'un message aux %d voisins du Pi n°%d toutes les %d sec-----\n", nombre_voisins, numero_pi, intervale_temps);

    while(1) {
        sleep(intervale_temps);

        // ---- Envois du message a tout les voisins
        pthread_t threads[nombre_voisins];
        for (int i = 0; i < nombre_voisins; i++) {

            struct sockaddr_in voisin = tab_voisins[i];
            struct paramsFonctionThread *params = malloc(sizeof(struct paramsFonctionThread));
            params->idThread = i;
            params->voisin = voisin;

            if (pthread_create(&threads[i], NULL, 
                diffusion_message, params) != 0){
                perror("❌ Pi : problème à la creation du thread");
                exit(1);
            }

            if (i < nombre_voisins-1)
                printf("\t-----\n");
        }

        for (int i = 0; i < nombre_voisins; i++) {
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
        printf("utilisation : %s IP_pconfig port_pconfig numero_pi intervale_temps\n", argv[0]);
        exit(1);
    }
    char *adresseIP_pconfig = argv[1];
    char *port_pconfig = argv[2];
    int numero_pi = atoi(argv[3]);
    int intervale_temps = atoi(argv[4]);

    // --- Etape 1 : Creation et mise en ecoute de la socket
    int socketPi = creerSocket();

    struct sockaddr_in structAdresseServeur = nommerSocket(socketPi, 0);
    char *ip_pi_suivant = inet_ntoa(structAdresseServeur.sin_addr);
    int port_pi_suivant = ntohs(structAdresseServeur.sin_port);

    printf("\t👂 Pi : Mise en écoute de la socket.\n");

    ecouterDemande(socketPi);

    struct sockaddr_in *tabVoisins;

    int nombre_voisins = initialisation(adresseIP_pconfig, port_pconfig, numero_pi, tabVoisins);

    //connexionVoisins(port_pi, tab_voisins, nombre_voisins);
    traitementClassique(numero_pi, tabVoisins, nombre_voisins, intervale_temps);

    printf("🏁 Fin du programme !\n");

    return 0;
}