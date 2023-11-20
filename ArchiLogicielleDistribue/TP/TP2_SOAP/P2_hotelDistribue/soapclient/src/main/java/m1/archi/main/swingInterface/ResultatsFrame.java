package m1.archi.main.swingInterface;

import m1.archi.agence.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResultatsFrame extends JFrame {

    public ResultatsFrame(List<Offres> listeOffres, JFrame parent, AgenceServiceIdentification proxyAgence) throws HotelNotFoundExceptionException {
        setTitle("Résultats de la recherche");

        // Créez un modèle de liste pour afficher les offres
        DefaultListModel<String> offresListModel = new DefaultListModel<>();
        JList<String> offresList = new JList<>(offresListModel);

        // Ajoutez les offres au modèle de liste
        for (Offres offres : listeOffres) {
            List<Offre> listeOffre = offres.getOffres();

            if (!listeOffre.isEmpty()) {
                Hotel hotel = proxyAgence.getHotel(listeOffre.get(0).getIdHotel());
                String nomHotel = hotel.getNom();
                offresListModel.addElement("- Selection de " + listeOffre.size() + " offres pour l'hotel " + nomHotel + ", situé " + hotel.getAdresse().getRue() + ". ");
            }
            else {
                offresListModel.addElement("Aucune offre pour cet hotel.");
            }
        }

        // Ajoutez un gestionnaire de sélection à la liste
        offresList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Obtenez l'offre sélectionnée
                Offres selectedOffres = listeOffres.get(offresList.getSelectedIndex());
                selectedOffres.getOffres().sort(Comparator.comparing(Offre::getPrix));

                // Créez une nouvelle fenêtre pour afficher les détails de l'offre
                DetailsOffreFrame detailsOffreFrame = new DetailsOffreFrame(selectedOffres, this);
                detailsOffreFrame.setVisible(true);
            }
        });

        // Ajoutez la liste au panneau de contenu
        JScrollPane scrollPane = new JScrollPane(offresList);
        getContentPane().add(scrollPane);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(parent);

    }

}
