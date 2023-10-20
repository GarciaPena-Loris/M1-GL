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
    
    # Lancement du processus Pi avec les paramètres
    ./Pi "$ip_Pconfig" "$port_pconfig" "5" "$i" &

    # Ajoutez une pause facultative entre les lancements pour éviter la surcharge du système
    # sleep 1
done

# Attendez que tous les processus Pi se terminent (facultatif)
wait

echo "Tous les processus Pi ont été lancés."