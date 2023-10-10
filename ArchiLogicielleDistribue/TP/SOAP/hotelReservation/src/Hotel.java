import java.util.ArrayList;

public class Hotel {
    private String nom;
    private Adresse adresse;
    private int nombreEtoiles;
    private ArrayList<Chambre> chambres;

    public Hotel(String nom, Adresse adresse, int nombreEtoiles) {
        this.nom = nom;
        this.adresse = adresse;
        this.nombreEtoiles = nombreEtoiles;
        this.chambres = new ArrayList<Chambre>();
    }

    public String getNom() {
        return this.nom;
    }

    public Adresse getAdresse() {
        return this.adresse;
    }

    public int getNombreEtoiles() {
        return this.nombreEtoiles;
    }

    public int getNombreChambres() {
        return this.chambres.size();
    }

    public Chambre getChambre(String numero) {
        for (Chambre chambre : this.chambres) {
            if (chambre.getNumero().equals(numero)) {
                return chambre;
            }
        }
        return null;
    }

    public ArrayList<Chambre> getChambres() {
        return this.chambres;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public void setNombreEtoiles(int nombreEtoiles) {
        this.nombreEtoiles = nombreEtoiles;
    }

    public void setChambres(ArrayList<Chambre> chambres) {
        this.chambres = chambres;
    }

    public void addChambre(Chambre chambre) {
        this.chambres.add(chambre);
    }

    public void removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
    }

}
