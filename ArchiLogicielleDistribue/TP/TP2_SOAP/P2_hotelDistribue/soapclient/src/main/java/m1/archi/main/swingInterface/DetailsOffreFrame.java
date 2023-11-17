package m1.archi.main.swingInterface;

import m1.archi.agence.AgenceServiceIdentification;
import m1.archi.agence.HotelNotFoundExceptionException;
import m1.archi.agence.Offre;
import m1.archi.agence.Offres;

import javax.swing.*;

public class DetailsOffreFrame extends JFrame {
    public DetailsOffreFrame(Offres offres, JFrame parent, AgenceServiceIdentification proxyAgence) throws HotelNotFoundExceptionException {
        // ... (initialisations)

        // Créez un modèle de liste pour afficher les détails de l'offre
        DefaultListModel<String> detailsListModel = new DefaultListModel<>();
        JList<String> detailsList = new JList<>(detailsListModel);

        // Ajoutez les détails de l'offre au modèle de liste
        for (Offre offre : offres.getOffres()) {
            detailsListModel.addElement("Offre n°" + offre.getIdentifiant() + " pour " + offre.getNombreLitsTotal() + " personnes, proposée au prix de " + offre.getPrix() + "€");
            // ... (ajoutez d'autres détails spécifiques à l'offre)
        }

        // ... (ajoutez d'autres composants à votre frame comme vous le souhaitez)
    }
}

