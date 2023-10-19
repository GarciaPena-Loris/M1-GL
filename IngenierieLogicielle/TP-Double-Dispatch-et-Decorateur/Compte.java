public class Compte {
    private Client client;

    public Compte(Client client) {
        this.client = client;
    }

    public double prixLocation(Produit produit) {
        return produit.getPrix();
    }

    public Client getClient() {
        return client;
    }
    
}
