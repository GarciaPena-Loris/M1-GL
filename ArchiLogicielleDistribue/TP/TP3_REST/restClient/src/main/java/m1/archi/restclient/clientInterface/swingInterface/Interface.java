package m1.archi.restclient.clientInterface.swingInterface;

import m1.archi.restclient.exceptions.InterfaceException;
import m1.archi.restclient.models.modelsComparateur.Comparateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.awt.*;

public class Interface {
    private final Comparateur comparateur;
    private final RestTemplate proxyComparateur;
    private final String baseUri = "http://localhost:8100/comparateurService/api";

    public Interface(RestTemplate proxyComparateur) throws InterfaceException {
        if (proxyComparateur == null)
            throw new InterfaceException("Problème de connexion avec le comparateur");

        this.proxyComparateur = proxyComparateur;
        // Récupération le comparateur
        comparateur = proxyComparateur.getForObject(baseUri + "/comparateur", Comparateur.class);

        if (comparateur == null)
            throw new InterfaceException("Problème lors de la récupération du comparateur");

        // Création de l'interface graphique
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        SwingUtilities.invokeLater(() -> {
            String nomComparateur = comparateur.getNom();
            JFrame frame = new JFrame("Bienvenue sur le comparateur d'agences " + nomComparateur + " :");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());
            mainPanel.setBackground(new Color(0, 0, 255, 179));

            // Création de la barre de recherche
            JPanel searchPanel = new SearchPanel(mainPanel, proxyComparateur);
            mainPanel.add(searchPanel, BorderLayout.NORTH);

            frame.getContentPane().add(mainPanel);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}