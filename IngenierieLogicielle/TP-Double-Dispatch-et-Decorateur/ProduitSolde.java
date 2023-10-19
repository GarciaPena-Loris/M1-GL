public class ProduitSolde extends Produit {
    private double rabais;

    public ProduitSolde(String nom, double prix, double rabais) {
        super(nom, prix);
        this.rabais = rabais;
    }

    public double getPrix() {
        return super.getPrix() * (1 - rabais);
    }

    public double prixLocation(Compte compte) {
        return compte.prixLocation(this);
    }

    @Override
    public String toString() {
        return super.toString() + " (solde " + String.format("%.2f", rabais * 100) + "%)";
    }
}
