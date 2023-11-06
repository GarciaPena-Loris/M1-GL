public class CompteBasique extends AbstractCompte {
    private Client client;

    public CompteBasique(Client client) {
        this.client = client;
    }

    public double prixLocation(Produit produit) {
        return produit.getPrix();
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "Le CompteBasique du client '" + client + "'";
    }

}
