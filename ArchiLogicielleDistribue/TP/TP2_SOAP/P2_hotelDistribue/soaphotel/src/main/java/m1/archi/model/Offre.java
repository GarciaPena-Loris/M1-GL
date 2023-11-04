package m1.archi.model;

import java.util.ArrayList;
import java.util.Date;

public class Offre {
    private int identifiant;
    private int nombreLitsTotal;
    private double prix;
    private Date dateDebut;
    private Date dateFin;
    private ArrayList<Chambre> chambres;
    private Hotel hotel;

    public Offre() {
    }

    public Offre(int identifiant, int nombreLitsTotal, double prix, Date dateDebut, Date dateFin, ArrayList<Chambre> chambres, Hotel hotel) {
        this.identifiant = identifiant;
        this.nombreLitsTotal = nombreLitsTotal;
        this.prix = prix;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
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
