package m1.archi.main.swingInterface;

import m1.archi.agence.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Base64;

public class ResultatsFrame extends JFrame {

        public ResultatsFrame(ArrayList<Offres> listeOffres, JFrame parent, AgenceServiceIdentification proxyAgence) throws HotelNotFoundExceptionException {
            setTitle("Résultats de la recherche");
            setSize(800, 600);

            // Créez un modèle de liste pour afficher les offres
            DefaultListModel<String> offresListModel = new DefaultListModel<>();
            JList<String> offresList = new JList<>(offresListModel);

            // Ajoutez les offres au modèle de liste
            for (Offres offres : listeOffres) {
                Hotel hotel = proxyAgence.getHotel(offres.getOffres().get(0).getIdHotel());
                String nomHotel = hotel.getNom();
                offresListModel.addElement("Offres pour l'hotel " + nomHotel + ", situé à " + hotel.getAdresse().getVille() + " :");
            }

            // Ajoutez un gestionnaire de sélection à la liste
            offresList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    // Obtenez l'offre sélectionnée
                    Offres selectedOffres = listeOffres.get(offresList.getSelectedIndex());

                    // Créez une nouvelle fenêtre pour afficher les détails de l'offre
                    DetailsOffreFrame detailsOffreFrame = null;
                    try {
                        detailsOffreFrame = new DetailsOffreFrame(selectedOffres, parent, proxyAgence);
                    } catch (HotelNotFoundExceptionException ex) {
                        System.out.println("Hotel non trouvé");
                    }
                    assert detailsOffreFrame != null;
                    detailsOffreFrame.setVisible(true);
                }
            });

            // Ajoutez la liste au panneau de contenu
            JScrollPane scrollPane = new JScrollPane(offresList);
            getContentPane().add(scrollPane);

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(parent);
            setVisible(true);
        }
}
