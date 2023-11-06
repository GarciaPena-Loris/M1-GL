public class ForfaitSeuil extends Forfait {
    private int seuil;
    private int compteur;

    public ForfaitSeuil(AbstractCompte compte, int seuil) {
        super(compte);
        this.seuil = seuil;
        this.compteur = 0;
    }

    public double prixLocation(Produit produit) {
        if (compteur >= seuil) {
            compteur = 0;
            return 0;
        } else {
            compteur++;
            return super.prixLocation(produit);
        }
    }

    @Override
    public String toString() {
        return super.toString() + " avec un seuil de " + seuil + " locations";
    }
    
}
