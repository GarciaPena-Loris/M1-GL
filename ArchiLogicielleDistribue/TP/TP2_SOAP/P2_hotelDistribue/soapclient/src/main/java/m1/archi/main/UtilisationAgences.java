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
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UtilisationAgences {
    private User userConnecte;

    private GestionnaireUser gestionnaireUser;
    private ArrayList<Offre> listeOffres = new ArrayList<Offre>();
    private final ArrayList<String> listeIdentifiantAgence = new ArrayList<>();

    public UtilisationAgences(GestionnaireUser gestionnaireUser) {
        this.gestionnaireUser = gestionnaireUser;
    }

    public void creationUtilisateur() {
        System.out.println("Récupération de vos informations :");
        System.out.println("\uD83D\uDC64 Veuillez entrer votre login");
        Scanner scanner = new Scanner(System.in);
        String login = scanner.nextLine();
        System.out.println("\uD83D\uDD11 Veuillez entrer votre mot de passe");
        String motdepasse = scanner.nextLine();

        if (gestionnaireUser.userExists(login, motdepasse)) {
            if (gestionnaireUser.getUser(login, motdepasse) != null) {
                userConnecte = gestionnaireUser.getUser(login, motdepasse);
                System.out.println("Vous êtes connecté en tant que " + userConnecte.getLogin());
            } else {
                System.out.println("Mot de passe incorrect !");
            }
        } else {
            System.out.println("Vous n'avez pas de compte, voulez-vous en créer un ? (oui/non)");
            String reponse = scanner.nextLine();
            if (reponse.equals("oui")) {
                System.out.println("Création de votre compte...");
                userConnecte = new User(login, motdepasse);
                gestionnaireUser.addUser(userConnecte);
                System.out.println("Votre compte a bien été créé !");
            } else {
                System.out.println("Vous n'avez pas de compte, vous ne pouvez pas utiliser l'application");
            }
        }
    }

    public void afficherListeAgence() throws InterruptedException {
        int compteur = 1;
        for (String identifiantAgence : listeIdentifiantAgence) {
            System.out.println("Agence n°" + compteur + " : " + identifiantAgence);
            compteur++;
            Thread.sleep(300);
        }
        System.out.println("\nAppuyer sur Entrer pour continuer...");
        new java.util.Scanner(System.in).nextLine();
    }

    public void afficherDetailAgence(AgenceServiceIdentification proxyAgences) throws AgenceNotFoundException_Exception {
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

    public void creationCompteAgence() throws MalformedURLException {
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
                if (userConnecte == null) {
                    System.out.println("Vous n'êtes pas connecté, veuillez vous connecter pour créer un compte");
                    creationUtilisateur();
                }
                proxyInscription.inscription(userConnecte.getLogin(), userConnecte.getPassword());
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


    public void afficherDetailHotel(AgenceServiceIdentification proxyAgences) throws AgenceNotFoundException_Exception, HotelNotFoundExceptionException {
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'agence");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
            return;
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

    public void afficherReservationHotel(AgenceServiceIdentification proxyAgences) throws AgenceNotFoundException_Exception, HotelNotFoundExceptionException {
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

    public void rechercherChambreCriteres(AgenceServiceIdentification proxyAgences) throws MalformedURLException, DatatypeConfigurationException, HotelNotFoundExceptionException {
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

            ArrayList<Offres> listeOffress = new ArrayList<>();
            try {
                listeOffress = (ArrayList<Offres>) proxyConsultation.listeChoisOffresHotelCriteres(userConnecte.getLogin(), userConnecte.getPassword(), ville, xmlDateArrivee, xmlDateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonne);
                System.out.println(listeOffress.size());
            } catch (DateNonValideException_Exception e) {
                System.out.println("⚠\uFE0F Vous n'avez pas renseigné de date valide.");
            } catch (UserNotFoundException_Exception e) {
                System.out.println("⚠\uFE0F Vous n'avez pas de compte dans cette agence.");
            } catch (Exception e) {
                System.out.println("⚠\uFE0F Probleme lors de la chercher de chambres :" + e.getMessage());
            }
            if (listeOffress.isEmpty()) {
                System.out.println("Aucune chambre ne correspond a vos critères...");
            } else {
                int compteur = 1;
                StringBuilder res = new StringBuilder();
                for (Offres offres : listeOffress) {
                    if (!offres.getOffres().isEmpty()) {
                        res.append("Liste des offres concernant ");
                        String IdentifiantHotel = offres.getOffres().get(0).getIdHotel();
                        String nomHotel = proxyAgences.afficherHotelSimple(IdentifiantHotel);
                        String pattern = ", possède \\d+ chambres\\.$";
                        String modifiedNomHotel = nomHotel.replaceAll(pattern, "");

                        res.append(modifiedNomHotel).append(" : \n");
                        for (Offre offre : offres.getOffres()) {
                            GregorianCalendar gc = offre.getDateArrivee().toGregorianCalendar();
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
                            listeOffres.add(offre);
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

    public void reserverChambres() throws MalformedURLException, DateNonValideException_Exception, ReservationProblemeException_Exception, UserNotFoundException_Exception {
        System.out.println("\uD83D\uDCAC ️Veuillez saisir le numéro de l'agence avec laquelle faire la recherche");
        Scanner scanner = new Scanner(System.in);
        int numeroAgence = scanner.nextInt();
        if (numeroAgence < 1 || numeroAgence > listeIdentifiantAgence.size()) {
            System.out.println("Veuillez entrer un nombre entre 1 et " + listeIdentifiantAgence.size());
        } else {
            URL url = new URL("http://localhost:8090/agencesservice/" + numeroAgence + "/reservation");
            AgenceServiceReservationImplService agenceServiceReservation = new AgenceServiceReservationImplService(url);
            AgenceServiceReservation proxyReservation = agenceServiceReservation.getAgenceServiceReservationImplPort();
            System.out.println("Quelle offre souhaitez vous prendre ?\n");
            int numeroOffre = scanner.nextInt();
            Offre offre = listeOffres.get(numeroOffre - 1);
            System.out.println("Souhaitez vous prendre un petit dejeuner ?\n");
            System.out.println("veuillez répondre oui ou non\n");
            String petitDejDeChampion = scanner.nextLine();
            boolean petitDejeuner;
            if (petitDejDeChampion.equals("oui")) {
                petitDejeuner = true;
            } else if (petitDejDeChampion.equals("non")) {
                petitDejeuner = false;
            } else petitDejeuner = false;
            System.out.println("Veuillez taper le nom sans lui faire trop mal\n");
            String nomClient = scanner.nextLine();
            System.out.println("Veuillez taper le prenom attention il est fragile\n");
            String prenomClient = scanner.nextLine();
            System.out.println("Veuillez taper l'email pas trop fort\n");
            String email = scanner.nextLine();
            System.out.println("Veuillez taper le numero de telephone attention au revers de la medaille\n");
            String telephone = scanner.nextLine();
            System.out.println("Veuillez taper la date d'expiration de votre carte nom sans lui faire trop mal\n");
            String dateExpiration = scanner.nextLine();
            System.out.println("Veuillez taper le nom de votre carte nom sans la froisser\n");
            String nomCarte = scanner.nextLine();
            System.out.println("Veuillez taper le numero de votre carte nom sans la brusquer\n");
            String numeroCarte = scanner.nextLine();
            System.out.println("Veuillez taper le CCV de votre carte nom sans les mains\n");
            String CCV = scanner.nextLine();
            Reservation reservation = proxyReservation.reserverChambresHotel(userConnecte.getLogin(), userConnecte.getPassword(), offre, petitDejeuner, nomClient, prenomClient, email, telephone, nomCarte, numeroCarte, dateExpiration, CCV);
            System.out.println("La Chambre a bien été réservé \n");
            userConnecte.addReservation(reservation);
        }
    }
}
