package m1.archi.main.swingInterface;

import m1.archi.agence.*;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginDialog extends JDialog {
    private User userConnecte;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginDialog(Frame owner, String nomSelectedAgence) throws MalformedURLException {
        super(owner, "Connectez / Inscrivez vous :", true);

        URL url = new URL("http://localhost:8090/agencesservice/" + Interface.selectedAgence + "/connectionInscription");
        UserServiceConnectionInscriptionImplService userServiceConnectionInscription = new UserServiceConnectionInscriptionImplService(url);
        UserServiceConnectionInscription proxyUser = userServiceConnectionInscription.getUserServiceConnectionInscriptionImplPort();

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("  Nom d'utilisateur :");
        JLabel passwordLabel = new JLabel("  Mot de passe :");

        // Créer les champs de texte
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        Font font = new Font("Arial", Font.PLAIN, 18);
        usernameLabel.setFont(font);
        passwordLabel.setFont(font);
        usernameField.setFont(font);
        passwordField.setFont(font);

        JButton loginButton = getjButton(proxyUser, nomSelectedAgence);

        usernameField.getDocument().addDocumentListener(new InputDocumentListener(loginButton, usernameField, passwordField));
        passwordField.getDocument().addDocumentListener(new InputDocumentListener(loginButton, usernameField, passwordField));


        add(loginPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        pack();
        setSize(400, 180);
        setLocationRelativeTo(owner);
    }

    private JButton getjButton(UserServiceConnectionInscription proxyUser, String nomSelectedAgence) {
        JButton loginButton = new JButton("Valider");
        loginButton.setEnabled(false);
        loginButton.addActionListener(e -> {
            String login = usernameField.getText();
            String motdepasse = new String(passwordField.getPassword());

            try {
                userConnecte = proxyUser.connectionUser(login, motdepasse);
                JOptionPane.showMessageDialog(null, "Vous êtes connecté en tant que " + userConnecte.getLogin());
                dispose(); // Fermer la fenêtre de connexion après la connexion réussie
            } catch (UserNotFoundException_Exception ex) {
                int response = JOptionPane.showConfirmDialog(null, "Vous n'avez pas de compte dans cette agence, voulez-vous en créer un ?", "Identifiant ou Mot de passe incorrect", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    try {
                        userConnecte = proxyUser.inscriptionUser(login, motdepasse);
                    } catch (UserAlreadyExistsException_Exception exc) {
                        JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la création du compte...");
                    }

                    JOptionPane.showMessageDialog(null, "Vous êtes maintenant inscrit dans l'agence " + nomSelectedAgence+ " avec le compte " + userConnecte.getLogin() + " !");
                    dispose(); // Fermer la fenêtre de connexion après la création du compte
                } else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez pas de compte, vous ne pouvez pas intéragir avec cette agence !");
                }
            }

            dispose(); // Fermer la fenêtre de connexion après la connexion réussie
        });
        return loginButton;
    }

    public User getUserConnecte() {
        return userConnecte;
    }
}

