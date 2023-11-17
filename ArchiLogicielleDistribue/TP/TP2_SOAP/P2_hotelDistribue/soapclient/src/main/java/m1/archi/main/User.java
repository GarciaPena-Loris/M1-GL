package m1.archi.main;

import m1.archi.agence.Reservation;

import java.util.ArrayList;
import java.util.List;

public class User {
    // Propriétés
    private String login;
    private String password;
    private List<Reservation> reservations;

    // Constructeur
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.reservations = new ArrayList<>();
    }

    // Méthode pour ajouter une réservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

}

