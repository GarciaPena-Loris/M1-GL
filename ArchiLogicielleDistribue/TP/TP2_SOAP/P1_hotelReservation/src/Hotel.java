import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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

    // Fonction récursive pour trouver des combinaisons de chambres
    private void chercherCombinaison(ArrayList<Chambre> chambresDisponibles, int personnesRestantes,
            ArrayList<Chambre> combinaisonActuelle,
            HashMap<Integer, ArrayList<Chambre>> listeCombinaisonsChambresDisponibles, double prixTotal) {
        if (personnesRestantes == 0) {
            if (combinaisonActuelle.size() > 0) {
                // Créez une copie de la combinaison actuelle
                ArrayList<Chambre> nouvelleCombinaison = new ArrayList<>(combinaisonActuelle);
                // Triez les chambres par numéro dans la nouvelle combinaison
                nouvelleCombinaison.sort(Comparator.comparing(Chambre::getNumero));
                // Ajoutez la nouvelle combinaison à la HashMap avec le prix total
                listeCombinaisonsChambresDisponibles.put((int) prixTotal, nouvelleCombinaison);
            }
        } else if (personnesRestantes > 0) {
            for (int i = 0; i < chambresDisponibles.size(); i++) {
                Chambre chambre = chambresDisponibles.get(i);
                if (chambre.getNombreLits() <= personnesRestantes) {
                    // Vérifiez que la chambre n'a pas déjà été utilisée
                    if (!combinaisonActuelle.contains(chambre)) {
                        combinaisonActuelle.add(chambre);
                        // Mettez à jour le prix total
                        double nouveauPrixTotal = prixTotal + chambre.getPrix();
                        chercherCombinaison(chambresDisponibles, personnesRestantes - chambre.getNombreLits(),
                                combinaisonActuelle, listeCombinaisonsChambresDisponibles, nouveauPrixTotal);
                        combinaisonActuelle.remove(combinaisonActuelle.size() - 1);
                    }
                }
            }
        }
    }

    // functions
    public HashMap<Integer, ArrayList<Chambre>> getChambreDisponibleCriteres(String ville, LocalDate dateArrivee,
            LocalDate dateDepart,
            int prixMin,
            int prixMax, int nombreEtoiles, int nombrePersonne) {

        HashMap<Integer, ArrayList<Chambre>> listeCombinaisonsChambresDisponibles = new HashMap<Integer, ArrayList<Chambre>>();

        if (this.adresse.getVille().equals(ville) && this.nombreEtoiles == nombreEtoiles) {

            ArrayList<Chambre> chambresDisponibles = new ArrayList<>();

            // On ajoute toute les chambre qui correspondent aux critères
            for (Chambre chambre : this.chambres) {
                if (chambre.getPrix() >= prixMin && chambre.getPrix() <= prixMax
                        && chambre.getNombreLits() <= nombrePersonne) {
                    chambresDisponibles.add(chambre);
                }
            }

            // On supprime les chambre déja reservées
            for (Reservation reservation : this.reservations) {
                if (reservation.getDateArrive().isBefore(dateDepart)
                        && reservation.getDateDepart().isAfter(dateArrivee)) {
                    for (Chambre chambre : reservation.getChambresReservees()) {
                        if (chambresDisponibles.contains(chambre)) {
                            chambresDisponibles.remove(chambre);
                        }
                    }
                }
            }

            if (chambresDisponibles.size() > 0) {
                // Vérifier si il y a une chambre avec le nombre de lits correspondant
                for (Chambre chambre : chambresDisponibles) {
                    if (chambre.getNombreLits() == nombrePersonne) {
                        ArrayList<Chambre> combinaisonChambre = new ArrayList<>();
                        combinaisonChambre.add(chambre);
                        listeCombinaisonsChambresDisponibles.put((int) chambre.getPrix(), combinaisonChambre);
                        return listeCombinaisonsChambresDisponibles;
                    }
                }

                // Vérifier s'il existe une combinaison de chambres qui correspond au nombre de
                // personnes
                chercherCombinaison(chambresDisponibles, nombrePersonne, new ArrayList<Chambre>(),
                        listeCombinaisonsChambresDisponibles, 0);

                return listeCombinaisonsChambresDisponibles;
            }
        }
        return listeCombinaisonsChambresDisponibles;
    }

    @Override
    public String toString() {
        String res = "L'hotel '" + this.nom + "' (" + this.nombreEtoiles
                + " étoiles) situé à " + this.adresse.getVille() + " possède " + this.chambres.size() + " chambres :\n";
        for (Chambre chambre : this.chambres) {
            res += "\t" + chambre.toString() + "\n";
        }
        return res;
    }

}
