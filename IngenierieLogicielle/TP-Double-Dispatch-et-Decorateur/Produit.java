public class Produit {
    private String nom;
    private double prix;

    public Produit(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public double prixLocation() {
        return getPrix();
    }

    @Override
    public String toString() {
        return nom + " (" + String.format("%.2f", prix) + ")";
    }
}
