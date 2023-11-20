package m1.archi.main.swingInterface;

import m1.archi.agence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Interface {
    private final HashMap<String, String> mapAgenceNomIdentifiant = new HashMap<>();
    private final HashMap<String, ArrayList<Hotel>> mapAgenceHotelPartenaire = new HashMap<>();
    private final HashMap<String, Integer> mapIdentifiantsHotelsPartenairesReduction = new HashMap<>();

    private ArrayList<String> listeVilles = new ArrayList<>();
    private final ArrayList<String> listeIdentifiantAgence;
    public static String selectedAgence;
    public static User userConnecte;
    public static ArrayList<Hotel> hotelsPartenaires = new ArrayList<Hotel>();

    public Interface(AgenceServiceIdentification proxyAgences) throws MalformedURLException {
        // Récupération des identifiants des agences
        listeIdentifiantAgence = (ArrayList<String>) proxyAgences.getListeAgence();

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

            JPanel agencePanel = new JPanel();
            agencePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            agencePanel.add(agenceLabel);
            agencePanel.add(agenceComboBox);

            // Étape 2 : Liste d'hôtels
            DefaultListModel<String> hotelListModel = new DefaultListModel<>();
            JList<String> hotelListUI = new JList<>(hotelListModel);
            JScrollPane scrollPane = new JScrollPane(hotelListUI);
            bottomPanel.add(scrollPane, BorderLayout.CENTER);

            // Étape 3 : Recherche et réservation
            JButton searchButton = new JButton("Effectuer une recherche");
            bottomPanel.add(searchButton, BorderLayout.SOUTH);

            // Étape 4 : Recherche et réservation
            JPanel connectionPanel = new JPanel();
            connectionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

            JButton deconnectionButton = new JButton("Deconnection");
            deconnectionButton.setEnabled(false);
            connectionPanel.add(deconnectionButton);

            topPanel.setLayout(new BorderLayout());
            topPanel.add(agencePanel, BorderLayout.WEST);
            topPanel.add(connectionPanel, BorderLayout.EAST);

            // Étape 5 : S'incrire dans une agence
            JButton inscriptionButton = new JButton("S'enregistrer à l'agence");
            bottomPanel.add(inscriptionButton, BorderLayout.NORTH);


            // Événement de sélection de l'agence
            agenceComboBox.addActionListener(e -> {
                selectedAgence = mapAgenceNomIdentifiant.get(agenceComboBox.getSelectedItem());

                // Affichage du message de chargement
                userConnecte = null;
                deconnectionButton.setEnabled(false);

                hotelListModel.clear();
                hotelListModel.addElement("Chargement en cours...");

                // Effectuer la requête SOAP dans un thread séparé
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            mapIdentifiantsHotelsPartenairesReduction.clear();
                            listeVilles.clear();
                            if (mapAgenceHotelPartenaire.get(selectedAgence) == null) {
                                hotelsPartenaires = (ArrayList<Hotel>) proxyAgences.getListeHotelsPartenaire(selectedAgence);
                                mapAgenceHotelPartenaire.put(selectedAgence, hotelsPartenaires);
                                for (Hotel hotel : hotelsPartenaires) {
                                    if (!listeVilles.contains(hotel.getAdresse().getVille()))
                                        listeVilles.add(hotel.getAdresse().getVille());
                                    mapIdentifiantsHotelsPartenairesReduction.put(hotel.getIdentifiant(), proxyAgences.getReduction(selectedAgence, hotel.getIdentifiant()));
                                }
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
                            hotelString.append(") ").append("situé à ").append(hotel.getAdresse().getVille()).append(" (").append(hotel.getAdresse().getPays()).append(") possède ").append(hotel.getChambres().size()).append(" chambres et est proposé avec une réduction de ").append(mapIdentifiantsHotelsPartenairesReduction.get(hotel.getIdentifiant())).append("%. ");
                            hotelListModel.addElement(hotelString.toString());
                        }
                    }
                };
                worker.execute(); // Démarre le travail dans un thread séparé
            });

            // Créer une instance de HotelListSelectionListener
            HotelListSelectionListener hotelListSelectionListener = new HotelListSelectionListener(frame);
            // Ajouter l'écouteur à la liste d'hôtels
            hotelListUI.addListSelectionListener(hotelListSelectionListener);

            // Événement pour la connection d'un utilisateur
            deconnectionButton.addActionListener(e -> {
                userConnecte = null;
                deconnectionButton.setEnabled(false);
            });

            // Événement pour rechercher des offres
            searchButton.addActionListener(e -> {
                if (selectedAgence != null) {
                    // Ouvrir la fenêtre de recherche
                    SearchDialog searchDialog = null;
                    try {
                        searchDialog = new SearchDialog(frame, selectedAgence, proxyAgences, listeVilles);
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
                } else {
                    // Ouvrir la fenêtre de recherche
                    try {
                        LoginDialog loginDialog = new LoginDialog(frame, (String) agenceComboBox.getSelectedItem());
                        loginDialog.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                userConnecte = loginDialog.getUserConnecte();
                                if (userConnecte != null) {
                                    deconnectionButton.setEnabled(true);
                                }
                            }
                        });
                        loginDialog.setVisible(true);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
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