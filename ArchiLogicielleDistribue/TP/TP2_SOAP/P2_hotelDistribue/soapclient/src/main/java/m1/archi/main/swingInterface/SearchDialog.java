package m1.archi.main.swingInterface;

import m1.archi.agence.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;

public class SearchDialog extends JFrame {
    private final JComboBox<String> villeField;
    private final JSpinner nbEtoilesField;
    private final JSpinner nbPersonneField;
    private final JSpinner prixMinField;
    private final JSpinner prixMaxField;
    private final JDatePickerImpl dateArriveePicker;
    private final JDatePickerImpl dateDepartPicker;
    private final JButton searchButton;

    public SearchDialog(JFrame parent, String selectedAgence, AgenceServiceIdentification proxyAgence, ArrayList<String> listeVilles) throws MalformedURLException {
        setTitle("Effcetuer une recherche");
        // Initialisation des composants
        String[] listeVillesArray = new String[listeVilles.size()];
        listeVillesArray = listeVilles.toArray(listeVillesArray);
        villeField = new JComboBox<>(listeVillesArray);
        nbEtoilesField = createSpinner(1, 1, 5);
        nbPersonneField = createSpinner(1, 1, 100);

        UtilDateModel dateArriveeModel = new UtilDateModel();
        UtilDateModel dateDepartModel = new UtilDateModel();

        dateArriveePicker = new JDatePickerImpl(new JDatePanelImpl(dateArriveeModel, new Properties()), new DateLabelFormatter());
        dateDepartPicker = new JDatePickerImpl(new JDatePanelImpl(dateDepartModel, new Properties()), new DateLabelFormatter());

        prixMinField = createSpinner(0, 0, 10000);
        prixMaxField = createSpinner(10, 0, 50000);

        URL url = new URL("http://localhost:8090/agencesservice/" + selectedAgence + "/consultation");
        AgenceServiceConsultationImplService agenceServiceConsultation = new AgenceServiceConsultationImplService(url);
        AgenceServiceConsultation proxyConsultation = agenceServiceConsultation.getAgenceServiceConsultationImplPort();

        searchButton = new JButton("Rechercher");
        searchButton.setEnabled(false);

        villeField.addActionListener(e -> updateButtonState());
        dateArriveePicker.addActionListener(e -> updateButtonState());
        dateDepartPicker.addActionListener(e -> updateButtonState());

        searchButton.addActionListener(e -> {
            if (Interface.userConnecte == null) {
                JOptionPane.showMessageDialog(parent, "Vous devez vous connecter pour effectuer une recherche.", "Non connecté", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Récupérer les valeurs saisies
            String ville = (String) villeField.getSelectedItem();
            int nombreEtoiles = (int) nbEtoilesField.getValue();
            int nombrePersonnes = (int) nbPersonneField.getValue();
            int prixMin = (int) prixMinField.getValue();
            int prixMax = (int) prixMaxField.getValue();

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
            List<Offres> listeOffress;
            try {
                listeOffress = proxyConsultation.listeChoisOffresHotelCriteres(Interface.userConnecte.getLogin(), Interface.userConnecte.getMotDePasse(), ville, xmlDateArrivee, xmlDateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonnes);
                // Vérifier si la liste d'offres n'est pas vide avant de créer ResultatsFrame
                if (listeOffress != null  && !listeOffress.isEmpty()) {
                    // Afficher les résultats
                    ResultatsFrame resultatsFrame = new ResultatsFrame(listeOffress, parent, proxyAgence);
                    resultatsFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(parent, "Aucune offre trouvée pour les critères spécifiés.", "Aucun résultat", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (DateNonValideException_Exception ex) {
                JOptionPane.showMessageDialog(parent, "Vous n'avez pas renseigné de date valide.", "Erreur de date", JOptionPane.ERROR_MESSAGE);
            } catch (UserNotFoundException_Exception ex) {
                JOptionPane.showMessageDialog(parent, "Vous n'avez pas de compte dans cette agence.", "Utilisateur non trouvé", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace(); // Imprimer la stack trace complète pour débogage
                JOptionPane.showMessageDialog(parent, "Problème lors de la recherche de chambres : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }

            // Fermer la fenêtre après la recherche
            dispose();
        });

        // Disposition des composants dans le panneau
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));  // Augmenter l'espacement entre les composants
        panel.add(new JLabel("  Ville:"));
        panel.add(villeField);
        panel.add(new JLabel("  Nombre d'étoiles:"));
        panel.add(nbEtoilesField);
        panel.add(new JLabel("  Nombre de lits:"));
        panel.add(nbPersonneField);
        panel.add(new JLabel("  Date d'arrivée:"));
        panel.add(dateArriveePicker);
        panel.add(new JLabel("  Date de départ:"));
        panel.add(dateDepartPicker);
        panel.add(new JLabel("  Prix minimum:"));
        panel.add(prixMinField);
        panel.add(new JLabel("  Prix maximum:"));
        panel.add(prixMaxField);
        panel.add(new JLabel(""));
        panel.add(searchButton);

        // Ajouter le panneau à la fenêtre
        add(panel);

        // Définir la taille et la position de la fenêtre
        setSize(400, 400);
        setLocationRelativeTo(this);
    }

    private void updateButtonState() {
        // Vérifiez que la longueur du texte est supérieure ou égale à 3 pour chaque champ
        // et que les dates sont valides et que la date d'arrivée est avant la date de départ
        boolean isDateArriveeValid = dateArriveePicker.getModel().getValue() != null;
        boolean isDateDepartValid = dateDepartPicker.getModel().getValue() != null;
        boolean isVilleValid = villeField.getSelectedItem() != null;

        // Vérifiez que la date d'arrivée est avant la date de départ
        boolean isDateOrderValid = true;
        if (isDateArriveeValid && isDateDepartValid) {
            Date dateArrivee = (Date) dateArriveePicker.getModel().getValue();
            Date dateDepart = (Date) dateDepartPicker.getModel().getValue();
            isDateOrderValid = dateArrivee.before(dateDepart);
        }

        // Vérifier les prix
        boolean isPrixValid = (int) prixMinField.getValue() <= (int) prixMaxField.getValue();

        // Activer le bouton uniquement si toutes les conditions sont remplies
        boolean isValid = isDateArriveeValid && isDateDepartValid && isVilleValid && isDateOrderValid;
        searchButton.setEnabled(isValid);
    }


    private JSpinner createSpinner(int init, int min, int max) {
        JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(init, min, max, 1)); // Valeur initiale, valeur minimale, valeur maximale, incrément
        spinner.setFont(new Font("Arial", Font.PLAIN, 18));

        // Ajoutez un ChangeListener pour détecter les changements de valeur
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Répondez aux changements de valeur ici si nécessaire
            }
        });

        return spinner;
    }
}

