public class Chambre {
    private String numero;
    private double prix;
    private int nombreLits;

    public Chambre(String numero, double prix, int nombreLits) {
        this.numero = numero;
        this.prix = prix;
        this.nombreLits = nombreLits;
    }

    public String getNumero() {
        return this.numero;
    }

    public double getPrix() {
        return this.prix;
    }

    public int getNombreLits() {
        return this.nombreLits;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setNombreLits(int nombreLits) {
        this.nombreLits = nombreLits;
    }
    
}
