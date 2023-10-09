public class Adresse {
    private String pays;
    private String ville;
    private String rue;
    private int numero;
    private String lieu_dit;
    private String position;
    
    public Adresse(String pays, String ville, String rue, int numero, String lieu_dit, String position) {
        this.pays = pays;
        this.ville = ville;
        this.rue = rue;
        this.numero = numero;
        this.lieu_dit = lieu_dit;
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
        return this.lieu_dit;
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

    public void setLieu_dit(String lieu_dit) {
        this.lieu_dit = lieu_dit;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String toString() {
        return "Adresse{" +
            " pays='" + getPays() + "'" +
            ", ville='" + getVille() + "'" +
            ", rue='" + getRue() + "'" +
            ", numero='" + getNumero() + "'" +
            ", lieu_dit='" + getLieu_dit() + "'" +
            ", position='" + getPosition() + "'" +
            "}";
    }
}
