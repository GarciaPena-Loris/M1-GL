package m1.ingelo;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

public class Partie {
    int nombreBidons;
    Bidon[] bidons;
    int volumeRecherche;

    ArrayList<Stack<Commande>> historiquePartie;
    Stack<Commande> historiqueCommandes;

    Stack<Commande> reelHistoriqueCommandes;
    Stack<Commande> historiqueCommandesAnnulees;

    public Partie(int nombreBidons, int[] capacitesBidons, int volumeRecherche, ArrayList<Stack<Commande>> historiquePartie) {
        this.nombreBidons = nombreBidons;
        bidons = new Bidon[nombreBidons];
        for (int i = 0; i < nombreBidons; i++) {
            bidons[i] = new Bidon(i + 1, capacitesBidons[i]);
        }
        this.volumeRecherche = volumeRecherche;
        this.historiquePartie = historiquePartie;
        historiqueCommandes = new Stack<>();
        reelHistoriqueCommandes = new Stack<>();
        historiqueCommandesAnnulees = new Stack<>();
    }

    public String stringSolution() {
        StringBuilder res = new StringBuilder("[");
        for (Commande c : reelHistoriqueCommandes) {
            res.append(c.afficherAction()).append(", ");
        }
        res = new StringBuilder(res.substring(0, res.length() - 2));
        return res + "]";
    }

    public String afficherHistoriqueCommandes(Stack<Commande> historiqueCommandes) {
        if (historiqueCommandes.isEmpty()) {
            return "Aucune action effectuée.";
        }
        StringBuilder res = new StringBuilder("[");
        for (Commande c : historiqueCommandes) {
            res.append(c.afficherAction()).append(", ");
        }
        res = new StringBuilder(res.substring(0, res.length() - 2));
        return res + "]";
    }

    // Méthode pour exécuter une action donnée
    public void executerAction(Commande action) throws InterruptedException {
        action.executer();
        historiqueCommandes.push(action);
        reelHistoriqueCommandes.push(action);
        Thread.sleep(1000);
    }

    // Méthode pour annuler la dernière action
    public void annulerAction() throws InterruptedException {
        if (!historiqueCommandes.isEmpty()) {
            Commande derniereAction = historiqueCommandes.pop();
            System.out.println("Annulation de la dernière action (" + derniereAction.afficherAction() + ")...");
            Undo undo = new Undo(derniereAction);
            undo.executer();
            historiqueCommandesAnnulees.push(derniereAction);
            reelHistoriqueCommandes.push(undo);
            Thread.sleep(1000);
            System.out.println("✅ Action annulée !");
        } else {
            System.out.println("Aucune action à annuler.");
        }
    }

    // Méthode pour revenir à l'état précédent (avant la dernière action annulée)
    public void revenirEtatPrecedent() throws InterruptedException {
        if (!historiqueCommandesAnnulees.isEmpty()) {
            Commande derniereActionAnnulee = historiqueCommandesAnnulees.pop();
            System.out.println("Rétablissement de la dernière action annulée (" + derniereActionAnnulee.afficherAction() + ")...");
            derniereActionAnnulee.executer();
            historiqueCommandes.push(derniereActionAnnulee);
            reelHistoriqueCommandes.push(derniereActionAnnulee);
            Thread.sleep(1000);
            System.out.println("✅ Action rétablie !");
        } else {
            System.out.println("Aucune action à rétablir.");
        }
    }

    // Méthode pour rejouer une séquence d'actions
    public void rejouerActions(Stack<Commande> sequenceCommandeImpls) throws InterruptedException {
        for (Commande commande : sequenceCommandeImpls) {
            commande.afficherAction();
            Thread.sleep(1000);
            commande.executer();
        }
    }

    // Affiche l'état des bidons après chaque action
    public void afficherEtatBidons() {
        System.out.println("État des bidons (volume recherché : " + volumeRecherche + ")");
        for (int i = 0; i < nombreBidons; i++) {
            int remplissage = bidons[i].volume / 10;
            int capaciteBidon = bidons[i].capacite / 10;

            String bidonInfo = getBidonInfo(capaciteBidon, remplissage, i);
            System.out.println(bidonInfo);
        }
        System.out.println();
    }

