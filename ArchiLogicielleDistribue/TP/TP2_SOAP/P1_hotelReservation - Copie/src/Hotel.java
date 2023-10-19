import java.util.ArrayList;
import java.time.LocalDate;

public class Hotel {
    private String nom;
    private Adresse adresse;
    private int nombreEtoiles;
    private ArrayList<Chambre> chambres;
    private ArrayList<Reservation> reservations;

    public Hotel(String nom, Adresse adresse, int nombreEtoiles) {
        this.nom = nom;
        this.adresse = adresse;
        this.nombreEtoiles = nombreEtoiles;
        this.chambres = new ArrayList<Chambre>();
        this.reservations = new ArrayList<Reservation>();
    }

    public String getNom() {
        return this.nom;
    }

    public Adresse getAdresse() {
        return this.adresse;
    }

    public int getNombreEtoiles() {
        return this.nombreEtoiles;
    }

    public int getNombreChambres() {
        return this.chambres.size();
    }

    public Chambre getChambre(String numero) {
        for (Chambre chambre : this.chambres) {
            if (chambre.getNumero().equals(numero)) {
                return chambre;
            }
        }
        return null;
    }

    public ArrayList<Chambre> getChambres() {
        return this.chambres;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public void setNombreEtoiles(int nombreEtoiles) {
        this.nombreEtoiles = nombreEtoiles;
    }

    public void setChambres(ArrayList<Chambre> chambres) {
        this.chambres = chambres;
    }

    public void addChambre(Chambre chambre) {
        this.chambres.add(chambre);
    }

    public void removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
    }

    public ArrayList<Reservation> getReservations() {
        return this.reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    // functions
    public ArrayList<Chambre> getChambreVide(String ville, LocalDate dateArrivee, LocalDate dateDepart, int prixMin,
            int prixMax,
            int nombreEtoiles, int nombrePersonne) {
        ArrayList<Chambre> chambresVides = new ArrayList<Chambre>();
        if (this.adresse.getVille().equals(ville) && this.nombreEtoiles == nombreEtoiles) {
            ArrayList<Chambre> chambreReserveesDate = new ArrayList<Chambre>();
            for (Reservation reservation : this.reservations) {
                if (reservation.getDateArrive().compareTo(dateArrivee) >= 0
                        && reservation.getDateDepart().compareTo(dateDepart) <= 0) {
                    chambreReserveesDate.add(reservation.getChambreReservee());
                }
            }
            for (Chambre chambre : this.chambres) {
                if (chambre.getNombreLits() >= nombrePersonne && chambre.getPrix() >= prixMin
                        && chambre.getPrix() <= prixMax) {
                    if (!chambreReserveesDate.contains(chambre)) {
                        chambresVides.add(chambre);
                    }
                }
            }
        }
        return chambresVides;
    }

    @Override
    public String toString() {
        return "Hotel [nom=" + nom + ", adresse=" + adresse + ", nombreEtoiles=" + nombreEtoiles + ", chambres="
                + chambres + ", reservations=" + reservations + "]";
    }

}
