public class ForfaitReduction extends Forfait {
    private double reduction;

    public ForfaitReduction(AbstractCompte compte, double reduction) {
        super(compte);
        this.reduction = reduction;
    }

    public double prixLocation(Produit produit) {
        return super.prixLocation(produit) * (1 - reduction);
    }

    @Override
    public String toString() {
        return super.toString() + " avec une reduction de " + String.format("%.2f", reduction * 100) + "%";
    }
    
}
