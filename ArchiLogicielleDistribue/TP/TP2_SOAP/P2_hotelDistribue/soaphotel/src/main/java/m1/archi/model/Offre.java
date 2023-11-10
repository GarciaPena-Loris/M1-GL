package m1.archi.model;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;

public class Offre {
    private String identifiant;
    private int nombreLitsTotal;
    private double prix;
    private Date dateArrivee;
    private Date dateDepart;
    private ArrayList<Chambre> chambres;
    private String idHotel;

    public Offre() {
    }

    public Offre(String identifiant, int nombreLitsTotal, double prix, Date dateArrivee, Date dateDepart, ArrayList<Chambre> chambres, String idHotel) {
        this.identifiant = identifiant;
        this.nombreLitsTotal = nombreLitsTotal;
        this.prix = prix;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.chambres = chambres;
        this.idHotel = idHotel;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
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

    public void setPrix(double prix) {
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

    public String getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }
}
