package m1.archi.model;

import java.util.ArrayList;
import java.util.Date;

public class Offre {
    private int identifiant;
    private int nombreLitsTotal;
    private double prix;
    private Date dateArrivee;
    private Date dateDepart;
    private ArrayList<Chambre> chambres;
    private Hotel hotel;

    public Offre() {
    }

    public Offre(int identifiant, int nombreLitsTotal, double prix, Date dateArrivee, Date dateDepart, ArrayList<Chambre> chambres, Hotel hotel) {
        this.identifiant = identifiant;
        this.nombreLitsTotal = nombreLitsTotal;
        this.prix = prix;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.chambres = chambres;
        this.hotel = hotel;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public int getNombreLitsTotal() {
        return nombreLitsTotal;
    }

    public void setNombreLitsTotal(int nombreLitsTotal) {
        this.nombreLitsTotal = nombreLitsTotal;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Date getdateArrivee() {
        return dateArrivee;
    }

    public void setdateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Date getdateDepart() {
        return dateDepart;
    }

    public void setdateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public ArrayList<Chambre> getChambres() {
        return chambres;
    }

    public void setChambres(ArrayList<Chambre> chambres) {
        this.chambres = chambres;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
