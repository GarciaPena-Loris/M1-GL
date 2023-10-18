#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>
#include <ifaddrs.h>

#include "fonctionTPC.h"

#define TAILLE_TABLEAU 10

struct infosPi {
    int numero_pi;
    int port_pi;
};

struct envoiPi {
    int terminer;
    struct sockaddr_in socketAdressePi;
};


int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        printf("❌ Pconfig utilisation : %s port_pconfig nomFichier\n", argv[0]);
        exit(1);
    }

    char *port_pconfig = argv[1];
    char* nomFichier = argv[2];

    printf("📖 Debut du lecture du ficier.\n");

    // -- Etape 1 : Recupere nombre Pi dans le fichier
    FILE *file;
    char line[256];
    int nombrePi, nombreArretes;

    // Ouvrir le fichier en mode lecture
    file = fopen(nomFichier, "r");
    if (file == NULL) {
        perror("Erreur lors de l'ouverture du fichier");
        return 1;
    }

    // Lire les lignes jusqu'à atteindre la ligne "p edge X Y"
    while (fgets(line, sizeof(line), file) != NULL) {
        if (sscanf(line, "p edge %d %d", &nombrePi, &nombreArretes) == 2) {
            break;
        }
    }

    if (nombrePi <= 0) {
        perror("❌ Pconfig : nombrePi n'a pas de valeur terminer.");
        return 1;
    }

    printf("🗒️  Lecture réussi, il y a %d Pi dans notre réseau\n\n", nombrePi);

    // -- Etape 2 : Preparation recuperation socketAdresse de tous les Pi.

    struct sockaddr_in *tabSocketAdress = malloc(sizeof(struct sockaddr_in) * nombrePi);

    // Création socket pconfig
    int socketPconfig = socket(PF_INET, SOCK_DGRAM, 0);
    if (socketPconfig == -1)
    {
        perror("❌ Pconfig : problème création 🧦 :");
        exit(1);
    }
    printf("✅ Pconfig : Création de la 🧦 réussie.\n");

    // Nommage la socket pconfig
    struct sockaddr_in ss;
    ss.sin_family = AF_INET;
    ss.sin_addr.s_addr = INADDR_ANY;
    ss.sin_port = htons(atoi(port_pconfig));

    int res = bind(socketPconfig, (struct sockaddr *)&ss, sizeof(ss));

    if (res == -1)
    {
        perror("❌ Pconfig : problème de bind :");
        exit(1);
    }
    printf("✅ Pconfig : Nommage de la 🧦 réussi.\n");

    // Affichage de l'ip et du port
    struct ifaddrs *ifap, *ifa;
    struct sockaddr_in *sa;
    char *addr;

    getifaddrs(&ifap);
    for (ifa = ifap; ifa; ifa = ifa->ifa_next)
    {
        if (ifa->ifa_addr && ifa->ifa_addr->sa_family == AF_INET)
        {
            sa = (struct sockaddr_in *)ifa->ifa_addr;
            addr = inet_ntoa(sa->sin_addr);
            printf("🌐 Address IP Pconfig : %s\n", addr);
        }
    }
    freeifaddrs(ifap);
    printf("🐖 Port du Pconfig : %d\n", atoi(port_pconfig));


    // -- Etape 3 : Recuperation socketAdresse de tous les Pi.
    int nombrePi_recu = 0;
    while (nombrePi_recu < nombrePi)
    {
        printf("📩 En attente du message d'un Pi...\n");

        struct sockaddr_in socketAdressPi;
        socklen_t lgAdr = sizeof(struct sockaddr_in);

        struct infosPi info;
        
        ssize_t resRecv = recvfrom(socketPconfig, &info, sizeof(info), 0, (struct sockaddr *)&socketAdressPi, &lgAdr);
        if (resRecv == -1)
        {
            perror("❌ Pconfig : problème avec le recvFrom :");
            exit(1);
        }
        printf("📧 Information Pi reçus : \n");

        char *ip_pi = inet_ntoa(socketAdressPi.sin_addr);
        int port_pi = ntohs(info.port_pi);

        printf("\t🧮 Numéro du Pi : %d\n", info.numero_pi);
        printf("\t📮 Ip du Pi : %s\n", ip_pi);
        printf("\t🐖 Port du Pi : %d\n", port_pi);

        printf("---\n");

        struct sockaddr_in realSocketAdressPi;
        realSocketAdressPi.sin_family = AF_INET;
        realSocketAdressPi.sin_addr = socketAdressPi.sin_addr;
        realSocketAdressPi.sin_port = info.port_pi;

        // Affectation dans les tableaux
        tabSocketAdress[info.numero_pi - 1] = realSocketAdressPi;

        nombrePi_recu++;
    }

    printf("🏁 Tout les Pi on bien été receptionés, maintenant on distribut 🌐 :\n");

    // -- Etape 4 : Envoyer les tableau de voisins a chaque Pi
    socklen_t sizeAdr = sizeof(struct sockaddr_in);
    struct sockaddr_in socketAddrVide;
    socketAddrVide.sin_family = AF_INET;
    socketAddrVide.sin_addr.s_addr = 0;
    socketAddrVide.sin_port = 0;

    while (fgets(line, sizeof(line), file) != NULL) {
        int numPiClient, numPiServeur;

        if (sscanf(line, "e %d %d", &numPiClient, &numPiServeur) == 2) {
            struct envoiPi envoiPi;
                       
            printf("----- 📨 Envois des données au Pi n°%d -----\n", numPiClient);

            envoiPi.terminer = 0;
            envoiPi.socketAdressePi = tabSocketAdress[numPiServeur - 1];

            int resSend = sendto(socketPconfig, &envoiPi, sizeof(envoiPi), 0, (struct sockaddr *) &tabSocketAdress[numPiClient - 1], sizeAdr);
            if (resSend == -1)
            {
                perror("\t❌ Pi : problème avec le send to :"); 
                exit(1);
            }

            char *ip_pi_suivant = inet_ntoa(tabSocketAdress[numPiServeur - 1].sin_addr);
            int port_pi_suivant = ntohs(tabSocketAdress[numPiServeur - 1].sin_port);

            printf("\t🧮 Numéro du Pi suivant : %d\n", numPiServeur);
            printf("\t📮 Ip du Pi suivant : %s\n", ip_pi_suivant);
            printf("\t🐖 Port du Pi suivant : %d\n", port_pi_suivant);
            printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);
            printf("\t❓ terminer : %d\n", envoiPi.terminer);

            printf("----- 🏆 Fin envois des données au Pi n°%d -----\n\n", numPiClient);
        }
    }
    printf("📖 Lecture du fichier terminée !\n");

    // Fermer le fichier
    fclose(file);

    printf("✅ Tous les voisins ont été envoyé ! Maintenant on les en informe.\n");

    struct envoiPi envoiPi;
    envoiPi.terminer = 0;
    envoiPi.socketAdressePi = socketAddrVide;

    for (int i = 0; i < nombrePi; i++)
    {
        int numero_pi = i;
        printf("----- 📨 Envois de l'info au Pi n°%d -----\n", numero_pi+1);

        socklen_t sizeAdr = sizeof(struct sockaddr_in);
        int resSend = sendto(socketPconfig, &envoiPi, sizeof(envoiPi), 0, (struct sockaddr *) &tabSocketAdress[numero_pi], sizeAdr);

        if (resSend == -1)
        {
            perror("❌ Pconfig : problème avec le send to :");
            exit(1);
        }

        printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);

        printf("----- 🏆 Fin envois des données au Pi n°%d -----\n\n", numero_pi+1);
    } 

    printf("🏁 Toutes les 🧦 on bien été envoyées !\n");

    // -- Etape 9 : Fermer la socket (lorsqu'elle n'est plus utilisée)
    int cls = close(socketPconfig);
    if (cls == -1)
    {
        perror("❌ Pconfig : problème avec le close :");
        exit(1);
    }
    printf("🚪 Pconfig : Fermture de la socket réussi.\n");

    printf("🙅 Pconfig : je termine.\n");

    return 0;
}