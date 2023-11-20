package m1.archi.model;

import m1.archi.hotel.Reservation;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String login;
    private String motDePasse;
    private List<Reservation> reservations;

    public User() {
    }

    public User(String login, String motDePasse) {
        this.login = login;
        this.motDePasse = motDePasse;
        this.reservations = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean addReservation(Reservation reservation) {
        return reservations.add(reservation);
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public boolean removeReservation(Reservation reservation) {
        return reservations.remove(reservation);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
