package m1.archi.resthotel.models;

public class Adresse {
    private String pays;
    private String ville;
    private String rue;
    private String numero;
    private String position;

    public Adresse() {
    }

    public Adresse(String pays, String ville, String rue, String numero, String position) {
        this.pays = pays;
        this.ville = ville;
        this.rue = rue;
        this.numero = numero;
        this.position = position;
    }

    public String getPays() {
        return this.pays;
    }

    public String getVille() {
        return this.ville;
    }

    public String getRue() {
        return this.rue;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        String res = this.numero + " " + this.rue + " à " + this.ville + " (" + this.pays + ")";
        return res;
    }
}
