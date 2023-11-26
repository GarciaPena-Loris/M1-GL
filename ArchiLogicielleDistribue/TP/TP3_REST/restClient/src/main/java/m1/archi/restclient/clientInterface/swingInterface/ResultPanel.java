package m1.archi.restclient.clientInterface.swingInterface;

import m1.archi.restclient.models.modelsHotel.Offre;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ResultPanel extends JScrollPane {
    public ResultPanel(JPanel mainPanel, Map<String, List<List<Offre>>> mapAgenceOffres) {
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        for (Map.Entry<String, List<List<Offre>>> entry : mapAgenceOffres.entrySet()) {
            String agenceName = entry.getKey();
            List<List<Offre>> offres = entry.getValue();

            JLabel agenceLabel = new JLabel("Agence: " + agenceName);
            innerPanel.add(agenceLabel);

            // Ajoutez les offres
            for (List<Offre> offreList : offres) {
                for (Offre offre : offreList) {
                    JLabel offreLabel = new JLabel("Offre: " + offre.toString());
                    innerPanel.add(offreLabel);
                }
            }

            innerPanel.add(Box.createVerticalStrut(10)); // Ajoutez un espace entre les agences
        }

        // Configurez le panel interne
        innerPanel.setAlignmentX(LEFT_ALIGNMENT);

        // Ajoutez le panel interne à la barre de défilement
        setViewportView(innerPanel);
        
    }
}
