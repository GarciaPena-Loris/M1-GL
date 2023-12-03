package m1.archi.restclient.models.modelsHotel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Reservation {
    private long idReservation;
    private Hotel hotel;
    private List<Chambre> chambresReservees;
    private LocalDateTime dateArrivee;
    private LocalDateTime dateDepart;
    private int nombrePersonnes;
    private double montantReservation;
    private boolean petitDejeuner;

    public Reservation() {
    }

    public Reservation(long idReservation, Hotel hotel, List<Chambre> chambresReservees, LocalDateTime dateArrivee, LocalDateTime dateDepart, int nombrePersonnes, double montantReservation, boolean petitDejeuner) {
        this.idReservation = idReservation;
        this.hotel = hotel;
        this.chambresReservees = chambresReservees;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nombrePersonnes = nombrePersonnes;
        this.montantReservation = montantReservation;
        this.petitDejeuner = petitDejeuner;
    }

    // #region Getters and Setters
    public long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(long idReservation) {
        this.idReservation = idReservation;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Chambre> getChambresReservees() {
        return chambresReservees;
    }

    public void setChambresReservees(List<Chambre> chambresReservees) {
        this.chambresReservees = chambresReservees;
    }

    public LocalDateTime getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(LocalDateTime dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public LocalDateTime getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDateTime dateDepart) {
        this.dateDepart = dateDepart;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    public double getMontantReservation() {
        return montantReservation;
    }

    public void setMontantReservation(double montantReservation) {
        this.montantReservation = montantReservation;
    }

    public boolean isPetitDejeuner() {
        return petitDejeuner;
    }

    public void setPetitDejeuner(boolean petitDejeuner) {
        this.petitDejeuner = petitDejeuner;
    }

    // #endregion


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return getIdReservation() == that.getIdReservation() && getNombrePersonnes() == that.getNombrePersonnes() && Double.compare(getMontantReservation(), that.getMontantReservation()) == 0 && isPetitDejeuner() == that.isPetitDejeuner() && Objects.equals(getHotel(), that.getHotel()) && Objects.equals(getChambresReservees(), that.getChambresReservees()) && Objects.equals(getDateArrivee(), that.getDateArrivee()) && Objects.equals(getDateDepart(), that.getDateDepart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdReservation(), getHotel(), getChambresReservees(), getDateArrivee(), getDateDepart(), getNombrePersonnes(), getMontantReservation(), isPetitDejeuner());
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idReservation=" + idReservation +
                ", hotel=" + hotel +
                ", chambresReservees=" + chambresReservees +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                ", nombrePersonnes=" + nombrePersonnes +
                ", montantReservation=" + montantReservation +
                ", petitDejeuner=" + petitDejeuner +
                '}';
    }
}
