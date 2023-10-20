#!/bin/bash

# Récupérer les paramètres en ligne de commande
ip_Pconfig="$1"
port_pconfig="$2"
nombre_de_Pi="$3"

# Vérifier si les paramètres sont présents
if [ -z "$ip_Pconfig" ] || [ -z "$port_pconfig" ] || [ -z "$nombre_de_Pi" ]; then
    echo "Utilisation : $0 <ip_Pconfig> <port_pconfig> <nombre_de_Pi>"
    exit 1
fi

# Boucle pour lancer les processus Pi en utilisant les paramètres
for ((i=1; i<=$nombre_de_Pi; i++))
do
    # Générez un nombre aléatoire entre 1 et 10 et affectez-le à une variable
    random_number=$(shuf -i 1-10 -n 1)

    # Lancement du processus Pi avec les paramètres
    ./Pi "$ip_Pconfig" "$port_pconfig" "$random_number" "$i" &

    # Ajoutez une pause facultative entre les lancements pour éviter la surcharge du système
    # sleep 1
done

# Attendez que tous les processus Pi se terminent (facultatif)
wait

echo "Tous les processus Pi ont été lancés."
