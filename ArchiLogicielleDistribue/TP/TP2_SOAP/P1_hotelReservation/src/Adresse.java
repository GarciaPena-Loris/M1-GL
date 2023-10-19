public class Adresse {
    private String pays;
    private String ville;
    private String rue;
    private int numero;
    private String lieuDit;
    private String position;

    public Adresse(String pays, String ville, String rue, int numero, String lieuDit, String position) {
        this.pays = pays;
        this.ville = ville;
        this.rue = rue;
        this.numero = numero;
        this.lieuDit = lieuDit;
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

    public int getNumero() {
        return this.numero;
    }

    public String getLieu_dit() {
        return this.lieuDit;
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

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setLieu_dit(String lieuDit) {
        this.lieuDit = lieuDit;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        String res = this.numero + " " + this.rue + " " + this.lieuDit + "\n" + this.ville + " " + this.pays + "\n"
                + this.position;
        return res;
    }
}
