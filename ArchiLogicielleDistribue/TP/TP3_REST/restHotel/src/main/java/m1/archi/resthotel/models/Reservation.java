package m1.archi.resthotel.models;

import m1.archi.exception.DateNonValideException;

import java.util.Date;
import java.util.ArrayList;
import java.util.Random;

public class Reservation {
    private String numero;
    private Hotel hotel;
    private ArrayList<Chambre> chambresReservees;
    private Client clientPrincipal;
    private Date dateArrivee;
    private Date dateDepart;
    private int nombrePersonnes;
    private double montantReservation;
    private boolean petitDejeuner;

    public Reservation() {
    }

    public Reservation(String numero, Hotel hotel, ArrayList<Chambre> chambresReservees, Client clientPrincipal,
                       Date dateArrivee, Date dateDepart, int nombrePersonnes, boolean petitDejeuner) throws DateNonValideException {
        if (dateArrivee.after(dateDepart)) {
            throw new DateNonValideException("La date d'arrivée doit être avant la date de départ");
        }
        this.numero = numero;
        this.hotel = hotel;
        this.chambresReservees = chambresReservees;
        this.clientPrincipal = clientPrincipal;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nombrePersonnes = nombrePersonnes;
        int montantReservation = 0;

        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        long daysDifference = (dateDepart.getTime() - dateArrivee.getTime()) / millisecondsPerDay;

        for (Chambre chambre : chambresReservees) {
            montantReservation += chambre.getPrix() * daysDifference;
        }
        if (petitDejeuner) {
            montantReservation += (hotel.getNombreEtoiles() * (new Random().nextInt(3) + 5)) * nombrePersonnes
                    * daysDifference;
        }
        this.montantReservation = montantReservation;
        this.petitDejeuner = petitDejeuner;
    }

    // #region Getters and Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public ArrayList<Chambre> getChambresReservees() {
        return chambresReservees;
    }

    public void setChambresReservees(ArrayList<Chambre> chambresReservees) {
        this.chambresReservees = chambresReservees;
    }

    public Date getdateArrivee() {
        return dateArrivee;
    }

    public void setdateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    public boolean isPetitDejeuner() {
        return petitDejeuner;
    }

    public void setPetitDejeuner(boolean petitDejeuner) {
        this.petitDejeuner = petitDejeuner;
    }

    public double getMontantReservation() {
        return montantReservation;
    }

    public void setMontantReservation(double montantReservation) {
        this.montantReservation = montantReservation;
    }

    public Client getclientPrincipal() {
        return clientPrincipal;
    }

    public void setclientPrincipal(Client clientPrincipal) {
        this.clientPrincipal = clientPrincipal;
    }
    // #endregion

    @Override
    public String toString() {
        String res = "Reservation " + numero + " : " + hotel.getNom() + " (" + hotel.getAdresse().getVille() + ")\n";
        res += "Chambres réservées : ";
        for (Chambre chambre : chambresReservees) {
            res += chambre.getNumero() + " ";
        }
        res += "\n";
        res += "Client principal : " + clientPrincipal.getNom() + " " + clientPrincipal.getPrenom() + "\n";
        res += "Du " + dateArrivee + " au " + dateDepart + "\n";
        res += "Nombre de personnes : " + nombrePersonnes + "\n";
        res += "Petit déjeuner : " + petitDejeuner + "\n";
        res += "Montant de la réservation : " + montantReservation + "€\n";
        return res;
    }

}
