public class Chambre {
    private String numero;
    private Hotel hotel;
    private double prix;
    private int nombreLits;

    public Chambre(String numero, Hotel hotel, double prix, int nombreLits) {
        this.numero = numero;
        this.hotel = hotel;
        this.prix = prix;
        this.nombreLits = nombreLits;
    }

    public String getNumero() {
        return this.numero;
    }

    public Hotel getHotel() {
        return this.hotel;
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

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setNombreLits(int nombreLits) {
        this.nombreLits = nombreLits;
    }

    @Override
    public String toString() {
        return "Chambre [numero=" + numero + ", hotel=" + hotel + ", prix=" + prix + ", nombreLits=" + nombreLits + "]";
    }

}
