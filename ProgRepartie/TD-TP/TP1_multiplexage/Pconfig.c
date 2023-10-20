#include <netinet/in.h>
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

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        printf("❌ Pconfig utilisation : %s port_pconfig nomFichier\n", argv[0]);
        exit(1);
    }

    char *port_pconfig = argv[1];
    char *nomFichier = argv[2];

    printf("📖 Debut premiere lecture du ficier.\n");

    // -- Etape 1 : Recupere nombre Pi dans le fichier
    FILE *file;
    char line[256];
    int nombrePi, nombreArretes;

    // Ouvrir le fichier en mode lecture
    file = fopen(nomFichier, "r");
    if (file == NULL)
    {
        perror("❌ Pconfig : erreur lors de l'ouverture du fichier");
        return 1;
    }

    // Lire les lignes jusqu'à atteindre la ligne "p edge X Y"
    while (fgets(line, sizeof(line), file) != NULL)
    {
        if (sscanf(line, "p edge %d %d", &nombrePi, &nombreArretes) == 2)
        {
            break;
        }
    }

    if (nombrePi <= 0)
    {
        perror("❌ Pconfig : nombrePi n'a pas de valeur terminer.");
        return 1;
    }

    printf("🧮 Il y a %d Pi dans notre réseau.\n\n", nombrePi);

    // -- Etape 2 : Preparation recuperation socketAdresse de tous les Pi.
    struct sockaddr_in *tabSocketAdressTCP = malloc(sizeof(struct sockaddr_in) * nombrePi);
    struct sockaddr_in *tabSocketAdressUDP = malloc(sizeof(struct sockaddr_in) * nombrePi);

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
    int nombrePiRecu = 0;
    while (nombrePiRecu < nombrePi)
    {
        printf("📩 En attente du message d'un Pi...\n");

        struct sockaddr_in socketAdressPi;
        socklen_t lgAdr = sizeof(struct sockaddr_in);

        struct infosPi info;

        ssize_t resRecv = recvfrom(socketPconfig, &info, sizeof(info), 0,
                                   (struct sockaddr *)&socketAdressPi, &lgAdr);
        if (resRecv == -1)
        {
            perror("❌ Pconfig : problème avec le recvFrom :");
            exit(1);
        }

        tabSocketAdressUDP[info.numeroPi - 1] = socketAdressPi;

        printf("\t📧 Information Pi reçus : \n");

        struct sockaddr_in structSocketPi = info.structSocketPi;
        char *ipPiUDP = inet_ntoa(socketAdressPi.sin_addr);
        int portPiUDP = ntohs(socketAdressPi.sin_port);

        char *ipPiTCP = inet_ntoa(info.structSocketPi.sin_addr);
        int portPiTCP = ntohs(info.structSocketPi.sin_port);

        printf("\t🌐 Adresse UDP Pi n°%d : %s:%d\n", info.numeroPi, ipPiUDP, portPiUDP);
        printf("\t🌏 Adresse TCP Pi n°%d : %s:%d\n", info.numeroPi, ipPiTCP, portPiTCP);
        printf("\t---\n");

        // Affectation dans les tableaux
        tabSocketAdressTCP[info.numeroPi - 1] = structSocketPi;

        nombrePiRecu++;
    }

    printf("🏁 Tout les Pi on bien été receptionés, maintenant on distribut 🛰️ :\n");

    // -- Etape 4 : Recupere nombre d'Accept et de Connect.

    int *tabAccept = malloc(sizeof(int) * nombrePi);
    int *tabConnect = malloc(sizeof(int) * nombrePi);

    // On initiaise les tableaux a 0
    for (int i = 0; i < nombrePi; i++)
    {
        tabAccept[i] = 0;
        tabConnect[i] = 0;
    }

    // On lie le fichier une premiere fois
    while (fgets(line, sizeof(line), file) != NULL)
    {
        int numPiClient, numPiServeur;

        if (sscanf(line, "e %d %d", &numPiClient, &numPiServeur) == 2)
        {
            tabAccept[numPiServeur - 1]++;
            tabConnect[numPiClient - 1]++;
        }
    }

    // Fermer le fichier
    fclose(file);

    // -- Etape 5 : Envois de ces nombre a chaque Pi.
    printf("👋 Début d'envoi du nombre de voisin au Pi\n");

    socklen_t sizeAdr = sizeof(struct sockaddr_in);

    for (int i = 0; i < nombrePi; i++)
    {
        struct compteurVoisins compteurVoisins;
        compteurVoisins.nombreAccept = tabAccept[i];
        compteurVoisins.nombreConnect = tabConnect[i];

        printf("\t📨 Envois nombre voisins au Pi n°%d\n", i);
        int resSend = sendto(socketPconfig, &compteurVoisins, sizeof(struct compteurVoisins),
                             0, (struct sockaddr *)&tabSocketAdressUDP[i], sizeAdr);
        if (resSend == -1)
        {
            perror("\t❌ Pi : problème avec le premier send to :");
            exit(1);
        }
        // printf("\t✅ Nombre d'octets envoyés : %d\n", resSend);
    }

    printf("✅ Fin d'envoi du nombre de voisin au Pi\n");

    printf("📖 Lecture et envois des struct d'adresse au Pi\n");

    // -- Etape 6 : Envoyer les voisins a chaque Pi
    file = fopen(nomFichier, "r");
    if (file == NULL)
    {
        perror("❌ Pconfig : erreur lors de la réouverture du fichier");
        return 1;
    }

    char conf;
    struct sockaddr_in structSocketExpediteurUDP;
    int compteur = 1;
    int numPiClient, numPiServeur;

    while (fgets(line, sizeof(line), file) != NULL)
    {
        if (sscanf(line, "e %d %d", &numPiClient, &numPiServeur) == 2)
        {

            printf("-- 📨 Envois des données au Pi n°%d --\n", numPiClient);

            int resSend = sendto(socketPconfig, &tabSocketAdressTCP[numPiServeur - 1], sizeof(tabSocketAdressTCP[numPiServeur - 1]),
                                 0, (struct sockaddr *)&tabSocketAdressUDP[numPiClient - 1], sizeAdr);
            if (resSend == -1)
            {
                perror("\t❌ Pi : problème avec le send to :");
                exit(1);
            }

            char *ipPi_actuel = inet_ntoa(tabSocketAdressUDP[numPiClient - 1].sin_addr);
            int portPi_actuel = ntohs(tabSocketAdressUDP[numPiClient - 1].sin_port);

            char *ipPi_voisin = inet_ntoa(tabSocketAdressTCP[numPiServeur - 1].sin_addr);
            int portPi_voisin = ntohs(tabSocketAdressTCP[numPiServeur - 1].sin_port);

            printf("\t🌐 Pi n°%d (%s:%d) à le Pi n°%d (%s:%d) comme voisin.\n",
                   numPiClient, ipPi_actuel, portPi_actuel, numPiServeur, ipPi_voisin, portPi_voisin);

            printf("\tLe pi n°%d dois faire %d connect :\n", numPiClient, tabConnect[numPiClient - 1]);

            if (tabConnect[numPiClient - 1] == compteur)
            {
                printf("\t⏳ Attente conformation du Pi n°%d\n", numPiClient);

                int resRecv = recvfrom(socketPconfig, &conf, sizeof(conf),
                                       0, (struct sockaddr *)&structSocketExpediteurUDP, &sizeAdr);
                if (resRecv == -1)
                {
                    perror("\t❌ Pi : problème avec le recvFrom :");
                    exit(1);
                }
                printf("\t✅ Le Pi n°%d a finis ses connects.\n", numPiClient);
                compteur = 0;
            }
            compteur++;
        }
    }

    // Fermer le fichier
    fclose(file);

    printf("✅ Tous les voisins ont été envoyé ! Maintenant on les informes\n");

    for (int i = 0; i < nombrePi; i++)
    {

        printf("\t📨 Envois confirmation au Pi n°%d\n", i + 1);
        int resSend = sendto(socketPconfig, &conf, sizeof(conf),
                             0, (struct sockaddr *)&tabSocketAdressUDP[i], sizeAdr);
        if (resSend == -1)
        {
            perror("\t❌ Pi : problème avec le premier send to :");
            exit(1);
        }
    }

    printf("\t✅ Fin d'envois de la confirmation. Le reseau est opérationnel 🌐\n");

    // -- Etape 7 : Fermer la socket (lorsqu'elle n'est plus utilisée)
    int cls = close(socketPconfig);
    if (cls == -1)
    {
        perror("❌ Pconfig : problème avec le close :");
        exit(1);
    }
    free(tabAccept);
    free(tabConnect);

    printf("🚪 Pconfig : Fermture de la socket réussi.\n");

    printf("🙅 Pconfig : je termine.\n");

    return 0;
}