package m1.archi.main;

import m1.archi.agence.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

public class client {
    private static String loginUser = "";
    private static String passwordUser = "";

    private static ArrayList<String> listeIdentifiantAgence = new ArrayList<String>();

    private static void creationUtilisateur() {
        System.out.println("Récupération de vos informations :");
        System.out.println("\uD83D\uDC64 Veuillez entrer votre login");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();
        System.out.println("\uD83D\uDD11 Veuillez entrer votre mot de passe");
        String motdepasse = scanner.nextLine();

        loginUser = login;
        passwordUser = motdepasse;

    }

    private static void afficherListeAgence() throws InterruptedException {
        int compteur = 1;
        for (String identifiantAgence : listeIdentifiantAgence) {
            System.out.println("Agence n°" + compteur + " : " + identifiantAgence);
            compteur++;
            Thread.sleep(300);
        }
        System.out.println("\nAppuyer sur Entrer pour continuer...");
        new java.util.Scanner(System.in).nextLine();
    }

    private static void afficherDetailAgence(AgenceServiceIdentification proxyAgences) throws AgenceNotFoundException_Exception {
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'agence");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
        } else {
            String identifiantAgence = listeIdentifiantAgence.get(numeroAgence - 1);
            System.out.println(proxyAgences.afficherAgence(listeIdentifiantAgence.get(numeroAgence - 1)));
        }
        System.out.println("\nAppuyer sur Entrer pour continuer...");
        new java.util.Scanner(System.in).nextLine();
    }

    private static void créationCompteAgence() throws MalformedURLException, UserAlreadyExistsException_Exception {
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'agence");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
        } else {
            URL url = new URL("http://localhost:8090/agencesservice/" + listeIdentifiantAgence.get(numeroAgence - 1) + "/inscription");
            UserServiceInscriptionImplService agenceServiceIdentification = new UserServiceInscriptionImplService(url);
            UserServiceInscription proxyInscription = agenceServiceIdentification.getUserServiceInscriptionImplPort();

            try {
                proxyInscription.inscription(loginUser, passwordUser);
                System.out.println("✅ Votre compte a bien été créé.");
            } catch (UserAlreadyExistsException_Exception e) {
                System.out.println("⚠\uFE0F Vous avez déjà un compte dans cette agence.");
            } catch (Exception e) {
                System.out.println("⚠\uFE0F Erreur lors de la création de votre compte.");
            }
        }
        System.out.println("\nAppuyer sur Entrer pour continuer...");
        new java.util.Scanner(System.in).nextLine();
    }


    private static void afficherDetailHotel(AgenceServiceIdentification proxyAgences) throws AgenceNotFoundException_Exception, HotelNotFoundExceptionException {
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'agence");
        Scanner scanner = new Scanner(System.in);
        String identifiantAgence;
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
        } else {
            identifiantAgence = listeIdentifiantAgence.get(numeroAgence - 1);
        }
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'hotel");
        int numeroHotel = scanner.nextInt();
        int nombreHotelAgence = proxyAgences.getListeIdentifiantHotelsPartenaire(listeIdentifiantAgence.get(numeroAgence - 1)).size();

        if (numeroHotel < 1 || numeroHotel > nombreHotelAgence) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + nombreHotelAgence);
        } else {
            System.out.println(proxyAgences.afficherHotel(proxyAgences.getListeIdentifiantHotelsPartenaire(listeIdentifiantAgence.get(numeroAgence - 1)).get(numeroHotel - 1)));
        }
        System.out.println("\nAppuyer sur Entrer pour continuer...");
        new java.util.Scanner(System.in).nextLine();
    }

    private static void afficherReservationHotel(AgenceServiceIdentification proxyAgences) throws AgenceNotFoundException_Exception, HotelNotFoundExceptionException {
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'agence");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
        } else {
            System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'hotel");
            int numeroHotel = scanner.nextInt();
            int nombreHotelAgence = proxyAgences.getListeIdentifiantHotelsPartenaire(listeIdentifiantAgence.get(numeroAgence - 1)).size();
            if (numeroHotel < 1 || numeroHotel > proxyAgences.getListeIdentifiantHotelsPartenaire(listeIdentifiantAgence.get(numeroAgence - 1)).size()) {
                System.out.println("Veuillez entrer un nombre entre 1 et " + nombreHotelAgence);
            } else {
                System.out.println(proxyAgences.afficherReservationsHotel(proxyAgences.getListeIdentifiantHotelsPartenaire(listeIdentifiantAgence.get(numeroAgence - 1)).get(numeroHotel - 1)));
            }
        }
        System.out.println("\nAppuyer sur Entrer pour continuer...");
        new java.util.Scanner(System.in).nextLine();
    }

    private static void rechercherChambreCriteres(AgenceServiceIdentification proxyAgences) throws MalformedURLException, DatatypeConfigurationException, DateNonValideException_Exception, UserNotFoundException_Exception, HotelNotFoundExceptionException {
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'agence avec laquelle faire la recherche");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        scanner.nextLine();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
        } else {
            URL url = new URL("http://localhost:8090/agencesservice/" + listeIdentifiantAgence.get(numeroAgence - 1) + "/consultation");
            AgenceServiceConsultationImplService agenceServiceConsultation = new AgenceServiceConsultationImplService(url);
            AgenceServiceConsultation proxyConsultation = agenceServiceConsultation.getAgenceServiceConsultationImplPort();

            DatatypeFactory df = DatatypeFactory.newInstance();
            GregorianCalendar gc = new GregorianCalendar();

            System.out.println("\uD83D\uDCAC ️Veuillez saisir la ville :");
            String ville = scanner.nextLine();
            System.out.println("\uD83D\uDCAC ️Veuillez renseigner la date de d'arriver :");
            System.out.println("️Veuillez saisir le jour");
            String jourArrivee = scanner.nextLine();
            System.out.println("Veuillez saisir le mois");
            String moisArrivee = scanner.nextLine();
            System.out.println("️Veuillez saisir l'annee");
            String anneeArrivee = scanner.nextLine();
            int anneeArriveeInt = Integer.parseInt(anneeArrivee);
            int moisArriveeInt = Integer.parseInt(moisArrivee);
            int jourArriveeInt = Integer.parseInt(jourArrivee);

            LocalDate localDateArrivee = LocalDate.of(anneeArriveeInt, moisArriveeInt, jourArriveeInt);

            GregorianCalendar greg = GregorianCalendar.from(ZonedDateTime.of(localDateArrivee.atStartOfDay(), ZoneId.systemDefault()));
            XMLGregorianCalendar xmlDateArrivee = df.newXMLGregorianCalendar(greg);
            System.out.println(xmlDateArrivee);

            System.out.println("\uD83D\uDCAC ️Veuillez renseigner la date de départ :");
            System.out.println("Veuillez saisir le jour");
            String jourDepart = scanner.nextLine();
            System.out.println("Veuillez saisir le mois");
            String moisDepart = scanner.nextLine();
            System.out.println("Veuillez saisir l'annee");
            String anneeDepart = scanner.nextLine();
            int anneeDepartInt = Integer.parseInt(anneeDepart);
            int moisDepartInt = Integer.parseInt(moisDepart);
            int jourDepartInt = Integer.parseInt(jourDepart);

            LocalDate localDateDepart = LocalDate.of(anneeDepartInt, moisDepartInt, jourDepartInt);
            greg = GregorianCalendar.from(ZonedDateTime.of(localDateDepart.atStartOfDay(), ZoneId.systemDefault()));
            XMLGregorianCalendar xmlDateDepart = df.newXMLGregorianCalendar(greg);

            System.out.println("\uD83D\uDCAC ️Veuillez saisir le prix minimum :");
            int prixMin = scanner.nextInt();
            System.out.println("\uD83D\uDCAC ️Veuillez saisir le prix maximum :");
            int prixMax = scanner.nextInt();
            System.out.println("\uD83D\uDCAC ️Veuillez saisir le nombre d'étoiles :");
            int nombreEtoiles = scanner.nextInt();
            System.out.println("\uD83D\uDCAC ️Veuillez saisir le nombre de personnes :");
            int nombrePersonne = scanner.nextInt();

            ArrayList<Offres> listeOffres = new ArrayList<>();
            try {
                listeOffres = (ArrayList<Offres>) proxyConsultation.listeChoisOffresHotelCriteres(loginUser, passwordUser, ville, xmlDateArrivee, xmlDateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonne);
            } catch (DateNonValideException_Exception e) {
                System.out.println("⚠\uFE0F Vous n'avez pas renseigné de date valide.");
            } catch (UserNotFoundException_Exception e) {
                System.out.println("⚠\uFE0F Vous n'avez pas de compte dans cette agence.");
            } catch (Exception e) {
                System.out.println("⚠\uFE0F Probleme lors de la chercher de chambres :" + e.getMessage());
            }
            if (listeOffres.isEmpty()) {
                System.out.println("Aucune chambre ne correspond a vos critères...");
            } else {
                int compteur = 1;
                StringBuilder res = new StringBuilder();
                for (Offres offres : listeOffres) {
                    if (!offres.getOffres().isEmpty()) {
                        res.append("Liste des offres concernant ");
                        String IdentifiantHotel = offres.getOffres().get(0).getIdHotel();
                        String nomHotel = proxyAgences.afficherHotelSimple(IdentifiantHotel);
                        String pattern = ", possède \\d+ chambres\\.$";
                        String modifiedNomHotel = nomHotel.replaceAll(pattern, "");

                        res.append(modifiedNomHotel).append(" : \n");
                        for (Offre offre : offres.getOffres()) {
                            gc = offre.getDateArrivee().toGregorianCalendar();
                            LocalDate localDate = gc.toZonedDateTime().toLocalDate();
                            String formattedDateArrivee = localDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
                            gc = offre.getDateDepart().toGregorianCalendar();
                            localDate = gc.toZonedDateTime().toLocalDate();
                            String formattedDateDepart = localDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));

                            res.append("\t- Offre n°").append(compteur).append(" (").append(offre.getIdentifiant()).append(") pour ").append(offre.getNombreLitsTotal()).append(" personnes, proposée au prix de ").append(offre.getPrix()).append("€,");
                            if (offre.getChambres().size() == 1) {
                                Chambre chambre = offre.getChambres().get(0);
                                String chambreString = "Chambre " + chambre.getNumero() + " (" + chambre.getNombreLits() + " lits), coute " + chambre.getPrix() + "€";
                                res.append(" dans la chambre ").append(chambreString);
                            } else {
                                res.append(" dans les chambres :\n");
                                for (int i = 0; i < offre.getChambres().size(); i++) {
                                    Chambre chambre = offre.getChambres().get(i);
                                    String chambreString = "Chambre " + chambre.getNumero() + " (" + chambre.getNombreLits() + " lits), coute " + chambre.getPrix() + "€\n";
                                    res.append("\t\t- ").append(chambreString);
                                }
                            }
                            compteur++;
                        }
                        res.append("\n---\n");
                    }
                }
                System.out.println(res);
            }
        }
        System.out.println("\nAppuyer sur Entrer pour continuer...");
        new java.util.Scanner(System.in).nextLine();
    }

    /*
    private static String reserverChambres() throws MalformedURLException {
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'agence avec laquelle faire la recherche");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
            return null;
        } else {
            URL url = new URL("http://localhost:8090/agencesservice/" + numeroAgence + "/reservation");
            AgenceServiceReservationImplService agenceServiceReservation = new AgenceServiceReservationImplService(url);
            AgenceServiceReservation proxyReservation = agenceServiceReservation.getAgenceServiceReservationImplPort();

            System.out.println("\uD83D\uDCAC ️Veuillez saisir votre login");
            String login = scanner.nextLine();

            proxyReservation.reserverChambresHotel(login, motDePasse, offre, petitDejeuner, nomClient, prenomClient, email, telephone, carte)

            String login, String motDePasse, Offre offre,boolean petitDejeuner,
            String nomClient, String prenomClient, String email, String telephone, Carte carte

        }
    }
     */

    public static void main(String[] args) throws
            MalformedURLException, AgenceNotFoundException_Exception, InterruptedException, UserAlreadyExistsException_Exception, HotelNotFoundExceptionException, DatatypeConfigurationException, DateNonValideException_Exception, UserNotFoundException_Exception {

        // CLI agence - hotel reservation

        // Recupere la liste des agences
        URL url = new URL("http://localhost:8090/agencesservice/identifiantAgences?wsdl");
        AgenceServiceIdentificationImplService agenceServiceIdentification = new AgenceServiceIdentificationImplService(url);
        AgenceServiceIdentification proxyAgences = agenceServiceIdentification.getAgenceServiceIdentificationImplPort();

        listeIdentifiantAgence = (ArrayList<String>) proxyAgences.getListeAgence();

        while (true) {
            if (Objects.equals(loginUser, "") && Objects.equals(passwordUser, "")) {
                creationUtilisateur();
            }
            System.out.println("\n\n\n\n\n\n\n\n\n\n");
            System.out.println("1. \uD83E\uDDFE Afficher la liste des agences");
            System.out.println("2. \uD83D\uDD0D Afficher les détails d'une agence");
            System.out.println("3. \uD83E\uDEAA Création de compte pour une agence");
            System.out.println("4. \uD83C\uDFE8 Afficher les détails d'un hotel");
            System.out.println("5. \uD83D\uDD0D Afficher les reservations d'un hotel");
            System.out.println("6. \uD83D\uDECF Recherche de chambres avec une agence");
            System.out.println("7. \uD83D\uDCB5 Réserver des chambres avec une agence");
            System.out.println("0. ❌ Quitter");

            int choix = 0;
            try {
                Scanner scanner = new Scanner(System.in);
                choix = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Veuillez entrer un chiffre");
            }
            switch (choix) {
                case 1: // Afficher la liste des agences
                    afficherListeAgence();
                    break;
                case 2: // Afficher les détails d'une agence
                    afficherDetailAgence(proxyAgences);
                    break;
                case 3: // Création de compte pour une agence
                    créationCompteAgence();
                    break;
                case 4: // Afficher les détails d'un hotel
                    afficherDetailHotel(proxyAgences);
                    break;
                case 5: // Afficher les reservations d'un hotel
                    afficherReservationHotel(proxyAgences);
                    break;
                case 6: // Recherche de chambres dans une agence
                    rechercherChambreCriteres(proxyAgences);
                    break;
                case 7: // Réserver des chambres dans une agence
                    System.out.println("Réserver des chambres dans une agence");
                    break;
                case 0: // Quitter
                    System.out.println("Au revoir !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Veuillez entrer un nombre entre 1 et 7");
                    break;
            }
        }
    }
}
