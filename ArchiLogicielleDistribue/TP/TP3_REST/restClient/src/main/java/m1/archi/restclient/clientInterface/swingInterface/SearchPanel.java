package m1.archi.restclient.clientInterface.swingInterface;

import m1.archi.restclient.models.modelsHotel.Offre;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPanel extends JPanel {
    private final RestTemplate proxyComparateur;
    private final JPanel mainPanel;
    private final String baseUri = "http://localhost:81000/comparateurService/api";

    public SearchPanel(JPanel mainPanel, RestTemplate proxyComparateur) {
        this.mainPanel = mainPanel;
        this.proxyComparateur = proxyComparateur;

        JPanel searchPanel = new JPanel(new FlowLayout());

        // Ville d'hébergement
        JTextField cityTextField = new JTextField(20);
        searchPanel.add(new JLabel("Ville d'hébergement:"));
        searchPanel.add(cityTextField);

        // Dates de réservation
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        JFormattedTextField startDateField = new JFormattedTextField(dateFormat);
        startDateField.setColumns(10);
        JFormattedTextField endDateField = new JFormattedTextField(dateFormat);
        endDateField.setColumns(10);
        searchPanel.add(new JLabel("Date début:"));
        searchPanel.add(startDateField);
        searchPanel.add(new JLabel("Date fin:"));
        searchPanel.add(endDateField);

        // Nombre de personnes à héberger
        JSpinner peopleSpinner = createSpinner(1, 1, 10);
        searchPanel.add(new JLabel("Nombre de personnes:"));
        searchPanel.add(peopleSpinner);

        // Nombre minimum d'étoiles
        JSpinner starsSpinner = createSpinner(1, 1, 5);
        searchPanel.add(new JLabel("Nombre minimum d'étoiles:"));
        searchPanel.add(starsSpinner);

        // Bouton de recherche
        JButton searchButton = new JButton("Rechercher");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action à effectuer lors du clic sur le bouton de recherche
                performSearch(cityTextField.getText(), startDateField.getText(), endDateField.getText(),
                        (int) peopleSpinner.getValue(), (int) starsSpinner.getValue());
            }
        });
        searchPanel.add(searchButton);
    }


    private void performSearch(String ville, String dateArrivee, String dateDepart, int nombrePersonne, int nombreEtoilesMin) {
        String villeEncodee = URLEncoder.encode(ville, StandardCharsets.UTF_8);
        String comparateurUri = baseUri + "/comparateurs/recherche";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(comparateurUri)
                .queryParam("ville", villeEncodee)
                .queryParam("dateArrivee", dateArrivee)
                .queryParam("dateDepart", dateDepart)
                .queryParam("nombrePersonne", nombrePersonne)
                .queryParam("nombreEtoiles", nombreEtoilesMin);


        ParameterizedTypeReference<Map<String, List<List<Offre>>>> typeReference = new ParameterizedTypeReference<>() {
        };

        // Appel à la méthode de recherche d'offres de l'hôtel via le proxyHotel
        ResponseEntity<Map<String, List<List<Offre>>>> responseEntity = proxyComparateur.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null, typeReference);
        Map<String, List<List<Offre>>> mapAgenceOffres = responseEntity.getBody();

        if (mapAgenceOffres == null || mapAgenceOffres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune offre n'est disponible...", "Résultat", JOptionPane.ERROR_MESSAGE);
        } else {
            mainPanel.add(new ResultPanel(mainPanel, mapAgenceOffres), BorderLayout.CENTER);
        }
    }

    private JSpinner createSpinner(int init, int min, int max) {
        JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(init, min, max, 1));
        spinner.setFont(new Font("Arial", Font.PLAIN, 18));

        spinner.addChangeListener(e -> {
            // Répondez aux changements de valeur ici si nécessaire
        });

        return spinner;
    }
}
