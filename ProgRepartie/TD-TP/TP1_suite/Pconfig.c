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

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        printf("❌ Pconfig utilisation : %s port_pconfig nomFichier\n", argv[0]);
        exit(1);
    }

    char *port_pconfig = argv[1];
    char* nomFichier = argv[2];
    int nombrePi_recu = 0;

    printf("📖 Debut du lecture du ficier.\n");

    // Lire le fichier
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

    int matriceVoisinage[nombrePi*nombrePi][2];

    

    // Initialiser la matrice de voisinage
    for (int i = 0; i < nombrePi*nombrePi; i++) {
            matriceVoisinage[i][0] = 0;
            matriceVoisinage[i][1] = 0;
    }

    // Lire les lignes contenant les arêtes et les ajouter à la matrice d'adjacence
    int nombre_liaisons = 0;
    while (fgets(line, sizeof(line), file) != NULL) {
        int node1, node2;
        if (sscanf(line, "e %d %d", &node1, &node2) == 2) {
            printf("%d ", node1);
            printf("%d ", node2);
            printf("\n");


            matriceVoisinage[nombre_liaisons][0] = node1;
            matriceVoisinage[nombre_liaisons][1] = node2;
            nombre_liaisons++;
        }
    }

    // Fermer le fichier
    fclose(file);

    printf("📖 Lecture du fichier terminée !\n");



    struct sockaddr_in *tab_socket = malloc(sizeof(struct sockaddr_in) * nombrePi);

    // -- Etape 1 : Création socket pconfig
    int socket_pconfig = socket(PF_INET, SOCK_DGRAM, 0);
    if (socket_pconfig == -1)
    {
        perror("❌ Pconfig : problème création 🧦 :");
        exit(1);
    }
    printf("✅ Pconfig : Création de la 🧦 réussie.\n");

    // -- Etape 2 : Nommage la socket pconfig
    struct sockaddr_in ss;
    ss.sin_family = AF_INET;
    ss.sin_addr.s_addr = INADDR_ANY;
    ss.sin_port = htons(atoi(port_pconfig));

    int res = bind(socket_pconfig, (struct sockaddr *)&ss, sizeof(ss));

    if (res == -1)
    {
        perror("❌ Pconfig : problème de bind :");
        exit(1);
    }
    printf("✅ Pconfig : Nommage de la 🧦 réussi.\n");

    //-- Etape 2 bis : affichage de l'ip
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

    // -- Etape 5 : Reception des messages des Pi
    while (nombrePi_recu < nombrePi)
    {
        printf("📩 En attente de message d'un Pi...\n");

        struct sockaddr_in sockPi;
        socklen_t lgAdr = sizeof(struct sockaddr_in);

        int numero_pi;
        ssize_t resRecv = recvfrom(socket_pconfig, &numero_pi, sizeof(numero_pi), 0, (struct sockaddr *)&sockPi, &lgAdr);
        if (resRecv == -1)
        {
            perror("❌ Pconfig : problème avec le recvFrom :");
            exit(1);
        }
        printf("📧 Message reçus : %d\n", numero_pi);

        printf("---\n");

        char *ip_pi = inet_ntoa(sockPi.sin_addr);
        int port_pi = ntohs(sockPi.sin_port);

        printf("\t🧮 Numéro du Pi : %d\n", numero_pi);
        printf("\t📮 Ip du Pi : %s\n", ip_pi);
        printf("\t🐖 Port du Pi : %d\n", port_pi);

        printf("---\n");

        // Affectation dans les tableaux
        tab_socket[numero_pi - 1] = sockPi;

        nombrePi_recu++;
    }

    printf("🏁 Tout les Pi on bien été receptionés, maintenant on distribut 🌐 :\n");

    for (int i = 0; i < nombre_liaisons; i++)
    {
        // gauche -> droite
        int numero_pi = matriceVoisinage[i][0];
        printf("----- 📨 Envois des données au Pi n°%d -----\n", numero_pi);

        int numero_pi_voisin = matriceVoisinage[i][1];

        socklen_t sizeAdr = sizeof(struct sockaddr_in);
        int resSend = sendto(socket_pconfig, &tab_socket[numero_pi_voisin-1], sizeof(tab_socket[numero_pi_voisin-1]), 0, (struct sockaddr *)&tab_socket[numero_pi-1], sizeAdr);

        if (resSend == -1)
        {
            perror("❌ Pconfig : problème avec le send to :");
            exit(1);
        }

        char *ip_pi_suivant = inet_ntoa(tab_socket[numero_pi_voisin-1].sin_addr);
        int port_pi_suivant = ntohs(tab_socket[numero_pi_voisin-1].sin_port);

        printf("\t🧮 Numéro du Pi suivant : %d\n", numero_pi_voisin);
        printf("\t📮 Ip du Pi suivant : %s\n", ip_pi_suivant);
        printf("\t🐖 Port du Pi suivant : %d\n", port_pi_suivant);
        printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);

        printf("----- 🏆 Fin envois des données au Pi n°%d -----\n\n", numero_pi);

        // droite -> gauche
        numero_pi = matriceVoisinage[i][1];
        printf("----- 📨 Envois des données au Pi n°%d -----\n", numero_pi);

        numero_pi_voisin = matriceVoisinage[i][0];

        resSend = sendto(socket_pconfig, &tab_socket[numero_pi_voisin-1], sizeof(tab_socket[numero_pi_voisin-1]), 0, (struct sockaddr *)&tab_socket[numero_pi-1], sizeAdr);

        if (resSend == -1)
        {
            perror("❌ Pconfig : problème avec le send to :");
            exit(1);
        }

        ip_pi_suivant = inet_ntoa(tab_socket[numero_pi_voisin-1].sin_addr);
        port_pi_suivant = ntohs(tab_socket[numero_pi_voisin-1].sin_port);

        printf("\t🧮 Numéro du Pi suivant : %d\n", numero_pi_voisin);
        printf("\t📮 Ip du Pi suivant : %s\n", ip_pi_suivant);
        printf("\t🐖 Port du Pi suivant : %d\n", port_pi_suivant);
        printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);

        printf("----- 🏆 Fin envois des données au Pi n°%d -----\n\n", numero_pi);
    }

    printf("✅ Tous les voisins ont été envoyé ! Maintenant on les en informe.\n");

    for (int i = 0; i < nombrePi; i++)
    {
        int numero_pi = i;
        printf("----- 📨 Envois de l'info au Pi n°%d -----\n", numero_pi+1);

        struct sockaddr_in socket_vide;
        socket_vide.sin_family = AF_INET;
        socket_vide.sin_addr.s_addr = 0;
        socket_vide.sin_port = 0;

        socklen_t sizeAdr = sizeof(struct sockaddr_in);
        int resSend = sendto(socket_pconfig, &socket_vide, sizeof(socket_vide), 0, (struct sockaddr *)&tab_socket[numero_pi], sizeAdr);

        if (resSend == -1)
        {
            perror("❌ Pconfig : problème avec le send to :");
            exit(1);
        }

        printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);

        printf("----- 🏆 Fin envois des données au Pi n°%d -----\n\n", numero_pi);
    } 

    printf("🏁 Toutes les 🧦 on bien été envoyées !\n");

    // -- Etape 9 : Fermer la socket (lorsqu'elle n'est plus utilisée)
    int cls = close(socket_pconfig);
    if (cls == -1)
    {
        perror("❌ Pconfig : problème avec le close :");
        exit(1);
    }
    printf("🚪 Pconfig : Fermture de la socket réussi.\n");

    printf("🙅 Pconfig : je termine.\n");

    return 0;
}