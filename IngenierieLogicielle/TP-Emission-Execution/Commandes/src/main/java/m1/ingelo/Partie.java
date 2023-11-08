package m1.ingelo;

import java.util.Stack;
import java.util.Scanner;

public class Partie {
    int nombreBidons;
    Bidon[] bidons;
    int volumeRecherche;
    Stack<Commande> historiqueCommandes;
    Stack<Commande> historiqueCommandesAnnulees;

    public Partie(int nombreBidons, int[] capacitesBidons, int volumeRecherche) {
        this.nombreBidons = nombreBidons;
        bidons = new Bidon[nombreBidons];
        for (int i = 0; i < nombreBidons; i++) {
            bidons[i] = new Bidon(i + 1, capacitesBidons[i]);
        }
        this.volumeRecherche = volumeRecherche;
        historiqueCommandes = new Stack<>();
        historiqueCommandesAnnulees = new Stack<>();
    }

    public String stringSolution() {
        String res = "[";
        for (Commande c : historiqueCommandes) {
            res += c.afficherAction() + ", ";
        }
        res.substring(0, res.length() - 2);
        return res + "]";
    }

    // Méthode pour exécuter une action donnée
    public void executerAction(Commande action) throws InterruptedException {
        historiqueCommandes.push(action);
        action.executer();

        Thread.sleep(1000);
    }

    // Méthode pour annuler la dernière action
    public void annulerAction() throws InterruptedException {
        Thread.sleep(1000);
        if (!historiqueCommandes.isEmpty()) {
            Commande derniereAction = historiqueCommandes.pop();
            derniereAction.annuler();
            historiqueCommandesAnnulees.push(derniereAction);
            System.out.println("Action annulée!");
        } else {
            System.out.println("Aucune action à annuler.");
        }

    }

    // Méthode pour revenir à l'état précédent (avant la dernière action annulée)
    public void revenirEtatPrecedent() throws InterruptedException {
        Thread.sleep(1000);
        if (!historiqueCommandesAnnulees.isEmpty()) {
            Commande derniereActionAnnulee = historiqueCommandesAnnulees.pop();
            derniereActionAnnulee.executer();
            historiqueCommandes.push(derniereActionAnnulee);
        } else {
            System.out.println("Aucune action à rétablir.");
        }
    }

    // Méthode pour rejouer une séquence d'actions
    public void rejouerActions(Stack<Commande> sequenceCommandes) {
        for (Commande commande : sequenceCommandes) {
            commande.executer();
        }
    }

    // Affiche l'état des bidons après chaque action
    public void afficherEtatBidons() {
        System.out.println("État des bidons :");
        for (int i = 0; i < nombreBidons; i++) {
            int pourcentageRemplissage = (bidons[i].volume * 100) / bidons[i].capacite;

            StringBuilder representationBidon = new StringBuilder();
            for (int j = 0; j < 10; j++) {
                if (j < pourcentageRemplissage / 10) {
                    representationBidon.append("\uD83D\uDCA7"); // Bidon rempli
                } else {
                    representationBidon.append("_"); // Bidon vide
                }
            }

            String bidonInfo = String.format("Bidon %d (%s) : %d / %d", (i + 1), representationBidon.toString(),
                    bidons[i].volume, bidons[i].capacite);
            System.out.println(bidonInfo);
        }
        System.out.println();
    }

    // Méthode pour simuler le comportement interactif du joueur
    public Stack<Commande> jouer() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenue dans le jeu des bidons !");
        afficherEtatBidons();

        while (true) {
            System.out.println("1. Remplir un Bidon");
            System.out.println("2. Vider un Bidon");
            System.out.println("3. Transvaser d'un Bidon vers un Autre");
            System.out.println("4. Annuler la dernière action");
            System.out.println("5. Revenir à l'état précédent");
            System.out.println("0. Quitter le jeu");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Entrez le numéro du bidon à remplir : ");
                    int bidonARemplir = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Remplissage du bidon " + bidonARemplir + "...");
                    executerAction(new RemplirBidonCommande(bidons[bidonARemplir - 1]));
                    System.out.println("Bidon " + bidonARemplir + " rempli !");
                    break;
                case 2:
                    System.out.print("Entrez le numéro du bidon à vider : ");
                    int bidonAVider = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Vidage du bidon " + bidonAVider + "...");
                    executerAction(new ViderBidonCommande(bidons[bidonAVider - 1]));
                    System.out.println("Bidon " + bidonAVider + " vidé !");
                    break;
                case 3:
                    System.out.print("Entrez le numéro du bidon source : ");
                    int bidonSource = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Entrez le numéro du bidon cible : ");
                    int bidonCible = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Transvasage du bidon " + bidonSource + " vers le bidon " + bidonCible + "...");
                    executerAction(new TransvaserBidonCommande(bidons[bidonSource - 1], bidons[bidonCible - 1]));
                    System.out.println("Bidon " + bidonSource + " transvasé dans le bidon " + bidonCible + " !");
                    break;
                case 4:
                    System.out.println("Annulation de la dernière action...");
                    annulerAction();
                    break;
                case 5:
                    System.out.println("Rétablissement de la dernière action annulée...");
                    revenirEtatPrecedent();
                    System.out.println("Action rétablie!");
                    break;
                case 0:
                    System.out.println("Au revoir!");
                    return historiqueCommandes;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une action valide.");
            }

            Thread.sleep(1000);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

            // Vérifier si le volume recherché a été atteint
            if (bidons[0].volume == volumeRecherche) {
                System.out.println("Gagné avec la solution : " + stringSolution());
                return this.historiqueCommandes;
            }
            else {
                afficherEtatBidons();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int[] capacitesBidons = { 200, 100, 50 };
        Partie p = new Partie(3, capacitesBidons, 150);
        p.jouer();
    }
}
