import java.util.ArrayList;

public class Client {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Carte carte;

    private ArrayList<Reservation> historiqueReservations;
    private ArrayList<Reservation> reservationsEnCours;

    public Client(String nom, String prenom, String email, String telephone, Carte carte) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.carte = carte;

        this.historiqueReservations = new ArrayList<Reservation>();
        this.reservationsEnCours = new ArrayList<Reservation>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Carte getCarte() {
        return carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    // Historique des réservations

    public ArrayList<Reservation> getHistoriqueReservations() {
        return historiqueReservations;
    }

    public void setHistoriqueReservations(ArrayList<Reservation> historiqueReservations) {
        this.historiqueReservations = historiqueReservations;
    }

    private void addReservationToHistorique(Reservation reservation) {
        this.historiqueReservations.add(reservation);
    }

    // Réservations en cours

    public ArrayList<Reservation> getReservationsEnCours() {
        return reservationsEnCours;
    }

    public void setReservationsEnCours(ArrayList<Reservation> reservationsEnCours) {
        this.reservationsEnCours = reservationsEnCours;
    }

    private void addReservationToReservationsEnCours(Reservation reservation) {
        this.reservationsEnCours.add(reservation);
    }

    private void removeReservationFromReservationsEnCours(Reservation reservation) {
        this.reservationsEnCours.remove(reservation);
    }
}
