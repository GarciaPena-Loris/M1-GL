package m1.archi.restclient.clientInterface.swingInterface;

import m1.archi.restclient.models.modelsComparateur.Comparateur;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;

public class Interface {
    private final Comparateur comparateur;
    private final RestTemplate proxyComparateur;
    private final String baseUri = "http://localhost:8100/comparateurService/api";

    public Interface(RestTemplate proxyComparateur) {
        // Récupération le comparateur
        comparateur = proxyComparateur.getForObject(baseUri + "/comparateur", Comparateur.class);
        this.proxyComparateur = proxyComparateur;

        // Création de l'interface graphique
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            String nomComparateur = comparateur.getNom();
            JFrame frame = new JFrame("Comparateur " + nomComparateur);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            // Création de la barre de recherche
            JPanel searchPanel = new SearchPanel(mainPanel, proxyComparateur);
            mainPanel.add(searchPanel, BorderLayout.NORTH);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 1000);
            frame.getContentPane().add(mainPanel);
            frame.setVisible(true);
        });
    }
}