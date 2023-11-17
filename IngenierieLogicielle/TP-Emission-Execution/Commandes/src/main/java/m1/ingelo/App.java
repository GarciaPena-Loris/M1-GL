package m1.ingelo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class App {

    public static boolean existeSolution(int[] capacitesBidons, int volumeRecherche) {
        int pgcd = capacitesBidons[0];
        for (int i = 1; i < capacitesBidons.length; i++) {
            pgcd = pgcd(pgcd, capacitesBidons[i]);
        }
        return volumeRecherche % pgcd != 0;
    }

    private static int pgcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return pgcd(b, a % b);
    }
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Stack<Commande>> historiquePartie = new ArrayList<>();
        Random random = new java.util.Random();

        // Creation de fausse historique de commandes
        Stack<Commande> historiqueCommandes = new Stack<>();
        historiqueCommandes.push(new RemplirBidonCommandeImpl(new Bidon(1, 10)));
        historiqueCommandes.push(new TransvaserBidonCommandeImpl(new Bidon(1, 10), new Bidon(2, 20)));
        historiqueCommandes.push(new RemplirBidonCommandeImpl(new Bidon(1, 10)));
        historiqueCommandes.push(new TransvaserBidonCommandeImpl(new Bidon(1, 10), new Bidon(2, 20)));


        System.out.println("Bienvenue dans le jeu des bidons !");
        while (true) {
            System.out.println("1. Afficher les règles");
            System.out.println("2. Jouer");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            Scanner sc = new Scanner(System.in);
            int premierChoix = sc.nextInt();
            if (premierChoix == 0) {
                System.out.println("Au revoir !");
                break;
            }
            switch (premierChoix) {
                case 1:
                    System.out.println("\nRègles du jeu :");
                    System.out.println("Le but du jeu est de trouver le volume d'eau recherché en effectuant des actions sur les bidons.");
                    System.out.println("Les actions possibles sont :");
                    System.out.println("\t-Remplir un bidon");
                    System.out.println("\t-Vider un bidon");
                    System.out.println("\t-Transvaser le contenu d'un bidon dans un autre bidon");
                    System.out.println("Bonne chance !");
                    Thread.sleep(5000);
                    break;
                case 2:
                    System.out.println("\nVeuillez choisir un niveau de difficulté :");
                    System.out.println("1. Facile");
                    System.out.println("2. Moyen");
                    System.out.println("3. Difficile");
                    System.out.println("0. Quitter");
                    System.out.print("Votre choix : ");

                    int choix = sc.nextInt();
                    if (choix == 0) {
                        System.out.println("Au revoir !");
                        break;
                    }
                    int nombreBidons = 0;
                    int volumeRecherche = 0;
                    int[] capacitesBidons = null;
                    switch (choix) {
                        case 1: // Facile :
                            do {
                                nombreBidons = random.nextInt(2) + 2;
                                capacitesBidons = new int[nombreBidons];
                                volumeRecherche = (random.nextInt(5) + 3) * 10;
                                for (int i = 0; i < nombreBidons; i++) {
                                    do {
                                        capacitesBidons[i] = (random.nextInt(5) + 5) * 10;
                                    }
                                    while (capacitesBidons[i] == volumeRecherche);
                                }
                            }
                            while (existeSolution(capacitesBidons, volumeRecherche));
                            break;
                        case 2:
                            do {
                                nombreBidons = random.nextInt(3) + 4;
                                capacitesBidons = new int[nombreBidons];
                                volumeRecherche = (random.nextInt(5) + 5) * 10;
                                for (int i = 0; i < nombreBidons; i++) {
                                    do {
                                        capacitesBidons[i] = (random.nextInt(10) + 8) * 10;
                                    }
                                    while (capacitesBidons[i] == volumeRecherche);
                                }
                            }
                            while (existeSolution(capacitesBidons, volumeRecherche));
                            break;
                        case 3:
                            do {
                                nombreBidons = random.nextInt(4) + 6;
                                capacitesBidons = new int[nombreBidons];
                                volumeRecherche = (random.nextInt(5) + 2) * 10;
                                for (int i = 0; i < nombreBidons; i++) {
                                    do {
                                        capacitesBidons[i] = (random.nextInt(20) + 10) * 10;
                                    }
                                    while (capacitesBidons[i] == volumeRecherche);
                                }
                            }
                            while (existeSolution(capacitesBidons, volumeRecherche));
                            break;
                        default:
                            System.out.println("Choix invalide !");
                            continue;
                    }
                    Partie partie = new Partie(nombreBidons, capacitesBidons, volumeRecherche, historiquePartie);
                    try {
                        historiquePartie.add(partie.jouer());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    }
}
