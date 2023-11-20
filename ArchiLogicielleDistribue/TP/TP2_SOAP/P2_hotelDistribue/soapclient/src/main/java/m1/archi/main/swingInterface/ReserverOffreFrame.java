package m1.archi.main.swingInterface;

import m1.archi.agence.*;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReserverOffreFrame extends JFrame {
    private final JCheckBox petitDejeunerCheckBox;
    private final JTextField nomClientField;
    private final JTextField prenomClientField;
    private final JTextField emailField;
    private final JTextField telephoneField;
    private final JTextField nomCarteField;
    private final JTextField numeroCarteField;
    private final JTextField expirationCarteField;
    private final JTextField CCVCarteField;
    private final AgenceServiceReservation proxyReservation;
    private final Offre offre;
    private final JFrame parent;

    public ReserverOffreFrame(Offre offre, JFrame parent) throws MalformedURLException {
        super("Réservation d'offre");

        URL url = new URL("http://localhost:8090/agencesservice/" + Interface.selectedAgence + "/reservation");
        AgenceServiceReservationImplService agenceServiceReservation = new AgenceServiceReservationImplService(url);

        this.proxyReservation = agenceServiceReservation.getAgenceServiceReservationImplPort();
        this.offre = offre;
        this.parent = parent;

        nomClientField = createFormattedTextField(30);
        prenomClientField = createFormattedTextField(30);
        emailField = createFormattedTextField(50);
        telephoneField = createFormattedTextField(10);
        nomCarteField = createFormattedTextField(30);
        numeroCarteField = createFormattedTextField(16);
        expirationCarteField = createFormattedTextField(5);
        CCVCarteField = createFormattedTextField(3);
        petitDejeunerCheckBox = new JCheckBox("Petit déjeuner inclus");

        JButton reserverButton = new JButton("Réserver");
        reserverButton.setEnabled(false);
        reserverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reserverOffre();
            }
        });

        // Créez une instance de votre classe InputDocumentListener pour activer/désactiver le bouton
        InputDocumentListener inputDocumentListener = new InputDocumentListener(reserverButton, nomClientField, prenomClientField, emailField, telephoneField, nomCarteField, numeroCarteField, expirationCarteField, CCVCarteField);

        // Ajoutez les composants au panneau
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.add(new JLabel("  Nom du client principal :"));
        panel.add(nomClientField);
        panel.add(new JLabel("  Prénom du client principal :"));
        panel.add(prenomClientField);
        panel.add(new JLabel("  Email :"));
        panel.add(emailField);
        panel.add(new JLabel("  Téléphone :"));
        panel.add(telephoneField);
        panel.add(new JLabel("  Petit déjeuner inclus :"));
        panel.add(petitDejeunerCheckBox);
        panel.add(new JLabel("  Nom sur la carte :"));
        panel.add(nomCarteField);
        panel.add(new JLabel("  Numéro de carte :"));
        panel.add(numeroCarteField);
        panel.add(new JLabel("  Expiration de la carte :"));
        panel.add(expirationCarteField);
        panel.add(new JLabel("  CCV de la carte :"));
        panel.add(CCVCarteField);
        panel.add(new JLabel(""));
        panel.add(reserverButton);

        // Ajoutez le panneau à la fenêtre
        add(panel);

        // Définissez la taille et la position de la fenêtre
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void reserverOffre() {
        // Récupérez les valeurs saisies
        boolean petitDejeuner = petitDejeunerCheckBox.isSelected();
        String nomClient = nomClientField.getText();
        String prenomClient = prenomClientField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();
        String nomCarte = nomCarteField.getText();
        String numeroCarte = numeroCarteField.getText();
        String expirationCarte = expirationCarteField.getText();
        String CCVCarte = CCVCarteField.getText();

        // Appelez la méthode de réservation avec les paramètres nécessaires
        try {
            Reservation reservation = proxyReservation.reserverChambresHotel(Interface.userConnecte.getLogin(), Interface.userConnecte.getMotDePasse(), offre, petitDejeuner, nomClient, prenomClient, email, telephone, nomCarte, numeroCarte, expirationCarte, CCVCarte);
            StringBuilder message = new StringBuilder();
            message.append("✅ Référence de la réservation : ").append(reservation.getNumero()).append("\n\n");
            message.append("La réservation au nom de ").append(reservation.getClientPrincipal().getNom()).append(" ").append(reservation.getClientPrincipal().getPrenom()).append(" a été effectuée avec succès !\n");
            message.append("Vous avez reservé ").append(reservation.getChambresReservees().size()).append(" chambres, pour ").append(reservation.getNombrePersonnes()).append(" personnes, ");
            if (reservation.isPetitDejeuner())
                message.append("avec petit déjeuner, ");
            else
                message.append("sans petit déjeuner, ");
            message.append("pour un montant total de ").append(reservation.getMontantReservation()).append("€.\n\n");
            Date dateArrivee = reservation.getDateArrivee().toGregorianCalendar().getTime();
            Date dateDepart = reservation.getDateDepart().toGregorianCalendar().getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            String formattedDateArrivee = sdf.format(dateArrivee);
            String formattedDateDepart = sdf.format(dateDepart);

            message.append("La reservation a été effectuée du ").append(formattedDateArrivee).append(" au ").append(formattedDateDepart).append("\n");

            JOptionPane.showMessageDialog(this, message.toString(), "Réservation effectuée", JOptionPane.INFORMATION_MESSAGE);
            parent.dispose();
            dispose(); // Fermez la fenêtre après la réservation réussie
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la réservation : " + ex.getMessage(), "Erreur de réservation", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JTextField createFormattedTextField(int columns) {
        // Ajoutez le DocumentListener ici si nécessaire
        return new JTextField(columns);
    }
}

