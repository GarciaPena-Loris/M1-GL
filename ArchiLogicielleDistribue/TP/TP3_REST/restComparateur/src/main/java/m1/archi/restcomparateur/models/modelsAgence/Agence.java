package m1.archi.restcomparateur.models.modelsAgence;

import java.util.ArrayList;
import java.util.List;


public class Agence {
   
    private long idAgence;
    private String nom;
    
    private List<ReductionHotel> reductionHotels;

    private List<Utilisateur> listeUtilisateurs;

    public Agence() {
    }

    public Agence(String nom) {
        this.nom = nom;
        this.listeUtilisateurs = new ArrayList<>();
        this.reductionHotels = new ArrayList<>();
    }

    public long getIdAgence() {
        return idAgence;
    }

    public void setIdAgence(long idAgence) {
        this.idAgence = idAgence;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<ReductionHotel> getReductionHotels() {
        return reductionHotels;
    }

    public void setReductionHotels(List<ReductionHotel> reductionHotels) {
        this.reductionHotels = reductionHotels;
    }

    public void addReductionHotel(ReductionHotel reductionHotel) {
        reductionHotels.add(reductionHotel);
    }

    public List<Utilisateur> getListeUtilisateurs() {
        return listeUtilisateurs;
    }

    public void setListeUtilisateurs(List<Utilisateur> listeUtilisateurs) {
        this.listeUtilisateurs = listeUtilisateurs;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("L'agence '" + this.nom + "' (" + this.getIdAgence() + ") possède " + this.getReductionHotels().size() + " hotels partenaires :\n");

        int compteur = 1;
        for (ReductionHotel reductionHotel : this.getReductionHotels()) {
            res.append("\t").append(compteur).append("- L'hotel (").append(reductionHotel.getIdHotel()).append(") avec une reduction de ").append(reductionHotel.getReduction()).append("% sur les chambres.\n");
            compteur++;
        }
        return res.toString();
    }
}
