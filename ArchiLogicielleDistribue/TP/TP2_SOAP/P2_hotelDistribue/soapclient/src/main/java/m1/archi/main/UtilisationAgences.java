package m1.archi.main;

import m1.archi.agence.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.impl.UtilCalendarModel;

public class UtilisationAgences {
    private static String loginUser = "";
    private static String passwordUser = "";
    private static ArrayList<String> listeIdentifiantAgence = new ArrayList<>();
    private static ArrayList<Offre> listeOffres = new ArrayList<Offre>();

    private static HashMap<String, String> mapAgenceNomIdentifiant = new HashMap<>();

    private static HashMap<String, ArrayList<Hotel>> mapAgenceHotelPartenaire = new HashMap<>();
    private static String selectedAgence;
    private static ArrayList<Hotel> hotelsPartenaires = new ArrayList<Hotel>();
    private static ArrayList<User> user = new ArrayList<User>();


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
                listeOffress = (ArrayList<Offres>) proxyConsultation.listeChoisOffresHotelCriteres(loginUser, passwordUser, ville, xmlDateArrivee, xmlDateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonne);
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

    private static int findUserIndexByName(String userName) {
        for (int i = 0; i < user.size(); i++) {
            if (user.get(i).getLogin().equals(userName)) {
                return i; // L'utilisateur a été trouvé, renvoie l'indice
            }
        }

        return -1; // L'utilisateur n'a pas été trouvé dans la liste
    }