    private String getBidonInfo(int capaciteBidon, int remplissage, int i) {
        StringBuilder representationBidon = new StringBuilder();
        for (int j = 0; j < capaciteBidon; j++) {
            if (j < remplissage) {
                representationBidon.append("\uD83D\uDCA7"); // Bidon rempli
            } else {
                representationBidon.append("_"); // Bidon vide
            }
        }

        return String.format("Bidon %d (%s) : %d / %d", (i + 1), representationBidon.toString(),
                bidons[i].volume, bidons[i].capacite);
    }

    // Méthode pour simuler le comportement interactif du joueur
    public Stack<Commande> jouer() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Debut de la partie, le volume recherché est : " + volumeRecherche + "\n");
        afficherEtatBidons();

        while (true) {
            System.out.println("1. Remplir un Bidon");
            System.out.println("2. Vider un Bidon");
            System.out.println("3. Transvaser d'un Bidon vers un Autre");
            System.out.println("4. Annuler la dernière action");
            System.out.println("5. Revenir à l'état précédent");
            System.out.println("6. Rejouer une séquence d'actions précédente");
            System.out.println("7. Afficher l'historique des actions");
            System.out.println("0. Quitter le jeu");

            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    System.out.print("Entrez le numéro du bidon à remplir : ");
                    int bidonARemplir = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Remplissage du bidon " + bidonARemplir + "...");
                    executerAction(new RemplirBidonCommandeImpl(bidons[bidonARemplir - 1]));
                    System.out.println("✅ Bidon " + bidonARemplir + " rempli !");
                    break;
                case 2:
                    System.out.print("Entrez le numéro du bidon à vider : ");
                    int bidonAVider = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Vidage du bidon " + bidonAVider + "...");
                    executerAction(new ViderBidonCommandeImpl(bidons[bidonAVider - 1]));
                    System.out.println("✅ Bidon " + bidonAVider + " vidé !");
                    break;
                case 3:
                    System.out.print("Entrez le numéro du bidon source : ");
                    int bidonSource = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Entrez le numéro du bidon cible : ");
                    int bidonCible = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Transvasage du bidon " + bidonSource + " vers le bidon " + bidonCible + "...");
                    executerAction(new TransvaserBidonCommandeImpl(bidons[bidonSource - 1], bidons[bidonCible - 1]));
                    System.out.println("✅ Bidon " + bidonSource + " transvasé dans le bidon " + bidonCible + " !");
                    break;
                case 4:
                    annulerAction();
                    break;
                case 5:
                    revenirEtatPrecedent();
                    break;
                case 6:
                    System.out.println("Choisissez une séquence d'actions à rejouer :");
                    if (historiquePartie.isEmpty()) {
                        System.out.println("Aucune séquence d'actions à rejouer.");
                        break;
                    }
                    int compteur = 1;
                    for (Stack<Commande> historiqueCommandeImpl : historiquePartie) {
                        System.out.println(compteur + ". " + afficherHistoriqueCommandes(historiqueCommandeImpl));
                        compteur++;
                    }
                    System.out.print("Votre choix : ");
                    int choixSequence = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Rejouage de la séquence d'actions " + choixSequence + "...");
                    rejouerActions(historiquePartie.get(choixSequence - 1));
                    System.out.println("✅ Séquence d'actions rejouée !");
                    break;
                case 7:
                    System.out.println("Historique des actions :");
                    System.out.println(afficherHistoriqueCommandes(reelHistoriqueCommandes));
                    System.out.println("Appuyez sur Entrée pour continuer...");
                    scanner.nextLine();
                    break;
                case 0:
                    System.out.println("Au revoir");
                    return historiqueCommandes;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une action valide.");
            }

            Thread.sleep(1000);
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

            // Vérifier si le volume recherché a été atteint
            if (bidons[0].volume == volumeRecherche) {
                afficherEtatBidons();
                System.out.println("\uD83C\uDFC6 Gagné avec la solution : " + stringSolution());
                Thread.sleep(5000);

                return this.historiqueCommandes;
            } else {
                afficherEtatBidons();
            }
        }
    }
}
