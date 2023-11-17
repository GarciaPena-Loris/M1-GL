package m1.archi.resthotel.models;

import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Client {
    @Id
    @GeneratedValue
    private Long idClient;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    @OneToOne
    private Carte carte;
    @OneToMany
    private ArrayList<Reservation> historiqueReservations;

    public Client() {
    }
    
    public Client(String nom, String prenom, String email, String telephone, Carte carte) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.carte = carte;

        this.historiqueReservations = new ArrayList<Reservation>();
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

    public void addReservationToHistorique(Reservation reservation) {
        this.historiqueReservations.add(reservation);
    }


    @Override
    public String toString() {
        String res = this.nom + " " + this.prenom + " (" + this.email + ")\n";
        res += "Carte " + this.carte.getNumero() + "\n";
        res += "\nHistorique des réservations : ";
        if (this.historiqueReservations.size() == 0) {
            res += "aucune";
        } else {
            for (Reservation reservation : this.historiqueReservations) {
                res += reservation.getNumero() + " ";
            }
        }
        return res;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdClient() {
        return idClient;
    }
}
