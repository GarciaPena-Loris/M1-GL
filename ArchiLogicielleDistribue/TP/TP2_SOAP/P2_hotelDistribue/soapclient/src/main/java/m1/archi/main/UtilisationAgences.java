package m1.archi.main;

import m1.archi.agence.*;

import javax.swing.*;
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
        private JTextField etoilesField;
        private JTextField litsField;
        private JDatePickerImpl dateArriveePicker;
        private JDatePickerImpl dateDepartPicker;

        private JTextField prixMinField;

        private JTextField prixMaxField;

        private JTextField loginUserField;

        public SearchDialog(JFrame parent) throws MalformedURLException {
            super(parent, "Rechercher une Offre", true);

            // Initialisation des composants
            villeField = new JTextField(10);
            etoilesField = new JTextField(10);
            litsField = new JTextField(10);
            UtilDateModel dateArriveeModel = new UtilDateModel();
            JDatePanelImpl dateArriveePanel = new JDatePanelImpl(dateArriveeModel, new Properties());
            dateArriveePicker = new JDatePickerImpl(dateArriveePanel, new DateLabelFormatter());

            // Configurer le date picker pour la date de départ
            UtilDateModel dateDepartModel = new UtilDateModel();
            JDatePanelImpl dateDepartPanel = new JDatePanelImpl(dateDepartModel, new Properties());
            dateDepartPicker = new JDatePickerImpl(dateDepartPanel, new DateLabelFormatter());

            prixMinField = new JTextField(10);
            prixMaxField = new JTextField(10);
            loginUserField = new JTextField(10);

            URL url = new URL("http://localhost:8090/agencesservice/" + selectedAgence + "/consultation");
            AgenceServiceConsultationImplService agenceServiceConsultation = new AgenceServiceConsultationImplService(url);
            AgenceServiceConsultation proxyConsultation = agenceServiceConsultation.getAgenceServiceConsultationImplPort();

            JButton searchButton = new JButton("Rechercher");
            searchButton.addActionListener(e -> {
                // Récupérer les valeurs saisies
                String ville = villeField.getText();
                int nombreEtoiles = Integer.parseInt(etoilesField.getText());
                int nombrePersonnes = Integer.parseInt(litsField.getText());
                int prixMin = Integer.parseInt(prixMinField.getText());
                int prixMax = Integer.parseInt(prixMaxField.getText());
                String loginUser = loginUserField.getText();

                // Convertir les champs de date en XMLGregorianCalendar
                Date dateArrivee = (Date) dateArriveePicker.getModel().getValue();
                Date dateDepart = (Date) dateDepartPicker.getModel().getValue();


                // Appeler la fonction de recherche avec ces valeurs
                try {
                    rechercherOffre(proxyConsultation, ville, nombreEtoiles, nombrePersonnes, dateArrivee, dateDepart, prixMin, prixMax, loginUser);
                } catch (DateNonValideException_Exception | UserNotFoundException_Exception |
                         DatatypeConfigurationException ex) {
                    throw new RuntimeException(ex);
                }

                // Fermer la fenêtre après la recherche
                dispose();
            });

            // Disposition des composants dans le panneau
            JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));  // Augmenter l'espacement entre les composants
            panel.add(new JLabel("Ville:"));
            panel.add(villeField);
            panel.add(new JLabel("Nombre d'étoiles:"));
            panel.add(etoilesField);
            panel.add(new JLabel("Nombre de lits:"));
            panel.add(litsField);
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

        private void rechercherOffre(AgenceServiceConsultation proxyConsultation, String ville, int nombreEtoiles, int nombrePersonnes, Date dateArrivee, Date dateDepart, int prixMin, int prixMax, String loginUser) throws DateNonValideException_Exception, UserNotFoundException_Exception, DatatypeConfigurationException {
            // Appeler la fonction de recherche ici en utilisant les paramètres fournis
            XMLGregorianCalendar dateArriveeXml = toXMLGregorianCalendar(dateArrivee);
            XMLGregorianCalendar dateDepartXml = toXMLGregorianCalendar(dateDepart);

            ArrayList<Offres> listeOffress = new ArrayList<>();
            listeOffress = (ArrayList<Offres>) proxyConsultation.listeChoisOffresHotelCriteres(loginUser, passwordUser, ville, dateArriveeXml, dateDepartXml, prixMin, prixMax, nombreEtoiles, nombrePersonnes);

            JOptionPane.showMessageDialog(this, "Recherche d'offres pour " + ville + " avec " + nombreEtoiles + " étoiles, " + nombrePersonnes + " lits, arrivée le " + dateArrivee + ", départ le " + dateDepart + ", prix entre " + prixMin + " et " + prixMax + ", pour l'utilisateur " + loginUser + ".");
        }
    }

    private static XMLGregorianCalendar toXMLGregorianCalendar(Date date) throws DatatypeConfigurationException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                return dateFormatter.format(value);
            }
            return "";
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
                            hotelString.append("L'hotel '").append(hotel.getNom()).append("' (");
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
                        JPanel hotelInfoPanel = new JPanel(new BorderLayout());

                        // Ajoutez les détails de l'hôtel à un composant JTextArea
                        StringBuilder res = new StringBuilder("L'hotel '" + selectedHotel.getNom() + "' (");
                        for (int i = 0; i < selectedHotel.getNombreEtoiles(); i++) {
                            res.append("⭐");
                        }
                        res.append("situé au ").append(selectedHotel.getAdresse()).append(", possède ").append(selectedHotel.getChambres().size()).append(" chambres :\n");
                        JTextArea hotelInfoTextArea = new JTextArea(res.toString());
                        hotelInfoTextArea.setWrapStyleWord(true);
                        hotelInfoTextArea.setLineWrap(true);
                        hotelInfoTextArea.setEditable(false);
                        hotelInfoPanel.add(new JScrollPane(hotelInfoTextArea), BorderLayout.CENTER);

                        // Créez un JLabel pour afficher l'image
                        JLabel hotelImageLabel = new JLabel();
                        String base64Image = selectedHotel.getImageHotel();
                        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                        ImageIcon hotelImageIcon = new ImageIcon(imageBytes);
                        hotelImageLabel.setIcon(hotelImageIcon);
                        hotelInfoPanel.add(hotelImageLabel, BorderLayout.WEST);

                        // Créez une liste pour afficher les détails des chambres
                        DefaultListModel<String> chambreListModel = new DefaultListModel<>();
                        JList<String> chambreList = new JList<>(chambreListModel);
                        for (Chambre chambre : selectedHotel.getChambres()) {
                            chambreListModel.addElement(chambre.toString());
                        }
                        hotelInfoPanel.add(new JScrollPane(chambreList), BorderLayout.SOUTH);

                        // Affichez le panneau d'informations de l'hôtel dans une boîte de dialogue
                        JOptionPane.showMessageDialog(null, hotelInfoPanel, "Détails de l'hôtel", JOptionPane.PLAIN_MESSAGE);
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
