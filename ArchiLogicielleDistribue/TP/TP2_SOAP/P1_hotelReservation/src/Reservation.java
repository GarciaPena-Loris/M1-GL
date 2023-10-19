import java.time.LocalDate;
import java.util.ArrayList;

public class Reservation {
    private String numero;
    private Hotel hotel;
    private ArrayList<Chambre> chambresReservees;
    private Client clientPrincipal;
    private LocalDate dateArrive;
    private LocalDate dateDepart;
    private int nombrePersonnes;
    private double montantReservation;
    private boolean petitDejeuner;

    public Reservation(String numero, Hotel hotel, ArrayList<Chambre> chambresReservees, Client clientPrincipal,
            LocalDate dateArrive, LocalDate dateDepart, int nombrePersonnes, boolean petitDejeuner) {
        if (dateArrive.isAfter(dateDepart)) {
            throw new IllegalArgumentException("La date d'arrivée doit être avant la date de départ");
        }
        this.numero = numero;
        this.hotel = hotel;
        this.chambresReservees = chambresReservees;
        this.clientPrincipal = clientPrincipal;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.nombrePersonnes = nombrePersonnes;
        int montantReservation = 0;
        for (Chambre chambre : chambresReservees) {
            montantReservation += chambre.getPrix() * (dateDepart.getDayOfYear() - dateArrive.getDayOfYear());
        }
        if (petitDejeuner) {
            montantReservation += (hotel.getNombreEtoiles() * 10) * nombrePersonnes
                    * (dateDepart.getDayOfYear() - dateArrive.getDayOfYear());
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
        String res = "Reservation " + numero + " : " + hotel.getNom() + " (" + hotel.getAdresse().getVille() + ")\n";
        res += "Chambres réservées : ";
        for (Chambre chambre : chambresReservees) {
            res += chambre.getNumero() + " ";
        }
        res += "\n";
        res += "Client principal : " + clientPrincipal.getNom() + " " + clientPrincipal.getPrenom() + "\n";
        res += "Du " + dateArrive + " au " + dateDepart + "\n";
        res += "Nombre de personnes : " + nombrePersonnes + "\n";
        res += "Petit déjeuner : " + petitDejeuner + "\n";
        res += "Montant de la réservation : " + montantReservation + "€\n";
        return res;
    }

}
