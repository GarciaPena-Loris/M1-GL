#include <netinet/in.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>

#include "fonctionTCP.h"

#define NOMBRE_VOISIN_MAX 5

struct Couple {
    int numero_pi;
    int compteur_pi;
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

    printf("----- 📩 Recevoir 🧦 des voisins -----\n");

    int nombre_max_voisins = NOMBRE_VOISIN_MAX;
    
    if (tab_voisins == NULL) {
         perror("\t❌ Pi : problème avec l'allocation mémoire :"); 
        exit(1);
    }

    int nombreVoisin = 0;

    struct sockaddr_in socket_suivant;
    do {
        struct sockaddr_in sockExpediteur;
        socklen_t lgAdr = sizeof(struct sockaddr_in);

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
        printf("\t-----\n");

        if (nombreVoisin < nombre_max_voisins) {
            tab_voisins[nombreVoisin] = socket_suivant;
            nombreVoisin++;
        }
        else {
            nombre_max_voisins += NOMBRE_VOISIN_MAX;
            tab_voisins = realloc(tab_voisins, nombre_max_voisins * sizeof(struct sockaddr_in));
            tab_voisins[nombreVoisin] = socket_suivant;
            nombreVoisin++;
        } 
    } while (socket_suivant.sin_addr.s_addr != 0);
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



void traitementClassique(int numero_pi, struct sockaddr_in* tab_voisins, int nombre_voisins)
{
    
    printf("🏠 Affichage des %d voisins du Pi n°%d:\n", nombre_voisins, numero_pi);

    for (int i = 0; i < nombre_voisins; i++) {
        struct sockaddr_in voisin = tab_voisins[i];
        
        char *ip_voisin = inet_ntoa(voisin.sin_addr);
        int port_voisin = ntohs(voisin.sin_port);

        printf("\t📮 Voisin n°%d du Pi n° %d - IP : %s\n", i + 1, numero_pi, ip_voisin);
        printf("\t🐖 Voisin n°%d du Pi n° %d - Port : %d\n", i + 1, numero_pi, port_voisin);

        if (i < nombre_voisins-1)
            printf("\t-----\n");
    }

    printf("✅ Fin d'affichage des voisins.\n");

}

int main(int argc, char *argv[])
{
    /* Je passe en paramètre le numéro de port et le numero du processus.*/
    if (argc != 5)
    {
        printf("utilisation : %s IP_pconfig port_pconfig port_pi numero_pi \n", argv[0]);
        exit(1);
    }
    char *adresseIP_pconfig = argv[1];
    char *port_pconfig = argv[2];
    int port_pi = atoi(argv[3]);
    int numero_pi = atoi(argv[4]);

    struct sockaddr_in *tab_voisins = malloc(sizeof(struct sockaddr_in) * NOMBRE_VOISIN_MAX);
    int nombre_voisins = initialisation(adresseIP_pconfig, port_pconfig, port_pi, numero_pi, tab_voisins);

    traitementClassique(numero_pi, tab_voisins, nombre_voisins);

    printf("🏁 Fin du programme !\n");

    return 0;
}