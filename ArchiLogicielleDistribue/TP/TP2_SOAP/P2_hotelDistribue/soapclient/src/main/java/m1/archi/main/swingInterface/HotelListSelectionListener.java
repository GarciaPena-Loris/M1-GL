package m1.archi.main.swingInterface;

import m1.archi.agence.Chambre;
import m1.archi.agence.Hotel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Base64;

public class HotelListSelectionListener implements ListSelectionListener {
    private final JFrame frame;

    public HotelListSelectionListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            JList<?> hotelListUI = (JList<?>) e.getSource();
            int selectedIndex = hotelListUI.getSelectedIndex();

            if (selectedIndex != -1) {
                Hotel selectedHotel = Interface.hotelsPartenaires.get(selectedIndex);
                showHotelDetails(selectedHotel);
            }
        }
    }

    private void showHotelDetails(Hotel selectedHotel) {
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
                    String resChambre = "<html><b>Chambre n°" + selectedChambre.getNumero() + ", proposé au prix de " + selectedChambre.getPrix() + "€ par nuit, " +
                            "disponible pour " + selectedChambre.getNombreLits() + " personnes.<br>" +
                            "Voici une image de la chambre : </html>";

                    // Ajoutez le texte au JTextPane
                    JTextPane chambreInfoTextPane = new JTextPane();
                    chambreInfoTextPane.setContentType("text/html");
                    chambreInfoTextPane.setEditable(false);
                    chambreInfoTextPane.setText(resChambre);
                    chambreInfoPanel.add(chambreInfoTextPane);

                    // Créez un panneau pour centrer l'image
                    JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    chambreInfoPanel.add(centerPanel);

                    // Créez un JLabel pour afficher l'image
                    JLabel chambreImageLabel = new JLabel();
                    String base64Image = selectedChambre.getImageChambre();
                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                    ImageIcon chambreImageIcon = new ImageIcon(imageBytes);
                    // Redimensionnez l'image pour harmoniser la taille
                    Image scaledImage = chambreImageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
                    chambreImageIcon = new ImageIcon(scaledImage);
                    chambreImageLabel.setIcon(chambreImageIcon);

                    // Ajoutez le JLabel au panneau centré
                    centerPanel.add(chambreImageLabel);

                    // Affichez le panneau d'informations de la chambre dans une fenêtre
                    JFrame chambreInfoFrame = new JFrame("Détails de la chambre " + selectedChambre.getNumero());
                    chambreInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    chambreInfoFrame.getContentPane().add(chambreInfoPanel);
                    chambreInfoFrame.pack();
                    chambreInfoFrame.setLocationRelativeTo(frame);
                    chambreInfoFrame.setVisible(true);
                }
            }
        });

        hotelInfoPanel.add(new JScrollPane(chambreList));

        // Créez un panneau pour centrer l'image
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        hotelInfoPanel.add(centerPanel);

        // Créez un JLabel pour afficher l'image
        JLabel hotelImageLabel = new JLabel();
        String base64Image = selectedHotel.getImageHotel();
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ImageIcon hotelImageIcon = new ImageIcon(imageBytes);
        // Redimensionnez l'image pour harmoniser la taille
        Image scaledImage = hotelImageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        hotelImageIcon = new ImageIcon(scaledImage);
        hotelImageLabel.setIcon(hotelImageIcon);

        // Ajoutez le JLabel au panneau centré
        centerPanel.add(hotelImageLabel);


        JFrame hotelInfoFrame = new JFrame("Détails de l'hôtel " + selectedHotel.getIdentifiant());
        hotelInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        hotelInfoFrame.getContentPane().add(hotelInfoPanel);
        hotelInfoFrame.pack();
        hotelInfoFrame.setLocationRelativeTo(frame);
        hotelInfoFrame.setVisible(true);
    }
}

