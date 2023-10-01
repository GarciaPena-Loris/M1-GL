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

int main(int argc, char *argv[]) {
    if (argc != 3){
        printf("❌ Pconfig utilisation : %s port_pconfig nb_pi\n", argv[0]);
        exit(1);
    }

    char* port_pconfig = argv[1];
    int nb_pi = atoi(argv[2]);
    int nb_pi_recu = 0;

    in_addr_t* tab_ip_pi = malloc(sizeof(in_addr_t) * nb_pi);
    in_port_t* tab_port_pi = malloc(sizeof(in_port_t) * nb_pi);

    // -- Etape 1 : Création socket pconfig
    int socket_pconfig = socket(PF_INET, SOCK_DGRAM, 0);
    if (socket_pconfig == -1){
        perror("❌ Pconfig : problème création socket :");
        exit(1);
    }
    printf("✅ Pconfig : Création de la socket réussie.\n");

    // -- Etape 2 : Nommage la socket pconfig
    struct sockaddr_in ss;
    ss.sin_family = AF_INET;
    ss.sin_addr.s_addr = INADDR_ANY;
    ss.sin_port = htons(atoi(port_pconfig));

    int res = bind(socket_pconfig, (struct sockaddr*)&ss, sizeof(ss));

    if (res == -1){
        perror("❌ Pconfig : problème de bind :");
        exit(1); 
    }
    printf("✅ Pconfig : Nommage de la socket réussi.\n");

    //-- Etape 2 bis : affichage de l'ip
      struct ifaddrs *ifap, *ifa;
      struct sockaddr_in *sa;
      char *addr;

      getifaddrs(&ifap);
      for (ifa = ifap; ifa; ifa = ifa->ifa_next) {
         if (ifa->ifa_addr && ifa->ifa_addr->sa_family==AF_INET) {
               sa = (struct sockaddr_in *) ifa->ifa_addr;
               addr = inet_ntoa(sa->sin_addr);
               printf("🌐 Address IP Pconfig : %s\n", addr);
         }
      }
      freeifaddrs(ifap);


    // -- Etape 5 : Reception des messages des Pi
    while(nb_pi_recu < nb_pi) {
        printf("📩 En attente de message d'un Pi...\n");
        
        struct sockaddr_in sockPi;
        socklen_t lgAdr = sizeof(struct sockaddr_in);
        char messageRecu[20];

        ssize_t resRecv = recvfrom(socket_pconfig, &messageRecu, sizeof(messageRecu), 0, (struct sockaddr *) &sockPi, &lgAdr);
        if (resRecv == -1) {
            perror("❌ Pconfig : problème avec le recvFrom :");
            exit(1);
        }
        printf("📧 Message reçus : %s\n", messageRecu);

        printf("---\n");

        int numero_pi = atoi(messageRecu);
        int ip_pi = atoi(inet_ntoa(sockPi.sin_addr));
        int port_pi = (int) htons(sockPi.sin_port);

        printf("\tNuméro du Pi : %d\n", numero_pi);
        printf("\tIp du Pi : %d\n", ip_pi);
        printf("\tPort du Pi : %d\n", port_pi);

        // Affectation dans les tableaux
        tab_ip_pi[numero_pi-1] = sockPi.sin_addr.s_addr;
        tab_port_pi[numero_pi-1] = sockPi.sin_port;

        nb_pi_recu++;
    }

    for (int i = 0; i < nb_pi; i++) {
        printf("📨 Envois des données au Pi n°%d...\n", i+1);

        int numero_pi;
        if (i == (nb_pi-1)) {
            numero_pi = 0;
        }
        else {
            numero_pi = i+1;
        }

        struct sockaddr_in socket_pi;
        socket_pi.sin_family = AF_INET;
        socket_pi.sin_addr.s_addr = tab_ip_pi[numero_pi];
        socket_pi.sin_port = tab_port_pi[numero_pi];

        printf("📨 Envois de l'ip du suivant du Pi n°%d...\n", i+1);

        in_addr_t ip_pi_suivant = tab_ip_pi[numero_pi];

        socklen_t sizeAdr = sizeof(struct sockaddr_in);
        int resSend = sendto(socket_pconfig, &ip_pi_suivant, sizeof(ip_pi_suivant), 0, (struct sockaddr *) &socket_pi, sizeAdr);

        if (resSend == -1) {
            perror("❌ Pconfig : problème avec le send to :");
            exit(1);
        }
        printf("✅ Nombre d'octets envoyés : %d\n", resSend);

        printf("📨 Envois du port du suivant du Pi n°%d...\n", i+1);

        in_port_t port_pi_suivant = tab_port_pi[numero_pi];
        resSend = sendto(socket_pconfig, &port_pi_suivant, sizeof(port_pi_suivant), 0, (struct sockaddr *) &socket_pi, sizeAdr);

        if (resSend == -1) {
            perror("❌ Pconfig : problème avec le send to :");
            exit(1);
        }
        printf("✅Nombre d'octets envoyés : %d\n", resSend);

        printf("🏆 Fin envois des données au Pi n°%d...\n", i+1);
    }


    // -- Etape 9 : Fermer la socket (lorsqu'elle n'est plus utilisée)
    int cls = close(socket_pconfig);
    if (cls == -1) {
        perror("❌ Pconfig : problème avec le close :");
        exit(1);
    }
    printf("✅ Pconfig : Fermture de la socket réussi.\n");

    printf("🙅 Pconfig : je termine.\n");

    return 0;
}