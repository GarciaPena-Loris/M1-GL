import java.util.ArrayList;
import java.util.Date;

public class Reservation {
    private String numero;
    private Hotel hotel;
    private ArrayList<Chambre> chambresReservees;
    private Client clientPrincipal;
    private String dateArrive;
    private String dateDepart;
    private int nombrePersonnes;
    private boolean petitDejeuner;

    public Reservation(String numero, Hotel hotel, ArrayList<Chambre> chambresReservees, Client clientPrincipal,
            String dateArrive, String dateDepart, int nombrePersonnes, boolean petitDejeuner) {
        this.numero = numero;
        this.hotel = hotel;
        this.chambresReservees = chambresReservees;
        this.clientPrincipal = clientPrincipal;
        this.dateArrive = dateArrive;
        this.dateDepart = dateDepart;
        this.nombrePersonnes = nombrePersonnes;
        this.petitDejeuner = petitDejeuner;
    }

    // #region Getters and Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(String dateArrive) {
        this.dateArrive = dateArrive;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
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

    public Client getclientPrincipal() {
        return clientPrincipal;
    }

    public void setclientPrincipal(Client clientPrincipal) {
        this.clientPrincipal = clientPrincipal;
    }
    // #endregion

    // Listes de chambres
    public ArrayList<Chambre> getChambres() {
        return this.chambresReservees;
    }

    public void setChambres(ArrayList<Chambre> chambresReservees) {
        this.chambresReservees = chambresReservees;
    }

    public void addChambre(Chambre chambre) {
        this.chambresReservees.add(chambre);
    }

    public void removeChambre(Chambre chambre) {
        this.chambresReservees.remove(chambre);
    }

    // Verifier si une chambre est disponible
    public ArrayList<Chambre> getChambresLibresFromHotel(Hotel hotel, Date dateArrive, Date dateDepart) {
        ArrayList<Chambre> chambresLibres = new ArrayList<Chambre>();
        for (Chambre chambre : hotel.getChambres()) {

        }
        return chambresLibres;
    }

}
