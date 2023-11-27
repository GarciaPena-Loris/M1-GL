package m1.archi.restclient.clientInterface.swingInterface;

import m1.archi.restclient.models.modelsHotel.Hotel;
import m1.archi.restclient.models.modelsHotel.Offre;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ResultPanel extends JPanel {
    private static final int MAX_AGENCY_WIDTH = 600;
    private static final int MAIN_CONTAINER_MARGIN = 20;
    private static final Random RANDOM = new Random();

    public ResultPanel(Map<String, List<List<Offre>>> mapAgenceOffres, RestTemplate proxyComparateur) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(MAIN_CONTAINER_MARGIN, MAIN_CONTAINER_MARGIN, MAIN_CONTAINER_MARGIN, MAIN_CONTAINER_MARGIN));

        JPanel contentPane = new JPanel(new BorderLayout());
        AbstractBorder brdrRight = new TextBubbleBorder(new Color(191, 126, 234, 0x7F), 6, 20, 20, false);
        contentPane.setBorder(brdrRight);
        contentPane.setOpaque(true);

        // Créer le panneau global avec un GridLayout
        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        innerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        innerPanel.setOpaque(false);

        for (Map.Entry<String, List<List<Offre>>> entry : mapAgenceOffres.entrySet()) {
            String agenceName = entry.getKey();
            List<List<Offre>> offres = entry.getValue();

            // Créer un panneau pour chaque agence
            JPanel agencePanel = createAgencePanel(agenceName, offres);

            // Ajouter le panneau de l'agence au panneau global
            innerPanel.add(agencePanel);
        }
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        add(contentPane, BorderLayout.CENTER);
    }

    private JPanel createAgencePanel(String agenceName, List<List<Offre>> offres) {
        // Générer une couleur aléatoire avec légère transparence
        Color randomColor = getRandomColorWithTransparency();

        JPanel agencePanel = new JPanel();
        agencePanel.setLayout(new BoxLayout(agencePanel, BoxLayout.Y_AXIS));
        AbstractBorder brdrLeft = new TextBubbleBorder(randomColor, 2, 15, 15);
        agencePanel.setBorder(brdrLeft);

        agencePanel.setOpaque(true);

        // Appliquer la couleur de fond au panneau de l'agence
        agencePanel.setBackground(randomColor);

        JLabel agenceLabel = new JLabel(agenceName + " : ");
        agenceLabel.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
        agenceLabel.setHorizontalAlignment(JLabel.LEFT);

        agencePanel.add(agenceLabel);

        // Ajoutez les offres triées au panneau de l'agence

        // Définir une couleur unique pour chaque hôtel
        Map<Hotel, Color> hotelColors = new HashMap<>();

        // Ajoutez les offres triées au panneau de l'agence
        for (List<Offre> offreList : offres) {
            Hotel hotel = offreList.get(0).getHotel();
            int nombreOffre = offreList.size();
            JLabel hotelLabel = createHotelLabel(hotel, nombreOffre);
            hotelColors.put(hotel, getRandomColorWithTransparency());
            agencePanel.add(hotelLabel);

            for (Offre offre : offreList) {
                // Créer un panneau pour chaque offre avec le trait entre les hôtels différents
                JPanel offrePanel = createOffrePanel(offre, hotelColors.get(hotel));
                offrePanel.setOpaque(false);
                agencePanel.add(offrePanel);
                agencePanel.add(Box.createVerticalStrut(10));
            }

            agencePanel.add(createSeparator());
        }

        // Définir la taille maximale du panneau de l'agence
        agencePanel.setPreferredSize(new Dimension(MAX_AGENCY_WIDTH, 1000));

        return agencePanel;
    }

    private Color getRandomColorWithTransparency() {
        int red = RANDOM.nextInt(256);
        int green = RANDOM.nextInt(256);
        int blue = RANDOM.nextInt(256);
        int alpha = 150; // Réglez la transparence ici (0-255), 0 étant complètement transparent et 255 étant complètement opaque
        return new Color(red, green, blue, alpha);
    }

    private JPanel createOffrePanel(Offre offre, Color hotelColor) {
        // Créer un RoundedPanel pour chaque offre
        RoundedPanel offrePanel = new RoundedPanel(30, hotelColor, hotelColor);
        offrePanel.setLayout(new BoxLayout(offrePanel, BoxLayout.Y_AXIS));
        offrePanel.setPreferredSize(new Dimension(MAX_AGENCY_WIDTH - 30, 100));

        // Ajouter le texte de l'offre au panneau de l'offre
        JLabel nombreLitsLabel = new JLabel("Nombre de lits : " + offre.getNombreLitsTotal());
        JLabel prixLabel = new JLabel("Prix : " + offre.getPrix());
        prixLabel.setForeground(Color.GRAY);
        JLabel prixAvecReductionLabel = new JLabel("Prix avec réduction : " + offre.getPrixAvecReduction());
        prixAvecReductionLabel.setForeground(Color.RED); // Mettre le prix avec réduction en vert

        // Ajouter les labels au panneau de l'offre
        offrePanel.add(nombreLitsLabel);
        offrePanel.add(prixLabel);
        offrePanel.add(prixAvecReductionLabel);

        return offrePanel;
    }

    private JLabel createHotelLabel(Hotel hotel, int nombreOffre) {
        // Créer un label pour l'hôtel
        JLabel hotelLabel;
        if (nombreOffre == 1) {
            hotelLabel = new JLabel(nombreOffre + " offre pour l'hôtel '" + hotel.getNom() + "' : ");
        }
        else {
            hotelLabel = new JLabel(nombreOffre + " offres pour l'hôtel '" + hotel.getNom() + "' : ");
        }
        hotelLabel.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
        hotelLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le texte horizontalement

        return hotelLabel;
    }

    private JSeparator createSeparator() {
        // Créer un séparateur
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(Color.GRAY); // Couleur du trait
        separator.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer le trait horizontalement

        return separator;
    }

}