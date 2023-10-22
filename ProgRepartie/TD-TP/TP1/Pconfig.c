#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <string.h>
#include <ifaddrs.h>

#include "fonctionTCP.h"

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        printf("❌ Pconfig utilisation : %s port_pconfig nb_pi\n", argv[0]);
        exit(1);
    }

    char *port_pconfig = argv[1];
    int nb_pi = atoi(argv[2]);
    int nb_pi_recu = 0;

    struct sockaddr_in *tab_socket = malloc(sizeof(struct sockaddr_in) * nb_pi);

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
    while (nb_pi_recu < nb_pi)
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

        nb_pi_recu++;
    }

    printf("🏁 Tout les Pi on bien été receptionés, maintenant on distribut 🌐 :\n");

    for (int numero_pi = 0; numero_pi < nb_pi; numero_pi++)
    {
        printf("----- 📨 Envois des données au Pi n°%d -----\n", numero_pi + 1);

        int numero_pi_suivant;
        if (numero_pi == (nb_pi - 1))
        {
            numero_pi_suivant = 0;
        }
        else
        {
            numero_pi_suivant = numero_pi + 1;
        }

        struct sockaddr_in socket_pi;
        socket_pi.sin_family = AF_INET;
        socket_pi.sin_addr.s_addr = tab_socket[numero_pi].sin_addr.s_addr;
        socket_pi.sin_port = tab_socket[numero_pi].sin_port;

        socklen_t sizeAdr = sizeof(struct sockaddr_in);
        int resSend = sendto(socket_pconfig, &tab_socket[numero_pi_suivant], sizeof(tab_socket[numero_pi_suivant]), 0, (struct sockaddr *)&socket_pi, sizeAdr);

        if (resSend == -1)
        {
            perror("❌ Pconfig : problème avec le send to :");
            exit(1);
        }

        char *ip_pi_suivant = inet_ntoa(tab_socket[numero_pi_suivant].sin_addr);
        int port_pi_suivant = ntohs(tab_socket[numero_pi_suivant].sin_port);

        printf("\t🧮 Numéro du Pi suivant : %d\n", numero_pi_suivant + 1);
        printf("\t📮 Ip du Pi suivant : %s\n", ip_pi_suivant);
        printf("\t🐖 Port du Pi suivant : %d\n", port_pi_suivant);
        printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);

        printf("----- 🏆 Fin envois des données au Pi n°%d -----\n\n", numero_pi + 1);
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