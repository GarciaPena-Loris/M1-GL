public class CompteAvecReduction extends Compte {
    private double reduction;

    public CompteAvecReduction(Client client, double reduction) {
        super(client);
        this.reduction = reduction;
    }

    public double prixLocation(Produit produit) {
        return super.prixLocation(produit) * (1 - reduction);
    }
}
