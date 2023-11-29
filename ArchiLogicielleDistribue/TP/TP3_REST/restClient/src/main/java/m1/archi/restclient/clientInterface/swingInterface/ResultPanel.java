package m1.archi.restclient.clientInterface.swingInterface;

import m1.archi.restclient.models.modelsHotel.Chambre;
import m1.archi.restclient.models.modelsHotel.Hotel;
import m1.archi.restclient.models.modelsHotel.Offre;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ResultPanel extends JPanel {
    private final RestTemplate proxyComparateur = new RestTemplate();
    private final Map<Long, Color> hotelColorMap = new HashMap<>();
    private final Map<Long, ImageIcon> chambreImageMap = new HashMap<>();
    private final String font = "Palatino Linotype";
    private final Random RANDOM = new Random();

    public ResultPanel(Map<String, List<List<Offre>>> mapAgenceOffres, RestTemplate proxyComparateur) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Créer un panneau pour contenir les agencePanels
        JPanel agencePanelContainer = new JPanel();
        agencePanelContainer.setLayout(new BoxLayout(agencePanelContainer, BoxLayout.Y_AXIS));

        for (Map.Entry<String, List<List<Offre>>> entry : mapAgenceOffres.entrySet()) { // Pour chaque agence
            String agenceName = entry.getKey();
            List<List<Offre>> offres = entry.getValue();
            // Trier par hotel
            offres.sort(Comparator.comparing(o -> o.get(0).getHotel().getNombreEtoiles()));

            JPanel agencePanel = createAgencePanel(agenceName, offres);
            agencePanelContainer.add(agencePanel);
            agencePanelContainer.add(createSeparator());
        }

        JScrollPane scrollPane = new JScrollPane(agencePanelContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

    }

    private JPanel createAgencePanel(String agenceName, List<List<Offre>> offres) {
        JPanel agencePanel = new JPanel(new BorderLayout());
        agencePanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        agencePanel.setOpaque(false);

        JLabel agenceLabel;
        if (offres.size() == 1)
            agenceLabel = new JLabel(agenceName + " (" + offres.size() + " hotel) : ");
        else
            agenceLabel = new JLabel(agenceName + " (" + offres.size() + " hotels) : ");
        agenceLabel.setFont(new Font(font, Font.BOLD, 32));
        agenceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        agencePanel.add(agenceLabel, BorderLayout.NORTH);

        JPanel offrePanelContainer = new JPanel();
        offrePanelContainer.setLayout(new BoxLayout(offrePanelContainer, BoxLayout.Y_AXIS));
        offrePanelContainer.setOpaque(false);

        for (List<Offre> offreList : offres) { // Pour chaque hôtel
            // Les trier par prix
            offreList.sort(Comparator.comparing(Offre::getPrixAvecReduction));

            Hotel hotel = offreList.get(0).getHotel();
            hotelColorMap.putIfAbsent(hotel.getIdHotel(), getRandomColorWithTransparency());
            JLabel hotelLabel = createHotelLabel(hotel, offreList.size(), hotelColorMap.get(hotel.getIdHotel()));
            JPanel hotelLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            hotelLabelPanel.add(hotelLabel);
            offrePanelContainer.add(hotelLabelPanel);

            JPanel hotelPanelContainer = new JPanel();
            hotelPanelContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
            hotelPanelContainer.setOpaque(false);

            for (Offre offre : offreList) {
                JPanel offrePanel = createOffrePanel(offre, hotelColorMap.get(hotel.getIdHotel()));
                hotelPanelContainer.add(offrePanel);

                // Si ce n'est pas la dernière offre, ajouter un séparateur
                if (offreList.indexOf(offre) != offreList.size() - 1) {
                    JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
                    separator.setForeground(hotelColorMap.get(hotel.getIdHotel())); // Set separator color to hotelColor
                    separator.setPreferredSize(new Dimension(2, 150));
                    hotelPanelContainer.add(separator);
                }
            }

            JPanel offrePanelContainerVertical = new JPanel();
            offrePanelContainerVertical.setLayout(new BoxLayout(offrePanelContainerVertical, BoxLayout.Y_AXIS));
            offrePanelContainerVertical.setBorder(new EmptyBorder(0, 20, 0, 0));
            offrePanelContainerVertical.add(hotelPanelContainer);

            offrePanelContainer.add(offrePanelContainerVertical);
        }

        agencePanel.add(offrePanelContainer, BorderLayout.CENTER);

        return agencePanel;
    }

    private JPanel createOffrePanel(Offre offre, Color hotelColor) {
        JPanel offrePanel = new JPanel(new BorderLayout());
        offrePanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        //Calculer la reduction en % entre le prix et le prix réduit
        int reduction = (int) ((1 - (offre.getPrixAvecReduction() / offre.getPrix())) * 100);

        // Texte centré en haut
        JLabel infoLabel;
        if (offre.getChambres().size() == 1)
            infoLabel = new JLabel("<html><div style='text-align: center;'><b>" + offre.getChambres().size() + "</b> chambre au prix de " + offre.getPrix() + "€, proposé au prix de <b><font color='red'>" + offre.getPrixAvecReduction() + "€ </b> grâce à une réduction de <b>" + reduction + "% </b> :</div></html>");
        else
            infoLabel = new JLabel("<html><div style='text-align: center;'><b>" + offre.getChambres().size() + "</b> chambres au prix de " + offre.getPrix() + "€, proposées au prix de <b><font color='red'>" + offre.getPrixAvecReduction() + "€</b> :</div></html>");
        infoLabel.setFont(new Font(font, Font.PLAIN, 15));
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel photoChambreContainer = new JPanel();
        photoChambreContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        photoChambreContainer.setOpaque(false);

        for (Chambre chambre : offre.getChambres()) {
            try {
                String base64ImageChambre = chambre.getImageChambre();
                if (base64ImageChambre == null || base64ImageChambre.isEmpty())
                    throw new Exception("Erreur lors de la récupération de la chambre");
                if (!chambreImageMap.containsKey(chambre.getIdChambre())) {
                    byte[] imageBytes = Base64.getDecoder().decode(base64ImageChambre);
                    ImageIcon chambreImageIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH));
                    chambreImageMap.put(chambre.getIdChambre(), chambreImageIcon);
                }
                // Créer un panel pour chaque chambre
                JPanel chambrePanel = new JPanel();
                chambrePanel.setLayout(new BoxLayout(chambrePanel, BoxLayout.Y_AXIS));

                // Ajouter l'image de la chambre au panel
                JLabel chambreImage = new JLabel(chambreImageMap.get(chambre.getIdChambre()));
                chambreImage.setBorder(BorderFactory.createLineBorder(hotelColor, 2));
                chambreImage.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer l'image
                chambrePanel.add(chambreImage);

                // Créer un label pour le nombre de places dans la chambre
                JLabel placesLabel;
                if (chambre.getNombreLits() == 1)
                    placesLabel = new JLabel("Chambre " + chambre.getNombreLits() + " place, pour " + chambre.getPrix() + "€");
                else
                    placesLabel = new JLabel("Chambre " + chambre.getNombreLits() + " places, pour " + chambre.getPrix() + "€");
                placesLabel.setFont(new Font(font, Font.PLAIN, 13));
                placesLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le label
                placesLabel.setBorder(new EmptyBorder(5, 0, 0, 0));

                // Ajouter le label au panel
                chambrePanel.add(placesLabel);

                // Ajouter le panel au container
                photoChambreContainer.add(chambrePanel);
            } catch (Exception e) {
                System.out.println("Erreur lors de la récupération de la chambre");
            }
        }

        offrePanel.add(infoLabel, BorderLayout.NORTH);
        offrePanel.add(photoChambreContainer, BorderLayout.CENTER);

        return offrePanel;
    }


    private JLabel createHotelLabel(Hotel hotel, int nombreOffre, Color hotelColor) {
        // Créer un label pour l'hôtel
        JLabel hotelLabel;
        String adresseHotel = hotel.getAdresse().getNumero() + " " + hotel.getAdresse().getRue();

        if (nombreOffre == 1)
            hotelLabel = new JLabel("<html><div style='font-family: Palatino Linotype; font-size: 17px; font-weight: bold;'> · L'hotel <font color='" + getColorHexString(hotelColor) + "'> " + hotel.getNom() + "</font> <font color='#B8860B'>" + "⭐".repeat(Math.max(0, hotel.getNombreEtoiles())) + "</font>, " + adresseHotel + " <i>(" + nombreOffre + " option)</i> :</div></html>");
        else
            hotelLabel = new JLabel("<html><div style='font-family: Palatino Linotype; font-size: 17px; font-weight: bold;'> · L'hotel <font color='" + getColorHexString(hotelColor) + "'> " + hotel.getNom() + "</font> <font color='#B8860B'>" + "⭐".repeat(Math.max(0, hotel.getNombreEtoiles())) + "</font>, " + adresseHotel + "  <i>(" + nombreOffre + " options)</i> :</div></html>");
        hotelLabel.setFont(new Font(font, Font.BOLD, 17));

        return hotelLabel;
    }

    private String getColorHexString(Color color) {
        // Convertir la couleur en représentation hexadécimale
        return String.format("#%06x", color.getRGB() & 0xFFFFFF);
    }

    private Color getRandomColorWithTransparency() {
        int red = RANDOM.nextInt(201);
        int green = RANDOM.nextInt(201);
        int blue = RANDOM.nextInt(201);
        return new Color(red, green, blue);
    }


    private JSeparator createSeparator() {
        // Créer un séparateur
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.GRAY); // Couleur du trait
        separator.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le trait horizontalement

        return separator;
    }

}