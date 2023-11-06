package m1.archi.main;

import m1.archi.agence.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class client {
    private static String loginUser = "";
    private static String passwordUser = "";

    private static ArrayList<String> listeIdentifiantAgence = new ArrayList<String>();


    private static boolean creationUtilisateur() {
        System.out.println("Récupération de vos informations :");
        System.out.println("\uD83D\uDC64 Veuillez entrer votre login");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();
        System.out.println("\uD83D\uDD11 Veuillez entrer votre mot de passe");
        String motdepasse = scanner.nextLine();

        loginUser = login;
        passwordUser = motdepasse;

        return true;
    }

    private static void afficherListeAgence() throws InterruptedException {
        int i = 1;
        for (String identifiantAgence : listeIdentifiantAgence) {
            System.out.println("Agence n°" + i + " : " + identifiantAgence);
            Thread.sleep(300);
        }
    }

    private static void afficherDetailAgence(AgenceServiceIdentification proxyAgences) throws AgenceNotFoundException_Exception {
        System.out.println("\uD83C\uDFF7️Veuillez saisir le numéro de l'agence");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
        } else {
            String identifiantAgence = listeIdentifiantAgence.get(numeroAgence - 1);
            System.out.println(proxyAgences.afficherAgence(listeIdentifiantAgence.get(numeroAgence - 1)));
        }
    }

    private static boolean créationCompteAgence() throws MalformedURLException, UserAlreadyExistsException_Exception {
        System.out.println("\uD83C\uDFF7️Veuillez saisir le numéro de l'agence");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
            return false;
        } else {
            URL url = new URL("http://localhost:8090/agencesservice/" + numeroAgence + "/inscription");
            UserServiceInscriptionImplService agenceServiceIdentification = new UserServiceInscriptionImplService(url);
            UserServiceInscription proxyInscription = agenceServiceIdentification.getUserServiceInscriptionImplPort();

            return proxyInscription.inscription(loginUser, passwordUser);
        }
    }

    private static void AjoutAgenceAleatoire(AgenceServiceIdentification proxyAgence) {

        proxyAgence.randomAgence();
    }



    // - Afficher la liste des agences
    // - Afficher les détails d'une agence
    // - Ajouter une agence aléatoire

    // - Afficher la liste des hotels partenaires d'une agence
    // - Afficher les détails d'un hotel
    // - Ajouter un hotel partenaire à une agence aléatoire

    // - Recherche de chambres dans une agence
    // - Réserver des chambres dans une agence

    public static void main(String[] args) throws MalformedURLException, AgenceNotFoundException_Exception, InterruptedException {

        // CLI agence - hotel reservation

        // Recupere la liste des agences
        URL url = new URL("http://localhost:8090/agencesservice/identifiantAgences?wsdl");
        AgenceServiceIdentificationImplService agenceServiceIdentification = new AgenceServiceIdentificationImplService(url);
        AgenceServiceIdentification proxyAgences = agenceServiceIdentification.getAgenceServiceIdentificationImplPort();

        listeIdentifiantAgence = (ArrayList<String>) proxyAgences.getListeAgence();

        while (true) {
            if (loginUser == "" && passwordUser == "") {
                creationUtilisateur();
            }
            Thread.sleep(2000);
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("1. \uD83E\uDDFE Afficher la liste des agences");
            System.out.println("2. \uD83D\uDD0D Afficher les détails d'une agence");
            System.out.println("3. \uD83E\uDEAA Création de compte pour une agence");
            System.out.println("4. ➕ Ajouter une agence aléatoire");
            System.out.println("6. \uD83C\uDFE8 Afficher les détails d'un hotel");
            System.out.println("7. ➕ Ajouter un hotel partenaire aléatoire à une agence");
            System.out.println("8. \uD83D\uDECF Recherche de chambres avec une agence");
            System.out.println("9. \uD83D\uDDD3 Réserver des chambres avec une agence");
            System.out.println("10. ❌ Quitter");

            int choix = 0;
            try {
                Scanner scanner = new Scanner(System.in);
                choix = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un nombre");
            }
            switch (choix) {
                case 1: // Afficher la liste des agences
                    afficherListeAgence();
                    break;
                case 2: // Afficher les détails d'une agence
                    afficherDetailAgence(proxyAgences);
                    break;
                case 3: // Création de compte pour une agence
                    creationUtilisateur();
                    break;
                case 4: // Ajouter une agence aléatoire
                    System.out.println("Ajouter une agence aléatoire");
                    break;
                case 5: // Afficher la liste des hotels partenaires d'une agence
                    System.out.println("Afficher la liste des hotels partenaires d'une agence");
                    break;
                case 6: // Afficher les détails d'un hotel
                    System.out.println("Afficher les détails d'un hotel");
                    break;
                case 7: // Ajouter un hotel partenaire à une agence aléatoire
                    System.out.println("Ajouter un hotel partenaire à une agence aléatoire");
                    break;
                case 8: // Recherche de chambres dans une agence
                    System.out.println("Recherche de chambres dans une agence");
                    break;
                case 9: // Réserver des chambres dans une agence
                    System.out.println("Réserver des chambres dans une agence");
                    break;
                case 10: // Quitter
                    System.out.println("Quitter");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Veuillez entrer un nombre entre 1 et 10");
                    break;
            }
        }
    }
}
