public class CompteAvecSeuil extends Compte {
    private double seuil;
    private int compteur;

    public CompteAvecSeuil(Client client, double seuil) {
        super(client);
        this.seuil = seuil;
        this.compteur = 0;
    }

    public double prixLocation(Produit produit) {
        double prix = super.prixLocation(produit);
        if (compteur == seuil) {
            compteur = 0;
            return 0;
        } else {
            compteur++;
            return prix;
        }
    }
}