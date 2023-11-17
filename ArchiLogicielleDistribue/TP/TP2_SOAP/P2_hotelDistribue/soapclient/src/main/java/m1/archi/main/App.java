package m1.archi.main;

import m1.archi.agence.*;
import m1.archi.main.swingInterface.Interface;

import javax.xml.datatype.DatatypeConfigurationException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws MalformedURLException, AgenceNotFoundException_Exception, InterruptedException, UserAlreadyExistsException_Exception, HotelNotFoundExceptionException, DatatypeConfigurationException, DateNonValideException_Exception, UserNotFoundException_Exception, ReservationProblemeException_Exception {
        // CLI agence - hotel reservation
        GestionnaireUser gestionnaireUser = new GestionnaireUser();
        UtilisationAgences utilisationAgences = new UtilisationAgences(gestionnaireUser);

        // Recupere la liste des agences
        URL url = new URL("http://localhost:8090/agencesservice/identifiantAgences?wsdl");
        AgenceServiceIdentificationImplService agenceServiceIdentification = new AgenceServiceIdentificationImplService(url);
        AgenceServiceIdentification proxyAgences = agenceServiceIdentification.getAgenceServiceIdentificationImplPort();

        while (true) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("1. \uD83E\uDDD1 Création ou connexion à un compte");
            System.out.println("2. \uD83E\uDDFE Afficher la liste des agences");
            System.out.println("3. \uD83D\uDD0D Afficher les détails d'une agence");
            System.out.println("4. \uD83E\uDEAA Création de compte pour une agence");
            System.out.println("5. \uD83C\uDFE8 Afficher les détails d'un hotel");
            System.out.println("6. \uD83D\uDD0D Afficher les reservations d'un hotel");
            System.out.println("7. \uD83D\uDECF Recherche de chambres avec une agence");
            System.out.println("8. \uD83D\uDCB5 Réserver des chambres avec une agence");
            System.out.println("9. \uD83D\uDCBB Lancer l'UI");
            System.out.println("0. ❌ Quitter");

            int choix = 0;
            try {
                Scanner scanner = new Scanner(System.in);
                choix = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un chiffre");
            }
            switch (choix) {
                case 1:
                    utilisationAgences.creationUtilisateur();
                    break;
                case 2: // Afficher la liste des agences
                    utilisationAgences.afficherListeAgence();
                    break;
                case 3: // Afficher les détails d'une agence
                    utilisationAgences.afficherDetailAgence(proxyAgences);
                    break;
                case 4: // Création de compte pour une agence
                    utilisationAgences.creationCompteAgence();
                    break;
                case 5: // Afficher les détails d'un hotel
                    utilisationAgences.afficherDetailHotel(proxyAgences);
                    break;
                case 6: // Afficher les reservations d'un hotel
                    utilisationAgences.afficherReservationHotel(proxyAgences);
                    break;
                case 7: // Recherche de chambres dans une agence
                    utilisationAgences.rechercherChambreCriteres(proxyAgences);
                    break;
                case 8: // Réserver des chambres dans une agence
                    utilisationAgences.reserverChambres();
                    break;
                case 9:
                    System.out.println("Demarrage de l'UI");
                    Interface ui = new Interface(proxyAgences, gestionnaireUser);
                    break;
                case 0: // Quitter
                    System.out.println("Au revoir !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Veuillez entrer un nombre entre 1 et 9");
                    break;
            }
        }
    }
}
