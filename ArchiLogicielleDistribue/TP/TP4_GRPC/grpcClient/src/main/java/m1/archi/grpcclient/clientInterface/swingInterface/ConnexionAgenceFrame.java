package m1.archi.grpcclient.clientInterface.swingInterface;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import m1.archi.grpcclient.models.agenceModels.Agence;
import m1.archi.grpcclient.models.agenceModels.Utilisateur;
import m1.archi.grpcclient.models.hotelModels.Offre;
import m1.archi.proto.models.UtilisateurOuterClass;
import m1.archi.proto.services.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ConnexionAgenceFrame extends JFrame {
    private final Agence agence;
    private final Offre offre;
    private final CustomContentPane customContentPane;
    private final ComparateurServiceGrpc.ComparateurServiceBlockingStub proxyComparateur;
    private final String font = "Palatino Linotype";
    private final int fontSize = 20; // Définissez la taille de la police ici

    public ConnexionAgenceFrame(CustomContentPane customContentPane, Agence agence, Offre offre, ComparateurServiceGrpc.ComparateurServiceBlockingStub proxyComparateur) {
        super("Connexion à l'agence " + agence.getNom() + " :");
        this.customContentPane = customContentPane;
        this.agence = agence;
        this.offre = offre;
        this.proxyComparateur = proxyComparateur;

        Object[] options = {"<html><font size=\"4\">✅ : Se connecter</font></html>", "<html><font size=\"4\">❌ : Créer un compte</font></html>"};
        int choice = JOptionPane.showOptionDialog(this,
                "<html><font size=\"4\">Possédé vous un compte dans cette agence ?</font></html>",
                "Connexion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == JOptionPane.YES_OPTION) {
            showLoginDialog("");
        } else if (choice == JOptionPane.NO_OPTION) {
            showRegisterDialog();
        }
    }

    private void showLoginDialog(String email) {
        JTextField emailField = new JTextField(20);
        emailField.setText(email);
        emailField.setFont(new Font(font, Font.PLAIN, fontSize)); // Utilisez votre police et taille de police

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font(font, Font.PLAIN, fontSize)); // Utilisez votre police et taille de police

        Object[] message = {
                "Email:", emailField,
                "Mot de passe:", passwordField
        };

        Object[] options = {"<html><font size=\"4\">Connection</font></html>", "<html><font size=\"4\">Annuler</font></html>"};
        int option = JOptionPane.showOptionDialog(this, message, "Connection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (option == JOptionPane.OK_OPTION) {
            email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez renseigner tous les champs", "Problème...", JOptionPane.ERROR_MESSAGE);
                showLoginDialog(email);
                dispose();
            } else {
                try {
                    ManagedChannel channel = ManagedChannelBuilder
                            .forAddress("localhost", 9090)
                            .usePlaintext()
                            .build();

                    AgenceServiceGrpc.AgenceServiceBlockingStub proxyAgence = AgenceServiceGrpc.newBlockingStub(channel);

                    GetUtilisateurByEmailMotDePasseRequest request = GetUtilisateurByEmailMotDePasseRequest.newBuilder()
                            .setIdAgence(agence.getIdAgence())
                            .setEmail(email)
                            .setMotDePasse(password)
                            .build();

                    Utilisateur utilisateur = new Utilisateur(proxyAgence.getUtilisateurByEmailMotDePasse(request));
                    // Vérifier si l'utilisateur est bien dans l'agence
                    try {
                        GetUtilisateurByIdRequest requestUser = GetUtilisateurByIdRequest.newBuilder()
                                .setIdAgence(agence.getIdAgence())
                                .setIdUtilisateur(utilisateur.getIdUtilisateur())
                                .build();

                        utilisateur = new Utilisateur(proxyAgence.getUtilisateurById(requestUser));
                        new ReserverOffreFrame(customContentPane, agence, offre, utilisateur, proxyComparateur);
                        dispose();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Vous n'etes pas inscrit dans cette agence", "Connection impossible !", JOptionPane.ERROR_MESSAGE);
                        showLoginDialog(email);
                        dispose();
                    } finally {
                        if (channel != null && !channel.isShutdown()) {
                            channel.shutdown();
                            try {
                                channel.awaitTermination(5, TimeUnit.SECONDS);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect", "Connection impossible !", JOptionPane.ERROR_MESSAGE);
                    showLoginDialog(email);
                    dispose();
                }
            }
        } else {
            new ConnexionAgenceFrame(customContentPane, agence, offre, proxyComparateur);
            dispose();
        }
    }

    private void showRegisterDialog() {
        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font(font, Font.PLAIN, fontSize));

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font(font, Font.PLAIN, fontSize));

        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font(font, Font.PLAIN, fontSize));

        JTextField surnameField = new JTextField(20);
        surnameField.setFont(new Font(font, Font.PLAIN, fontSize));

        Object[] message = {
                "Email:", emailField,
                "Mot de passe:", passwordField,
                "Nom:", nameField,
                "Prénom:", surnameField
        };

        Object[] options = {"<html><font size=\"4\">Inscription</font></html>", "<html><font size=\"4\">Annuler</font></html>"};
        int option = JOptionPane.showOptionDialog(this, message, "Inscription", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String name = nameField.getText();
            String surname = surnameField.getText();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez renseigner tous les champs", "Problème...", JOptionPane.ERROR_MESSAGE);
                showRegisterDialog();
                dispose();
            } else {
                ManagedChannel channel = ManagedChannelBuilder
                        .forAddress("localhost", 9090)
                        .usePlaintext()
                        .build();

                AgenceServiceGrpc.AgenceServiceBlockingStub proxyAgence = AgenceServiceGrpc.newBlockingStub(channel);

                try {
                    // Création de l'utilisateur
                    UtilisateurOuterClass.Utilisateur utilisateurToSend = UtilisateurOuterClass.Utilisateur.newBuilder()
                            .setEmail(email)
                            .setMotDePasse(password)
                            .setNom(name)
                            .setPrenom(surname)
                            .addAllIdReservations(new ArrayList<>())
                            .build();

                    CreateUtilisateurRequest request = CreateUtilisateurRequest.newBuilder()
                            .setIdAgence(agence.getIdAgence())
                            .setUtilisateur(utilisateurToSend)
                            .build();

                    Utilisateur utilisateur = new Utilisateur(proxyAgence.createUtilisateur(request));

                    try {
                        AddAgenceUtilisateurRequest addUserrequest = AddAgenceUtilisateurRequest.newBuilder()
                                .setIdAgence(agence.getIdAgence())
                                .setUtilisateur(utilisateur.toProto())
                                .build();

                        utilisateur = new Utilisateur(proxyAgence.addAgenceUtilisateur(addUserrequest));

                        JOptionPane.showMessageDialog(this, "<html><font size=\"5\">✅ Inscription réussie ! Vous pouvez maintenant vous connecter.</font></html>", "Inscription réussie", JOptionPane.INFORMATION_MESSAGE);
                        showLoginDialog(email);
                        dispose();

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Vous possédez déjà un compte dans cette agence ! Connectez vous.", "Inscription impossible !", JOptionPane.ERROR_MESSAGE);
                        showLoginDialog(email);
                        dispose();
                    } finally {
                        if (channel != null && !channel.isShutdown()) {
                            channel.shutdown();
                            try {
                                channel.awaitTermination(5, TimeUnit.SECONDS);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Erreur lors de votre inscription", "Erreur" + e.getMessage(), JOptionPane.ERROR_MESSAGE);
                    showRegisterDialog();
                    dispose();
                }
            }
        } else {
            new ConnexionAgenceFrame(customContentPane, agence, offre, proxyComparateur);
            dispose();
        }
    }
}