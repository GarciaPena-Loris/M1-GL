import java.time.LocalDate;

public class Reservation {
    private String numero;
    private Hotel hotel;
    private Chambre chambreReservee;
    private Client clientPrincipal;
    private LocalDate dateArrive;
    private LocalDate dateDepart;
    private int nombrePersonnes;
    private double montantReservation;
    private boolean petitDejeuner;

    public Reservation(String numero, Hotel hotel, Chambre chambreReservee, Client clientPrincipal,
            LocalDate dateArrive, LocalDate dateDepart, int nombrePersonnes, boolean petitDejeuner) {
        if (dateArrive.isAfter(dateDepart)) {
            throw new IllegalArgumentException("La date d'arrivée doit être avant la date de départ");
        }
        if (!hotel.getChambres().contains(chambreReservee)) {
            throw new IllegalArgumentException("La chambre doit appartenir à l'hotel");
        }
        this.numero = numero;
        this.hotel = hotel;
        this.chambreReservee = chambreReservee;
        this.clientPrincipal = clientPrincipal;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.nombrePersonnes = nombrePersonnes;
        this.montantReservation = chambreReservee.getPrix() * (dateDepart.getDayOfYear() - dateArrive.getDayOfYear());
        if (petitDejeuner) {
            this.montantReservation += (hotel.getNombreEtoiles() * 10) * nombrePersonnes;
        }
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

    public Chambre getChambreReservee() {
        return chambreReservee;
    }

    public void setChambreReservee(Chambre chambreReservee) {
        this.chambreReservee = chambreReservee;
    }

    public LocalDate getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(LocalDate dateArrive) {
        this.dateArrive = dateArrive;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDate dateDepart) {
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
        return "Reservation [numero=" + numero + ", hotel=" + hotel + ", chambreReservee=" + chambreReservee
                + ", clientPrincipal=" + clientPrincipal + ", dateArrive=" + dateArrive + ", dateDepart=" + dateDepart
                + ", nombrePersonnes=" + nombrePersonnes + ", montantReservation=" + montantReservation
                + ", petitDejeuner=" + petitDejeuner + "]";
    }

}
