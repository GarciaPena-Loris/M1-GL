public abstract class Forfait extends AbstractCompte {
    private AbstractCompte compte;

    public Forfait(AbstractCompte compte) {
        this.compte = compte;
    }

    public double prixLocation(Produit produit) {
        return compte.prixLocation(produit);
    }

    @Override
    public String toString() {
        return compte.toString();
    }

}
