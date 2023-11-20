package m1.archi.main.swingInterface;

import m1.archi.agence.Chambre;
import m1.archi.agence.Offre;
import m1.archi.agence.Offres;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.net.MalformedURLException;

public class DetailsOffreFrame extends JFrame {
    protected JFrame actualFrame;

    public DetailsOffreFrame(Offres offres, JFrame parent) {
        super("Détails de la selection");

        actualFrame = this;
        // Créez un modèle de liste pour afficher les détails de l'offre
        DefaultListModel<String> detailsListModel = new DefaultListModel<>();
        JList<String> detailsList = new JList<>(detailsListModel);

        int compteur = 1;

        // Ajoutez les détails de chaque offre au modèle de liste
        for (Offre offre : offres.getOffres()) {
            StringBuilder offreDetails = new StringBuilder();
            offreDetails.append("Offre n°").append(compteur).append(" ")
                    .append("avec ").append(offre.getChambres().size()).append(" chambres, ")
                    .append("proposée à ").append(offre.getPrix()).append("€ : ");

            // Ajoutez les détails de chaque chambre
            for (Chambre chambre : offre.getChambres()) {
                offreDetails.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Chambre n°").append(chambre.getNumero()).append(" pour ").append(chambre.getNombreLits()).append(" personnes à ").append(chambre.getPrix()).append("€.</p>");
            }

            // Ajoutez l'offre complète à la liste
            detailsListModel.addElement("<html>" + offreDetails + "</html>");
            detailsListModel.addElement("----------------------------------------");

            compteur++;
        }

        // Créez un JScrollPane pour afficher la liste des détails
        JScrollPane scrollPane = new JScrollPane(detailsList);

        // Ajoutez le JScrollPane à la frame
        getContentPane().add(scrollPane);

        // Définissez la taille et la visibilité de la frame
        setSize(700, 650);
        setLocationRelativeTo(parent);
        setVisible(true);
        setAlwaysOnTop(true);

        detailsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = detailsList.getSelectedIndex();
                if (selectedIndex % 2 == 0) { // Vérifiez que la sélection est une ligne d'offre
                    int offerIndex = selectedIndex / 2;
                    int response = JOptionPane.showConfirmDialog(actualFrame,
                            "Voulez-vous réserver cette offre ?", "Confirmation de réservation",
                            JOptionPane.YES_NO_OPTION);

                    if (response == JOptionPane.YES_OPTION) {
                        ReserverOffreFrame reserverOffreFrame = null;
                        try {
                            reserverOffreFrame = new ReserverOffreFrame(offres.getOffres().get(offerIndex), parent);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                        reserverOffreFrame.setVisible(true);

                        // Fermer la fenêtre après la réservation
                        dispose();
                    }
                }

                detailsList.clearSelection();
            }
        });

    }
}

