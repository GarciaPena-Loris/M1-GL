package m1.archi.main.swingInterface;

import m1.archi.agence.*;
import m1.archi.main.GestionnaireUser;
import m1.archi.main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Interface {
    private final GestionnaireUser gestionnaireUser;
    private User userConnecte;
    private final HashMap<String, String> mapAgenceNomIdentifiant = new HashMap<>();
    private final HashMap<String, ArrayList<Hotel>> mapAgenceHotelPartenaire = new HashMap<>();
    private final ArrayList<String> listeIdentifiantAgence;
    private String selectedAgence;
    private ArrayList<Hotel> hotelsPartenaires = new ArrayList<Hotel>();

    public Interface(AgenceServiceIdentification proxyAgences, GestionnaireUser gestionnaireUser) throws MalformedURLException {
        // Récupération des identifiants des agences
        listeIdentifiantAgence = (ArrayList<String>) proxyAgences.getListeAgence();
        this.gestionnaireUser = gestionnaireUser;

        // Création de l'interface graphique
        createAndShowGUI(proxyAgences);
    }

    private void createAndShowGUI(AgenceServiceIdentification proxyAgences) {
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

            topPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alignement à gauche
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

            // Étape 4 : Recherche et réservation
            JButton connectionButton = new JButton("Connexion");
            topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            topPanel.add(connectionButton);

            // Étape 5 : S'incrire dans une agence
            JButton inscriptionButton = new JButton("S'enregistrer à l'agence");
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

            // Événement pour la connection d'un utilisateur
            connectionButton.addActionListener(e -> {
                LoginDialog loginDialog = new LoginDialog(frame, gestionnaireUser);
                loginDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (userConnecte == null) {
                            userConnecte = loginDialog.getUserConnecte();
                            if (userConnecte != null) {
                                connectionButton.setText("Déconnexion");
                            }
                        }
                        else {
                            userConnecte = null;
                            connectionButton.setText("Connection");
                        }
                    }
                });
                loginDialog.setVisible(true);
            });

            // Événement pour rechercher des offres
            searchButton.addActionListener(e -> {
                if (selectedAgence != null) {
                    // Ouvrir la fenêtre de recherche
                    SearchDialog searchDialog = null;
                    try {
                        searchDialog = new SearchDialog(frame, selectedAgence, userConnecte, proxyAgences);
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
                if (selectedAgence == null) {
                    JOptionPane.showMessageDialog(frame, "Sélectionnez d'abord une agence.");
                } else if (userConnecte == null) {
                    JOptionPane.showMessageDialog(frame, "Connectez vous d'abord.");
                } else {
                    // Ouvrir la fenêtre de recherche
                    try {
                        URL url = new URL("http://localhost:8090/agencesservice/" + selectedAgence + "/inscription");
                        UserServiceInscriptionImplService userServiceInscription = new UserServiceInscriptionImplService(url);
                        UserServiceInscription proxyInscription = userServiceInscription.getUserServiceInscriptionImplPort();

                        proxyInscription.inscription(userConnecte.getLogin(), userConnecte.getPassword());

                        JOptionPane.showMessageDialog(frame, "Vous êtes maintenant inscrit dans l'agence " + selectedAgence + " !");
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    } catch (UserAlreadyExistsException_Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Vous êtes déja inscrit dans cette agence.");
                    }
                }
            });

            mainPanel.add(topPanel, BorderLayout.NORTH);
            mainPanel.add(bottomPanel, BorderLayout.CENTER);

            frame.add(mainPanel);
            frame.setVisible(true);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    mainPanel.requestFocusInWindow();
                }
            });
        });

    }
}