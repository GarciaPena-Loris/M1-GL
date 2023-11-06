package m1.archi.resthotel.models;

public class Chambre {
    private int numero;
    private double prix;
    private int nombreLits;

    public Chambre() {
    }
    
    public Chambre(int numero, double prix, int nombreLits) {
        this.numero = numero;
        this.prix = prix;
        this.nombreLits = nombreLits;
    }

    public int getNumero() {
        return this.numero;
    }

    public double getPrix() {
        return this.prix;
    }

    public int getNombreLits() {
        return this.nombreLits;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setNombreLits(int nombreLits) {
        this.nombreLits = nombreLits;
    }

    @Override
    public String toString() {
        String res = "Chambre " + this.numero + " (" + this.nombreLits + " lits), coute " + this.prix + " euros";
        return res;
    }

}
