package m1.archi.main;

import java.util.ArrayList;

public class GestionnaireUser {
    // Attributs
    ArrayList<User> users;

    // Constructeur
    public GestionnaireUser() {
        this.users = new ArrayList<>();
    }

    // Méthode pour ajouter un utilisateur
    public void addUser(User user) {
        users.add(user);
    }

    // Méthode pour supprimer un utilisateur
    public void deleteUser(User user) {
        users.remove(user);
    }

    // Méthode pour vérifier si un utilisateur existe
    public boolean userExists(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour récupérer un utilisateur
    public User getUser(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean userExists(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour récupérer un utilisateur
    public User getUser(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    // Méthode pour récupérer la liste des utilisateurs
    public ArrayList<User> getUsers() {
        return users;
    }


}
