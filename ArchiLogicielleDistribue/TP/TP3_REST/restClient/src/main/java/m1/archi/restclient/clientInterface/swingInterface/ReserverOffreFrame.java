package m1.archi.restclient.clientInterface.swingInterface;

import m1.archi.restclient.models.modelsAgence.Agence;
import m1.archi.restclient.models.modelsAgence.Utilisateur;
import m1.archi.restclient.models.modelsHotel.Offre;
import m1.archi.restclient.models.modelsHotel.Reservation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ReserverOffreFrame extends JFrame {
    private final JCheckBox petitDejeunerCheckBox;
    private final JTextField nomClientField;
    private final JTextField prenomClientField;
    private JTextField telephoneField;
    private final JTextField nomCarteField;
    private JTextField numeroCarteField;
    private JTextField expirationCarteField;
    private JTextField CCVCarteField;
    private final JButton reserverButton;
    private double montantTotal;
    private final JLabel montantTotalLabel;

    private final CustomContentPane customContentPane;
    private final Offre offre;
    private final Agence agence;
    private final Utilisateur utilisateur;
    private final RestTemplate proxyComparateur;

    private final String baseUri = "http://localhost:8100/comparateurService/api";
    private static final String font = "Palatino Linotype";


    public ReserverOffreFrame(CustomContentPane customContentPane, Agence agence, Offre offre, Utilisateur utilisateur, RestTemplate proxyComparateur) {
        super("Réservation de l'offre à l'hotel " + offre.getHotel().getNom() + " :");
        this.customContentPane = customContentPane;
        this.agence = agence;
        this.offre = offre;
        this.utilisateur = utilisateur;
        this.montantTotal = offre.getPrixAvecReduction();
        this.proxyComparateur = proxyComparateur;

        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nomClientField = createFormattedTextField(30);
        nomClientField.setText(utilisateur.getNom());
        prenomClientField = createFormattedTextField(30);
        prenomClientField.setText(utilisateur.getPrenom());

        // -- Petit déjeuner

        int nombreNuits = (int) (offre.getDateDepart().toLocalDate().toEpochDay() - offre.getDateArrivee().toLocalDate().toEpochDay());
        double montantPetitDejeuner = (offre.getHotel().getNombreEtoiles() * 4) * offre.getNombreLitsTotal() * nombreNuits;

        petitDejeunerCheckBox = new JCheckBox("<html> Petit déjeuner inclus ? <i><font color='green'>+" + montantPetitDejeuner + "€ (" + (offre.getHotel().getNombreEtoiles() * 4)+ "€/personne/repas)</font></i></html>");
        petitDejeunerCheckBox.setFont(new Font(font, Font.PLAIN, 24));

        petitDejeunerCheckBox.addItemListener(e -> {
            if (petitDejeunerCheckBox.isSelected()) {
                this.montantTotal += montantPetitDejeuner;
            } else {
                this.montantTotal -= montantPetitDejeuner;
            }
            updateTotalPriceDisplay();
        });


        // -- Carte
        nomCarteField = createFormattedTextField(30);
        try {
            MaskFormatter maskFormatter = new MaskFormatter("################");
            numeroCarteField = new JFormattedTextField(maskFormatter);
        } catch (ParseException e) {
            numeroCarteField = new JTextField(5);
        }
        numeroCarteField.setFont(new Font(font, Font.PLAIN, 24));

        try {
            MaskFormatter maskFormatter = new MaskFormatter("##/##");
            expirationCarteField = new JFormattedTextField(maskFormatter);
        } catch (ParseException e) {
            expirationCarteField = new JTextField(5);
        }
        expirationCarteField.setFont(new Font(font, Font.PLAIN, 24));

        try {
            MaskFormatter telephoneMask = new MaskFormatter("##.##.##.##.##");
            telephoneField = new JFormattedTextField(telephoneMask);

            MaskFormatter cvvMask = new MaskFormatter("###");
            CCVCarteField = new JFormattedTextField(cvvMask);
        } catch (ParseException e) {
            telephoneField = new JTextField(10);
            CCVCarteField = new JTextField(3);
        }
        telephoneField.setFont(new Font(font, Font.PLAIN, 24));
        CCVCarteField.setFont(new Font(font, Font.PLAIN, 24));

        reserverButton = new JButton("Réserver");
        reserverButton.addActionListener(e -> reserverOffre());

        // Add DocumentListener to each JTextField
        addDocumentListener(nomClientField);
        addDocumentListener(prenomClientField);
        addDocumentListener(telephoneField);
        addDocumentListener(nomCarteField);
        addDocumentListener(numeroCarteField);
        addDocumentListener(expirationCarteField);
        addDocumentListener(CCVCarteField);

        // Ajoutez les composants au panneau
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 0));
        JLabel clientLabel = createLabel("Information sur le client principal :");
        clientLabel.setFont(new Font(font, Font.ITALIC, 24));

        panel.add(clientLabel);
        panel.add(createLabel("Nom :"));
        panel.add(nomClientField);
        // Add an empty space of 10 pixels high
        panel.add(Box.createVerticalStrut(2));
        panel.add(createLabel("Prénom :"));
        panel.add(prenomClientField);
        // Add an empty space of 10 pixels high
        panel.add(Box.createVerticalStrut(2));
        panel.add(createLabel("Téléphone :"));
        panel.add(telephoneField);
        // Add an empty space of 10 pixels high
        panel.add(Box.createVerticalStrut(2));
        panel.add(petitDejeunerCheckBox);

        montantTotalLabel = createLabel("<html> Montant total de la réservation : <b><font color='red'>" + montantTotal + "€</font></b><html>");
        montantTotalLabel.setFont(new Font(font, Font.ITALIC, 24));
        panel.add(montantTotalLabel);

        panel.add(Box.createVerticalStrut(5));

        // Créer un séparateur
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.GRAY); // Couleur du trait
        separator.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le trait horizontalement
        panel.add(separator);


        // -- Carte
        JLabel carteLabel = createLabel("Information pour le paiement :");
        carteLabel.setFont(new Font(font, Font.ITALIC, 24));
        panel.add(carteLabel);
        panel.add(createLabel("Nom du titulaire :"));
        panel.add(nomCarteField);
        // Add an empty space of 10 pixels high
        panel.add(Box.createVerticalStrut(2));
        panel.add(createLabel("Numéro de carte :"));
        panel.add(numeroCarteField);
        // Add an empty space of 10 pixels high
        panel.add(Box.createVerticalStrut(2));

        JPanel expirationAndCvvPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        expirationAndCvvPanel.add(expirationCarteField);
        expirationAndCvvPanel.add(CCVCarteField);
        panel.add(createLabel("Expiration de la carte (MM/YY) et CCV de la carte :"));
        panel.add(expirationAndCvvPanel);

        panel.add(Box.createVerticalStrut(10));

        panel.add(reserverButton);

        checkFields();

        // Ajoutez le panneau à la fenêtre
        add(panel);

        // Définissez la taille et la position de la fenêtre
        setSize(600, 1000);
        setLocationRelativeTo(customContentPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void reserverOffre() {
        // Récupérez les valeurs saisies
        boolean petitDejeuner = petitDejeunerCheckBox.isSelected();
        String nomClient = nomClientField.getText();
        String prenomClient = prenomClientField.getText();
        String telephone = telephoneField.getText();
        String nomCarte = nomCarteField.getText();
        String numeroCarte = numeroCarteField.getText();
        String expirationCarte = expirationCarteField.getText();
        String CCVCarte = CCVCarteField.getText();

        String comparateurUri = baseUri + "/comparateur/reservation";

        try {
            // Construire l'URI de la requête avec les paramètres
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(comparateurUri)
                    .queryParam("email", utilisateur.getEmail())
                    .queryParam("motDePasse", utilisateur.getMotDePasse())
                    .queryParam("idAgence", agence.getIdAgence())
                    .queryParam("idHotel", offre.getHotel().getIdHotel())
                    .queryParam("idOffre", offre.getIdOffre())
                    .queryParam("petitDejeuner", petitDejeuner)
                    .queryParam("nomClient", nomClient)
                    .queryParam("prenomClient", prenomClient)
                    .queryParam("telephone", telephone)
                    .queryParam("nomCarte", nomCarte)
                    .queryParam("numeroCarte", numeroCarte)
                    .queryParam("expirationCarte", expirationCarte)
                    .queryParam("CCVCarte", CCVCarte);

            ParameterizedTypeReference<Reservation> typeReference = new ParameterizedTypeReference<>() {
            };

            // Appel à la méthode de reservation d'offres de l'hôtel via le proxyAgence
            ResponseEntity<Reservation> responseEntity = proxyComparateur.exchange(builder.buildAndExpand().toUri(), HttpMethod.POST, null, typeReference);
            Reservation reservation = responseEntity.getBody();

            if (reservation == null) {
                JOptionPane.showMessageDialog(this, "Problème lors de la réservation...", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                StringBuilder message = new StringBuilder();
                message.append("✅ Référence de la réservation : ").append(reservation.getIdReservation()).append("\n\n");
                message.append("La réservation au nom de ").append(nomClient).append(" ").append(prenomClient).append(" a été effectuée avec succès !\n");
                message.append("Vous avez reservé ").append(reservation.getChambresReservees().size()).append(" chambres, pour ").append(reservation.getNombrePersonnes()).append(" personnes, ");
                if (reservation.isPetitDejeuner())
                    message.append("avec petit déjeuner, ");
                else
                    message.append("sans petit déjeuner, ");
                message.append("pour un montant total de ").append(reservation.getMontantReservation()).append("€.\n\n");
                LocalDateTime dateArrivee = reservation.getDateArrivee();
                LocalDateTime dateDepart = reservation.getDateDepart();

                // Conversion de LocalDateTime en Date
                Date dateArriveeConverted = Date.from(dateArrivee.atZone(ZoneId.systemDefault()).toInstant());
                Date dateDepartConverted = Date.from(dateDepart.atZone(ZoneId.systemDefault()).toInstant());

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                String formattedDateArrivee = sdf.format(dateArriveeConverted);
                String formattedDateDepart = sdf.format(dateDepartConverted);

                message.append("La reservation a été effectuée du ").append(formattedDateArrivee).append(" au ").append(formattedDateDepart).append("\n");

                JOptionPane.showMessageDialog(this, message.toString(), "Réservation effectuée", JOptionPane.INFORMATION_MESSAGE);

                customContentPane.clean();
                dispose(); // Fermez la fenêtre après la réservation réussie
            }
        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(this, "Erreur lors de la réservation : " + e.getMessage(), "Erreur de réservation", JOptionPane.ERROR_MESSAGE);
        }
    }


    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(font, Font.PLAIN, 24)); // Increase the font size to 24
        return label;
    }

    private JTextField createFormattedTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font(font, Font.PLAIN, 24)); // Increase the font size to 24
        return field;
    }

    private void addDocumentListener(JTextField field) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkFields();
            }

            public void removeUpdate(DocumentEvent e) {
                checkFields();
            }

            public void insertUpdate(DocumentEvent e) {
                checkFields();
            }
        });
    }

    private void checkFields() {
        String nomClient = nomClientField.getText();
        String prenomClient = prenomClientField.getText();
        String telephone = telephoneField.getText();
        String nomCarte = nomCarteField.getText();
        String numeroCarte = numeroCarteField.getText();
        String expirationCarte = expirationCarteField.getText();
        String CCVCarte = CCVCarteField.getText();

        // Regular expressions for the expected formats
        String telephoneRegex = "\\d{2}\\.\\d{2}\\.\\d{2}\\.\\d{2}\\.\\d{2}";
        String numeroCarteRegex = "\\d{16}";
        String expirationCarteRegex = "\\d{2}/\\d{2}";
        String CCVCarteRegex = "\\d{3}";

        reserverButton.setEnabled(!nomClient.isEmpty() && !prenomClient.isEmpty() && !nomCarte.isEmpty() &&
                telephone.matches(telephoneRegex) && numeroCarte.matches(numeroCarteRegex) &&
                expirationCarte.matches(expirationCarteRegex) && CCVCarte.matches(CCVCarteRegex));
    }

    private void updateTotalPriceDisplay() {
        montantTotalLabel.setText("<html> Montant total de la réservation : <b><font color='red'>" + Math.round(montantTotal * 10) / 10 + "€</font></b><html>");
    }
}

