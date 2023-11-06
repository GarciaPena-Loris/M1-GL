package m1.archi.resthotel.models;

import m1.archi.exception.DateNonValideException;

import java.util.*;

public class Hotel {
    private String identifiant;
    private String nom;
    private Adresse adresse;
    private int nombreEtoiles;
    private ArrayList<Chambre> chambres;
    private ArrayList<Reservation> reservations;
    private ArrayList<Offre> offres;

    public Hotel() {
    }

    public Hotel(String identifiant, String nom, Adresse adresse, int nombreEtoiles) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.adresse = adresse;
        this.nombreEtoiles = nombreEtoiles;
        this.chambres = new ArrayList<Chambre>();
        this.reservations = new ArrayList<Reservation>();
        this.offres = new ArrayList<Offre>();
    }

    public String getIdentifiant() {
        return this.identifiant;
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

    public Chambre getChambre(int numero) {
        for (Chambre chambre : this.chambres) {
            if (chambre.getNumero() == numero) {
                return chambre;
            }
        }
        return null;
    }

    public ArrayList<Chambre> getChambres() {
        return this.chambres;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
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

    public ArrayList<Offre> getOffres() {
        return this.offres;
    }

    public void setOffres(ArrayList<Offre> offres) {
        this.offres = offres;
    }

    public void addOffre(Offre offre) {
        this.offres.add(offre);
    }

    public void removeOffre(Offre offre) {
        this.offres.remove(offre);
    }

    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    // Fonction récursive pour trouver des combinaisons de chambres
    private void chercherCombinaison(ArrayList<Chambre> chambresDisponibles, int personnesRestantes,
                                     ArrayList<Chambre> combinaisonActuelle,
                                     ArrayList<ArrayList<Chambre>> listeCombinaisonsChambres) {
        if (personnesRestantes == 0) {
            if (combinaisonActuelle.size() > 0) {
                // Créez une copie de la combinaison actuelle
                ArrayList<Chambre> nouvelleCombinaison = new ArrayList<>(combinaisonActuelle);
                // Triez les chambres par numéro dans la nouvelle combinaison
                nouvelleCombinaison.sort(Comparator.comparing(Chambre::getNumero));
                // Ajoutez la nouvelle combinaison à la HashMap avec le prix total
                listeCombinaisonsChambres.add(nouvelleCombinaison);
            }
        } else if (personnesRestantes > 0) {
            for (int i = 0; i < chambresDisponibles.size(); i++) {
                Chambre chambre = chambresDisponibles.get(i);
                if (chambre.getNombreLits() <= personnesRestantes) {
                    // Vérifiez que la chambre n'a pas déjà été utilisée
                    if (!combinaisonActuelle.contains(chambre)) {
                        combinaisonActuelle.add(chambre);
                        chercherCombinaison(chambresDisponibles, personnesRestantes - chambre.getNombreLits(),
                                combinaisonActuelle, listeCombinaisonsChambres);
                        combinaisonActuelle.remove(combinaisonActuelle.size() - 1);
                    }
                }
            }
        }
    }

    // functions
    public ArrayList<Offre> getChambreDisponibleCriteres(String ville, Date dateArrivee,
                                                         Date dateDepart,
                                                         int prixMin,
                                                         int prixMax, int nombreEtoiles, int nombrePersonne) throws DateNonValideException {

        if (dateArrivee.after(dateDepart)) {
            throw new DateNonValideException("La date d'arrivée doit être avant la date de départ");
        }

        if (this.adresse.getVille().equals(ville) && this.nombreEtoiles == nombreEtoiles) {

            ArrayList<Offre> offres = new ArrayList<>();
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
                if (reservation.getdateArrivee().before(dateDepart)
                        && reservation.getDateDepart().after(dateArrivee)) {
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
                        int indentifiant = (int) new Date().getTime();
                        Offre offre = new Offre(indentifiant, chambre.getNombreLits(), chambre.getPrix(), dateArrivee, dateDepart, new ArrayList<>(Arrays.asList(chambre)), this);
                        offres.add(offre);
                        this.addOffre(offre);
                        return offres;
                    }
                }

                // Vérifier s'il existe une combinaison de chambres qui correspond au nombre de
                // personnes
                ArrayList<ArrayList<Chambre>> listeCombinaisonsChambres = new ArrayList<>();

                chercherCombinaison(chambresDisponibles, nombrePersonne, new ArrayList<Chambre>(),
                        listeCombinaisonsChambres);

                for (ArrayList<Chambre> combinaisonChambresDisponibles : listeCombinaisonsChambres) {
                    int indentifiant = (int) new Date().getTime();
                    Offre offre = new Offre(indentifiant, nombrePersonne, combinaisonChambresDisponibles.stream().mapToDouble(Chambre::getPrix).sum(), dateArrivee, dateDepart, combinaisonChambresDisponibles, this);
                    offres.add(offre);
                    this.addOffre(offre);
                }

                return offres;
            }
        }
        return null;
    }

    public Reservation reserverChambres(Offre offre, String nomClient, String prenomClient, String email, String telephone, Carte carte, boolean petitDejeuner) throws DateNonValideException {
        if (offre.getdateArrivee().after(offre.getdateDepart())) {
            throw new DateNonValideException("La date d'arrivée doit être avant la date de départ");
        }
        Client clientPrincipal = new Client(nomClient, prenomClient, email, telephone, carte);
        String numero = "R" + new Date().getTime();
        Reservation reservation = new Reservation(numero, this, offre.getChambres(), clientPrincipal, offre.getdateArrivee(), offre.getdateDepart(), offre.getNombreLitsTotal(), petitDejeuner);
        this.addReservation(reservation);
        clientPrincipal.addReservationToHistorique(reservation);
        this.removeOffre(offre);
        return reservation;
    }

    public String getHotelInfo() {
        String res = "L'hotel '" + this.nom + "' (" + this.nombreEtoiles
                + " étoiles) situé au " + this.adresse + ", possède " + this.chambres.size() + " chambres :\n";

        for (Chambre chambre : this.chambres) {
            res += "\t- " + chambre.toString() + "\n";
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "L'hotel '" + this.nom + "' (" + this.nombreEtoiles
                + " étoiles) situé au " + this.adresse + ", possède " + this.chambres.size() + " chambres.";
        return res;
    }

}
