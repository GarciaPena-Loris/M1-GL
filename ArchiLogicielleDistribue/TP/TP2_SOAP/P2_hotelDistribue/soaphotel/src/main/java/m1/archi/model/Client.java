package m1.archi.model;

import java.util.ArrayList;

public class Client {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private Carte carte;

    private ArrayList<String> historiqueReservations;

    public Client() {
    }
    
    public Client(String nom, String prenom, String email, String telephone, Carte carte) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.carte = carte;
        this.historiqueReservations = new ArrayList<>();
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

    public ArrayList<String> getHistoriqueReservations() {
        return historiqueReservations;
    }

    public void setHistoriqueReservations(ArrayList<String> historiqueReservations) {
        this.historiqueReservations = historiqueReservations;
    }

    public void addReservationToHistorique(String reservation) {
        this.historiqueReservations.add(reservation);
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(this.nom + " " + this.prenom + " (" + this.email + ")\n");
        res.append("Carte ").append(this.carte.getNumero()).append("\n");
        res.append("Réservations en cours : ");
        res.append("\nHistorique des réservations : ");
        if (this.historiqueReservations.size() == 0) {
            res.append("aucune");
        } else {
            for (String reservation : this.historiqueReservations) {
                res.append(reservation).append(" ");
            }
        }
        return res.toString();
    }
}
