package m1.archi.resthotel.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Offre {
    @Id
    @GeneratedValue
    private long idOffre;
    private int nombreLitsTotal;
    private double prix;
    private Date dateArrivee;
    private Date dateDepart;
    @ManyToMany
    private List<Chambre> chambres;
    @ManyToOne
    @JsonIgnore
    private Hotel hotel;

    public Offre() {
    }

    public Offre(int nombreLitsTotal, double prix, Date dateArrivee, Date dateDepart, List<Chambre> chambres, Hotel hotel) {
        this.nombreLitsTotal = nombreLitsTotal;
        this.prix = prix;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.chambres = chambres;
        this.hotel = hotel;
    }

    public long getIdOffre() {
        return idOffre;
    }

    public void setIdOffre(long idOffre) {
        this.idOffre = idOffre;
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

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public List<Chambre> getChambres() {
        return chambres;
    }

    public void setChambres(List<Chambre> chambres) {
        this.chambres = chambres;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offre offre = (Offre) o;
        return getNombreLitsTotal() == offre.getNombreLitsTotal() && Double.compare(getPrix(), offre.getPrix()) == 0 && Objects.equals(getIdOffre(), offre.getIdOffre()) && Objects.equals(dateArrivee, offre.dateArrivee) && Objects.equals(dateDepart, offre.dateDepart) && Objects.equals(getChambres(), offre.getChambres()) && Objects.equals(getHotel(), offre.getHotel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOffre(), getNombreLitsTotal(), getPrix(), dateArrivee, dateDepart, getChambres(), getHotel());
    }

    @Override
    public String toString() {
        return "Offre{" +
                "idOffre=" + idOffre +
                ", nombreLitsTotal=" + nombreLitsTotal +
                ", prix=" + prix +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                ", chambres=" + chambres +
                ", hotel=" + hotel +
                '}';
    }
}
