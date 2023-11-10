package m1.archi.model;

public class Chambre {
    private int numero;
    private double prix;
    private int nombreLits;

    private String idHotel;

    public Chambre() {
    }

    public Chambre(int numero, double prix, int nombreLits, String idHotel) {
        this.numero = numero;
        this.prix = prix;
        this.nombreLits = nombreLits;
        this.idHotel = idHotel;
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

    public String getIdHotel() {
        return this.idHotel;
    }

    public void setIdHotel(String idHotel) {
        this.idHotel = idHotel;
    }

    @Override
    public String toString() {
        String res = "Chambre " + this.numero + " (" + this.nombreLits + " lits), coute " + this.prix + " euros";
        return res;
    }

}
