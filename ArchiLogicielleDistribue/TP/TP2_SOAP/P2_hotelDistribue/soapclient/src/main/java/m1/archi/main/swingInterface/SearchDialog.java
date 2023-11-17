package m1.archi.main.swingInterface;

import m1.archi.agence.*;
import m1.archi.main.User;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

public class SearchDialog extends JDialog {
    private final JTextField villeField;
    private final JTextField nbEtoilesField;
    private final JTextField nbPersonneField;
    private final JTextField prixMinField;
    private final JTextField prixMaxField;

    public SearchDialog(JFrame parent, String selectedAgence, User user, AgenceServiceIdentification proxyAgence) throws MalformedURLException {
        super(parent, "Rechercher une Offre", true);

        // Initialisation des composants
        villeField = new JTextField(10);
        nbEtoilesField = new JTextField(10);
        nbPersonneField = new JTextField(10);

        UtilDateModel dateArriveeModel = new UtilDateModel();
        UtilDateModel dateDepartModel = new UtilDateModel();

        JDatePickerImpl dateArriveePicker = new JDatePickerImpl(new JDatePanelImpl(dateArriveeModel, new Properties()), new DateLabelFormatter());
        JDatePickerImpl dateDepartPicker = new JDatePickerImpl(new JDatePanelImpl(dateDepartModel, new Properties()), new DateLabelFormatter());

        prixMinField = new JTextField(10);
        prixMaxField = new JTextField(10);

        URL url = new URL("http://localhost:8090/agencesservice/" + selectedAgence + "/consultation");
        AgenceServiceConsultationImplService agenceServiceConsultation = new AgenceServiceConsultationImplService(url);
        AgenceServiceConsultation proxyConsultation = agenceServiceConsultation.getAgenceServiceConsultationImplPort();

        JButton searchButton = new JButton("Rechercher");

        searchButton.addActionListener(e -> {
            // Récupérer les valeurs saisies
            String ville = villeField.getText();
            int nombreEtoiles = Integer.parseInt(nbEtoilesField.getText());
            int nombrePersonnes = Integer.parseInt(nbPersonneField.getText());
            int prixMin = Integer.parseInt(prixMinField.getText());
            int prixMax = Integer.parseInt(prixMaxField.getText());

            // Convertir les champs de date en XMLGregorianCalendar
            Date dateArrivee = dateArriveeModel.getValue();
            Date dateDepart = dateDepartModel.getValue();

            //Convertir les date en XMLGregorianCalendar
            GregorianCalendar greg = GregorianCalendar.from(ZonedDateTime.of(dateArrivee.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(), ZoneId.systemDefault()));
            XMLGregorianCalendar xmlDateArrivee = null;
            try {
                xmlDateArrivee = DatatypeFactory.newInstance().newXMLGregorianCalendar(greg);
            } catch (DatatypeConfigurationException ex) {
                ex.printStackTrace();
            }
            greg = GregorianCalendar.from(ZonedDateTime.of(dateDepart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay(), ZoneId.systemDefault()));
            XMLGregorianCalendar xmlDateDepart = null;
            try {
                xmlDateDepart = DatatypeFactory.newInstance().newXMLGregorianCalendar(greg);
            } catch (DatatypeConfigurationException ex) {
                ex.printStackTrace();
            }

            // Faire la recherche
            ArrayList<Offres> listeOffress;
            try {
                listeOffress = (ArrayList<Offres>) proxyConsultation.listeChoisOffresHotelCriteres(user.getLogin(), user.getPassword(), ville, xmlDateArrivee, xmlDateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonnes);

                // Afficher les résultats
                new ResultatsFrame(listeOffress, parent, proxyAgence);
            } catch (DateNonValideException_Exception ex) {
                System.out.println("⚠\uFE0F Vous n'avez pas renseigné de date valide.");
            } catch (UserNotFoundException_Exception ex) {
                System.out.println("⚠\uFE0F Vous n'avez pas de compte dans cette agence.");
            } catch (Exception ex) {
                System.out.println("⚠\uFE0F Probleme lors de la chercher de chambres :" + ex.getMessage());
            }

            // Fermer la fenêtre après la recherche
            dispose();
        });

        // Disposition des composants dans le panneau
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));  // Augmenter l'espacement entre les composants
        panel.add(new JLabel("Ville:"));
        panel.add(villeField);
        panel.add(new JLabel("Nombre d'étoiles:"));
        panel.add(nbEtoilesField);
        panel.add(new JLabel("Nombre de lits:"));
        panel.add(nbPersonneField);
        panel.add(new JLabel("Date d'arrivée:"));
        panel.add(dateArriveePicker);
        panel.add(new JLabel("Date de départ:"));
        panel.add(dateDepartPicker);
        panel.add(new JLabel("Prix minimum:"));
        panel.add(prixMinField);
        panel.add(new JLabel("Prix maximum:"));
        panel.add(prixMaxField);
        panel.add(new JLabel(""));
        panel.add(searchButton);

        // Ajouter le panneau à la fenêtre
        add(panel);

        // Définir la taille et la position de la fenêtre
        setSize(400, 400);
        setLocationRelativeTo(parent);
    }
}

