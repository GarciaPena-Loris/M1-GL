Communication entre Pconfig et Pi

Étape 1 : Envoi des informations de Pi à Pconfig

- Le fichier Pconfig est exécuté sur le port sur lequel il doit écouter (port_pconfig) et le fichier décrivant le réseau (nomFichier).
- Pconfig ouvre une socket en mode Datagram (UDP) et l'associe à l'adresse IP (ANY) et au port spécifiés.
- Pconfig effectue une première lecture du fichier pour déterminer combien il y a de processus dans le réseau. Ensuite, il fait une boucle de réception jusqu'à avoir reçu les informations de tous les Pi.
- Chaque instance de Pi envoie ces informations à Pconfig. Ces informations sont de type `struct infosPi`, elles comprennent le numéro de Pi (numeroPi) et la structure de socket TCP du Pi (struct sockaddr_in) préalablement mise en écoute :
        struct infosPi
        {
            int numeroPi; // Numéro de chaque Pi
            struct sockaddr_in structSocketPi; // Descripteur de sa socket TCP
        };
- Pconfig stocke dans deux tableaux les sockets UDP sur lesquelles les Pi ont envoyé leurs informations et les descripteurs de socket TCP avec lesquels les Pi vont communiquer entre eux.

Étape 2 : Envoi des nombres de voisins par Pconfig

- Après avoir reçu les informations de tous les Pi, Pconfig continue la lecture du fichier pour déterminer le nombre de voisins pour chaque Pi, c'est-à-dire le nombre d'accepts et de connects.
- Pconfig envoie le nombre de voisins à chaque Pi à l'aide de la socket UDP de chaque Pi. Ces informations sont encapsulées dans une `struct compteurVoisins` :
        struct compteurVoisins
        {
            int nombreAccept;  // Nombre de voisins que le Pi doit accepter
            int nombreConnect; // Nombre de voisins auxquels le Pi doit se connecter
        };
- Chaque Pi reçoit cette structure qui contient le nombre d'accepts et de connects qu'il doit gérer.

Étape 3 : Envoi des adresses de socket des voisins par Pconfig

- Pconfig effectue une deuxième lecture du fichier réseau pour obtenir les informations sur les voisins de chaque Pi. Lorsqu'il obtient une paire d'identifiants de Pi (ex : Pi1 Pi2), Pconfig sait que Pi1 et Pi2 sont voisins et qu'il doit envoyer les informations de la socket de Pi2 à Pi1. Pi1 sera placé en tant que client et Pi2 en tant que serveur.
- Pconfig envoie la structure de socket TCP de Pi2 à Pi1 sous forme de `struct sockaddr_in`, qui contient l'adresse IP et le port du Pi.
- Pconfig envoie toutes les structures des voisins d'un même Pi avant d'attendre de recevoir une confirmation (de type `char`). Par exemple, si le Pi numéro 20 doit se connecter à 10 autres Pi, Pconfig lui envoie les structures de ces 10 voisins, puis attend la confirmation (de type `char`).
- De leur côté, chaque Pi sait combien il doit recevoir d'informations (nombreConnect), il fait donc une boucle de réceptions jusqu'à avoir tout reçu. Une fois qu'il a fini de recevoir tous les voisins auxquels il doit se connecter, il se connecte (à l'aide de la fonction `connect` de TCP) à tous ces voisins, puis envoie une confirmation à Pconfig (de type `char`) via la socket UDP.
- Lorsqu'un Pi fait un `connect`, il ajoute la socket nouvellement créée à son tableau de sockets (tabSocketsVoisins), qui est un tableau de `int`.

Étape 4 : Attente de la confirmation par Pconfig

- Pconfig attend la confirmation de chaque Pi. Une fois reçue, il sait que tous les Pi ont terminé la connexion à leurs voisins. Il continue donc de lire le fichier et d'envoyer les informations au Pi suivant.
- Une fois que tous les Pi ont envoyé leurs connexions, et que donc Pconfig a reçu toutes les confirmations, il informe tous les Pi pour qu'ils puissent commencer à établir leurs connexions. Il envoie donc une nouvelle confirmation (dans l'autre sens cette fois) à chaque Pi. Cette confirmation est également un caractère de type `char`.
- Chaque Pi attend donc de recevoir cette confirmation (en utilisant `recvfrom` en UDP). Une fois la confirmation reçue, il accepte (à l'aide de la fonction `accept` de TCP) toutes les connexions des Pi qui lui ont envoyé une demande (nombreAccept de la structure `compteurVoisins` précédemment reçue).
- Lorsqu'un Pi fait un `accept`, il ajoute la socket retournée par l'accept à son tableau de sockets (tabSocketsVoisins).

Communication entre Pi et Pi

- Une fois que tous les `accepts` sont terminés, chaque Pi peut maintenant communiquer avec ses voisins via son tableau de sockets (tabSocketsVoisins).
- Chaque Pi peut maintenant communiquer avec les autres Pi en utilisant les fonctions `send` et `recv` de TCP.

Cela résume les principales étapes de la communication entre Pconfig et Pi.