    private static void reserverChambres() throws MalformedURLException, DateNonValideException_Exception, ReservationProblemeException_Exception, UserNotFoundException_Exception {
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
            Reservation reservation = proxyReservation.reserverChambresHotel(loginUser, passwordUser, offre, petitDejeuner, nomClient, prenomClient, email, telephone, nomCarte, numeroCarte, dateExpiration, CCV);
            System.out.println("La Chambre a bien été réservé \n");
            int i = findUserIndexByName(loginUser);
            user.get(i).addReservation(reservation);
        }
    }

    private static class SearchDialog extends JDialog {
        private JTextField villeField;
        private JTextField nbEtoilesField;
        private JTextField nbPersonneField;
        private JDatePickerImpl dateArriveePicker;
        private JDatePickerImpl dateDepartPicker;
        private JTextField prixMinField;
        private JTextField prixMaxField;

        public SearchDialog(JFrame parent) throws MalformedURLException {
            super(parent, "Rechercher une Offre", true);

            // Initialisation des composants
            villeField = new JTextField(10);
            nbEtoilesField = new JTextField(10);
            nbPersonneField = new JTextField(10);

            UtilDateModel dateArriveeModel = new UtilDateModel();
            UtilDateModel dateDepartModel = new UtilDateModel();

            dateArriveePicker = new JDatePickerImpl(new JDatePanelImpl(dateArriveeModel, new Properties()), new DateLabelFormatter());
            dateDepartPicker = new JDatePickerImpl(new JDatePanelImpl(dateDepartModel, new Properties()), new DateLabelFormatter());

            prixMinField = new JTextField(10);
            prixMaxField = new JTextField(10);

            URL url = new URL("http://localhost:8090/agencesservice/" + selectedAgence + "/consultation");
            AgenceServiceConsultationImplService agenceServiceConsultation = new AgenceServiceConsultationImplService(url);
            AgenceServiceConsultation proxyConsultation = agenceServiceConsultation.getAgenceServiceConsultationImplPort();

            JButton searchButton = new JButton("Rechercher");

            searchButton.addActionListener(e -> {
                // Récupérer les valeurs saisies
                String ville = villeField.getText();
                int nombreEtoiles = Integer.parseInt(nbEtoilesField.getText());
                int nombrePersonnes = Integer.parseInt(nbPersonneField.getText());
                int prixMin = Integer.parseInt(prixMinField.getText());
                int prixMax = Integer.parseInt(prixMaxField.getText());

                // Convertir les champs de date en XMLGregorianCalendar
                Date dateArrivee = dateArriveeModel.getValue();
                Date dateDepart = dateDepartModel.getValue();

                //Convertir les date en XMLGregorianCalendar
                GregorianCalendar greg = GregorianCalendar.from(ZonedDateTime.of(dateArrivee.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(), ZoneId.systemDefault()));
                XMLGregorianCalendar xmlDateArrivee = null;
                try {
                    xmlDateArrivee = DatatypeFactory.newInstance().newXMLGregorianCalendar(greg);
                } catch (DatatypeConfigurationException ex) {
                    ex.printStackTrace();
                }
                greg = GregorianCalendar.from(ZonedDateTime.of(dateDepart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(), ZoneId.systemDefault()));
                XMLGregorianCalendar xmlDateDepart = null;
                try {
                    xmlDateDepart = DatatypeFactory.newInstance().newXMLGregorianCalendar(greg);
                } catch (DatatypeConfigurationException ex) {
                    ex.printStackTrace();
                }

                System.out.println("La date d'arrivée est :  " + xmlDateArrivee);
                System.out.println("La date de départ est :  " + xmlDateDepart);


                // Faire la recherche
                ArrayList<Offres> listeOffress = new ArrayList<>();
                try {
                    listeOffress = (ArrayList<Offres>) proxyConsultation.listeChoisOffresHotelCriteres(loginUser, passwordUser, ville, xmlDateArrivee, xmlDateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonnes);
                    System.out.println(listeOffress.size());

                    // Afficher les résultats
                    new ResultatsFrame(listeOffress);
                } catch (DateNonValideException_Exception ex) {
                    System.out.println("⚠\uFE0F Vous n'avez pas renseigné de date valide.");
                } catch (UserNotFoundException_Exception ex) {
                    System.out.println("⚠\uFE0F Vous n'avez pas de compte dans cette agence.");
                } catch (Exception ex) {
                    System.out.println("⚠\uFE0F Probleme lors de la chercher de chambres :" + ex.getMessage());
                }

                // Fermer la fenêtre après la recherche
                dispose();
            });

            // Disposition des composants dans le panneau
            JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));  // Augmenter l'espacement entre les composants
            panel.add(new JLabel("Ville:"));
            panel.add(villeField);
            panel.add(new JLabel("Nombre d'étoiles:"));
            panel.add(nbEtoilesField);
            panel.add(new JLabel("Nombre de lits:"));
            panel.add(nbPersonneField);
            panel.add(new JLabel("Date d'arrivée:"));
            panel.add(dateArriveePicker);
            panel.add(new JLabel("Date de départ:"));
            panel.add(dateDepartPicker);
            panel.add(new JLabel("Prix minimum:"));
            panel.add(prixMinField);
            panel.add(new JLabel("Prix maximum:"));
            panel.add(prixMaxField);
            panel.add(new JLabel(""));
            panel.add(searchButton);

            // Ajouter le panneau à la fenêtre
            add(panel);

            // Définir la taille et la position de la fenêtre
            setSize(400, 400);
            setLocationRelativeTo(parent);
        }
    }

    public static class ResultatsFrame extends JFrame {

        public ResultatsFrame(ArrayList<Offres> listeOffres) {
            setTitle("Résultats de la recherche");
            setSize(800, 600);

            // Créez un modèle de liste pour afficher les offres
            DefaultListModel<String> offresListModel = new DefaultListModel<>();
            JList<String> offresList = new JList<>(offresListModel);

            // Ajoutez les offres au modèle de liste
            for (Offres offre : listeOffres) {
                offresListModel.addElement(offre.toString()); // Remplacez cela par la représentation souhaitée de l'offre
            }

            // Ajoutez la liste au panneau de contenu
            JScrollPane scrollPane = new JScrollPane(offresList);
            getContentPane().add(scrollPane);

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }


    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "dd MMMM yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern, Locale.getDefault());

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                if (value instanceof Date) {
                    return dateFormatter.format((Date) value);
                } else if (value instanceof GregorianCalendar) {
                    return dateFormatter.format(((GregorianCalendar) value).getTime());
                } else {
                    return "Erreur...";
                }
            } else {
                return "Selectionnez une date..."; // ou une représentation par défault
            }
        }
    }

    private static class InscriptionDialog extends JDialog {
        private JTextField loginField;
        private JTextField passwordField;

        public InscriptionDialog(JFrame parent) throws MalformedURLException {
            super(parent, "Inscription", true);
            loginField = new JTextField(10);
            passwordField = new JTextField(10);

            URL url = new URL("http://localhost:8090/agencesservice/" + selectedAgence + "/inscription");
            UserServiceInscriptionImplService userServiceInscription = new UserServiceInscriptionImplService(url);
            UserServiceInscription proxyInscription = userServiceInscription.getUserServiceInscriptionImplPort();

            // Initialisation des composants
            loginField = new JTextField(10);
            passwordField = new JTextField(10);

            JButton inscriptionButton = new JButton("S'inscrire");

            inscriptionButton.addActionListener(e -> {
                // Récupérer les valeurs saisies
                String login = loginField.getText();
                String password = passwordField.getText();

                // Faire l'inscription
                try {
                    proxyInscription.inscription(login, password);
                } catch (UserAlreadyExistsException_Exception ex) {
                    System.out.println("⚠️ Vous avez déjà un compte dans cette agence.");
                } catch (Exception ex) {
                    System.out.println("⚠️ Erreur lors de la création de votre compte.");
                }

                // Fermer la fenêtre après la recherche
                dispose();
            });

            // Disposition des composants dans le panneau
            JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));  // Augmenter l'espacement entre les composants
            panel.add(new JLabel("Login:"));
            panel.add(loginField);
            panel.add(new JLabel("Mot de passe:"));
            panel.add(passwordField);
            panel.add(new JLabel(""));
            panel.add(inscriptionButton);

            // Ajouter le panneau à la fenêtre
            add(panel);

            // Définir la taille et la position de la fenêtre
            setSize(400, 400);
            setLocationRelativeTo(parent);

        }
    }

    private static void createAndShowGUI(AgenceServiceIdentification proxyAgences) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Gestionnaire d'agence");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new FlowLayout());

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BorderLayout());

            // Étape 1 : Liste des agences
            JLabel agenceLabel = new JLabel("Sélectionnez une agence pour afficher ces hotel partenaires :");
            String[] agences = new String[listeIdentifiantAgence.size()];
            for (int i = 0; i < listeIdentifiantAgence.size(); i++) {
                try {
                    agences[i] = proxyAgences.getAgence(listeIdentifiantAgence.get(i)).getNom();
                    mapAgenceNomIdentifiant.put(agences[i], listeIdentifiantAgence.get(i));
                } catch (AgenceNotFoundException_Exception e) {
                    System.out.println("⚠\uFE0F L'agence n'a pas été trouvée.");
                }
            }
            JComboBox<String> agenceComboBox = new JComboBox<>(agences);
            agenceComboBox.setPreferredSize(new Dimension(400, 30));

            topPanel.add(agenceLabel);
            topPanel.add(agenceComboBox);

            // Étape 2 : Liste d'hôtels
            DefaultListModel<String> hotelListModel = new DefaultListModel<>();
            JList<String> hotelListUI = new JList<>(hotelListModel);
            JScrollPane scrollPane = new JScrollPane(hotelListUI);
            bottomPanel.add(scrollPane, BorderLayout.CENTER);

            // Étape 3 : Recherche et réservation
            JButton searchButton = new JButton("Effectuer une recherche");
            bottomPanel.add(searchButton, BorderLayout.SOUTH);

            // Étape 4 : S'incrire dans une agence
            JButton inscriptionButton = new JButton("S'inscrire à l'agence");
            bottomPanel.add(inscriptionButton, BorderLayout.NORTH);

            // Événement de sélection de l'agence
            agenceComboBox.addActionListener(e -> {
                selectedAgence = mapAgenceNomIdentifiant.get(agenceComboBox.getSelectedItem());

                // Affichage du message de chargement
                hotelListModel.clear();
                hotelListModel.addElement("Chargement en cours...");

                // Effectuer la requête SOAP dans un thread séparé
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            if (mapAgenceHotelPartenaire.get(selectedAgence) == null) {
                                hotelsPartenaires = (ArrayList<Hotel>) proxyAgences.getListeHotelsPartenaire(selectedAgence);
                                mapAgenceHotelPartenaire.put(selectedAgence, hotelsPartenaires);
                            } else hotelsPartenaires = mapAgenceHotelPartenaire.get(selectedAgence);
                        } catch (AgenceNotFoundException_Exception ex) {
                            System.out.println("⚠️ L'agence n'a pas été trouvée.");
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        // Une fois que les données sont récupérées
                        hotelListModel.clear();
                        hotelsPartenaires.sort(Comparator.comparing((Hotel hotel) -> hotel.getAdresse().getPays()).thenComparing(hotel -> hotel.getAdresse().getVille()).thenComparing(Hotel::getNombreEtoiles));
                        for (Hotel hotel : hotelsPartenaires) {
                            StringBuilder hotelString = new StringBuilder();
                            hotelString.append("- L'hotel '").append(hotel.getNom()).append("' (");
                            for (int i = 0; i < hotel.getNombreEtoiles(); i++) {
                                hotelString.append("⭐");
                            }
                            hotelString.append(") ").append("situé à ").append(hotel.getAdresse().getVille()).append(" (").append(hotel.getAdresse().getPays()).append(") possède ").append(hotel.getChambres().size()).append(" chambres.");
                            hotelListModel.addElement(hotelString.toString());
                        }
                    }
                };
                worker.execute(); // Démarre le travail dans un thread séparé
            });

            hotelListUI.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    int selectedIndex = hotelListUI.getSelectedIndex();
                    if (selectedIndex != -1) {
                        Hotel selectedHotel = hotelsPartenaires.get(selectedIndex);

                        // Créez un panneau pour afficher les informations de l'hôtel
                        JPanel hotelInfoPanel = new JPanel();
                        hotelInfoPanel.setLayout(new BoxLayout(hotelInfoPanel, BoxLayout.Y_AXIS));

                        // Ajoutez les détails de l'hôtel à un composant JTextPane avec HTML
                        StringBuilder res = new StringBuilder("<html><b>L'hotel '" + selectedHotel.getNom() + "' (");
                        for (int i = 0; i < selectedHotel.getNombreEtoiles(); i++) {
                            res.append("⭐");
                        }
                        res.append(") situé au ").append(selectedHotel.getAdresse().getNumero()).append(" ").append(selectedHotel.getAdresse().getRue()).append(" en ").append(selectedHotel.getAdresse().getPays())
                                .append(", possède ").append(selectedHotel.getChambres().size()).append(" chambres :</b><br>");

                        // Ajoutez le texte en gras au JTextPane
                        JTextPane hotelInfoTextPane = new JTextPane();
                        hotelInfoTextPane.setContentType("text/html");
                        hotelInfoTextPane.setEditable(false);
                        hotelInfoTextPane.setText(res.toString());

                        // Ajoutez le JTextPane à un JScrollPane et au panneau
                        JScrollPane scrollPaneHotel = new JScrollPane(hotelInfoTextPane);
                        hotelInfoPanel.add(scrollPaneHotel);

                        // Créez un JLabel pour afficher l'image
                        JLabel hotelImageLabel = new JLabel();
                        String base64Image = selectedHotel.getImageHotel();
                        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                        ImageIcon hotelImageIcon = new ImageIcon(imageBytes);
                        // Redimensionnez l'image pour harmoniser la taille
                        Image scaledImage = hotelImageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                        hotelImageIcon = new ImageIcon(scaledImage);
                        hotelImageLabel.setIcon(hotelImageIcon);
                        hotelInfoPanel.add(hotelImageLabel);

                        // Créez une liste pour afficher les détails des chambres
                        DefaultListModel<String> chambreListModel = new DefaultListModel<>();
                        JList<String> chambreList = new JList<>(chambreListModel);
                        for (Chambre chambre : selectedHotel.getChambres()) {
                            chambreListModel.addElement("- Chambre n°" + chambre.getNumero() + " pour " + chambre.getNombreLits() + " personnes, coûte " + chambre.getPrix() + "€ la nuit.");
                        }

                        chambreList.addListSelectionListener(c -> {
                            if (!c.getValueIsAdjusting()) {
                                int selectedChambreIndex = chambreList.getSelectedIndex();
                                if (selectedChambreIndex != -1) {
                                    Chambre selectedChambre = selectedHotel.getChambres().get(selectedChambreIndex);

                                    // Créez un panneau pour afficher les informations de la chambre
                                    JPanel chambreInfoPanel = new JPanel();
                                    chambreInfoPanel.setLayout(new BoxLayout(chambreInfoPanel, BoxLayout.Y_AXIS));

                                    // Ajoutez les détails de la chambre à un composant JTextPane avec HTML
                                    String resChambre = "<html><b>Chambre n°" + selectedChambre.getNumero() + "</b><br>" + "Prix : " + selectedChambre.getPrix() + "€<br>" +
                                            "Nombre de lits : " + selectedChambre.getNombreLits() + "<br>" +
                                            "Image de la chambre : </html>";

                                    // Ajoutez le texte au JTextPane
                                    JTextPane chambreInfoTextPane = new JTextPane();
                                    chambreInfoTextPane.setContentType("text/html");
                                    chambreInfoTextPane.setEditable(false);
                                    chambreInfoTextPane.setText(resChambre);
                                    chambreInfoPanel.add(chambreInfoTextPane);

                                    // Ajoutez l'image de la chambre
                                    JLabel chambreImageLabel = new JLabel();
                                    String base64ImageChambre = selectedChambre.getImageChambre();
                                    byte[] imageBytesChambre = Base64.getDecoder().decode(base64ImageChambre);
                                    ImageIcon chambreImageIcon = new ImageIcon(imageBytesChambre);
                                    // Redimensionnez l'image pour harmoniser la taille
                                    Image scaledImageChambre = chambreImageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                                    chambreImageIcon = new ImageIcon(scaledImageChambre);
                                    chambreImageLabel.setIcon(chambreImageIcon);
                                    chambreInfoPanel.add(chambreImageLabel);

                                    // Affichez le panneau d'informations de la chambre dans une fenêtre
                                    JFrame chambreInfoFrame = new JFrame("Détails de la chambre " + selectedChambre.getNumero());
                                    chambreInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    chambreInfoFrame.getContentPane().add(chambreInfoPanel);
                                    chambreInfoFrame.pack();
                                    chambreInfoFrame.setLocationRelativeTo(null);
                                    chambreInfoFrame.setVisible(true);

                                    System.out.println("Chambre sélectionnée : " + selectedChambre.getNumero());
                                }
                            }
                        });

                        hotelInfoPanel.add(new JScrollPane(chambreList));

                        // Affichez le panneau d'informations de l'hôtel dans une boîte de dialogue
                        JOptionPane.showMessageDialog(frame, hotelInfoPanel, "Détails de l'hôtel " + selectedHotel.getIdentifiant() + " : ", JOptionPane.PLAIN_MESSAGE);

                    }
                }
            });

            // Événement pour rechercher des offres
            searchButton.addActionListener(e -> {
                if (selectedAgence != null) {
                    // Ouvrir la fenêtre de recherche
                    SearchDialog searchDialog = null;
                    try {
                        searchDialog = new SearchDialog(frame);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                    searchDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Sélectionnez d'abord une agence.");
                }
            });

            // Événement pour s'inscrire dans une agence
            inscriptionButton.addActionListener(e -> {
                if (selectedAgence != null) {
                    // Ouvrir la fenêtre de recherche
                    InscriptionDialog inscriptionDialog = null;
                    try {
                        inscriptionDialog = new InscriptionDialog(frame);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                    inscriptionDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Sélectionnez d'abord une agence.");
                }
            });

            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(bottomPanel, BorderLayout.CENTER);

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) throws MalformedURLException, AgenceNotFoundException_Exception, InterruptedException, UserAlreadyExistsException_Exception, HotelNotFoundExceptionException, DatatypeConfigurationException, DateNonValideException_Exception, UserNotFoundException_Exception, ReservationProblemeException_Exception {

        // CLI agence - hotel reservation

        // Recupere la liste des agences
        URL url = new URL("http://localhost:8090/agencesservice/identifiantAgences?wsdl");
        AgenceServiceIdentificationImplService agenceServiceIdentification = new AgenceServiceIdentificationImplService(url);
        AgenceServiceIdentification proxyAgences = agenceServiceIdentification.getAgenceServiceIdentificationImplPort();

        listeIdentifiantAgence = (ArrayList<String>) proxyAgences.getListeAgence();
        createAndShowGUI(proxyAgences);

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
            System.out.println("8. Lancer l'UI");
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
                    reserverChambres();
                    break;
                case 8:
                    System.out.println("Demarrage de l'UI");
                    createAndShowGUI(proxyAgences);
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
