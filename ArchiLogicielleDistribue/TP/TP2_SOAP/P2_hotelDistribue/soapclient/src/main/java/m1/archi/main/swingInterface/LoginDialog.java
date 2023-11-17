package m1.archi.main.swingInterface;

import m1.archi.main.GestionnaireUser;
import m1.archi.main.User;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends JDialog {
    private User userConnecte;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginDialog(Frame owner, GestionnaireUser gestionnaireUser) {
        super(owner, "Connectez / Inscrivez vous :", true);

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

        JButton loginButton = getjButton(gestionnaireUser);

        usernameField.getDocument().addDocumentListener(new InputDocumentListener(loginButton, usernameField, passwordField));
        passwordField.getDocument().addDocumentListener(new InputDocumentListener(loginButton, usernameField, passwordField));


        add(loginPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        pack();
        setSize(400, 180);
        setLocationRelativeTo(owner);
    }

    private JButton getjButton(GestionnaireUser gestionnaireUser) {
        JButton loginButton = new JButton("Se connecter");
        loginButton.setEnabled(false);
        loginButton.addActionListener(e -> {
            String login = usernameField.getText();
            String motdepasse = new String(passwordField.getPassword());

            if (gestionnaireUser.userExists(login, motdepasse)) {
                if (gestionnaireUser.getUser(login, motdepasse) != null) {
                    userConnecte = gestionnaireUser.getUser(login, motdepasse);
                    JOptionPane.showMessageDialog(null, "Vous êtes connecté en tant que " + userConnecte.getLogin());
                    dispose(); // Fermer la fenêtre de connexion après la connexion réussie
                } else {
                    JOptionPane.showMessageDialog(null, "Mot de passe incorrect !");
                }
            } else {
                int response = JOptionPane.showConfirmDialog(null, "Vous n'avez pas de compte, voulez-vous en créer un ?", "Création de compte", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    userConnecte = new User(login, motdepasse);
                    gestionnaireUser.addUser(userConnecte);
                    JOptionPane.showMessageDialog(null, "Votre compte a bien été créé !");
                    dispose(); // Fermer la fenêtre de connexion après la création du compte
                } else {
                    JOptionPane.showMessageDialog(null, "Vous n'avez pas de compte, vous ne pouvez pas utiliser l'application");
                }
            }
        });
        return loginButton;
    }

    public User getUserConnecte() {
        return userConnecte;
    }
}

