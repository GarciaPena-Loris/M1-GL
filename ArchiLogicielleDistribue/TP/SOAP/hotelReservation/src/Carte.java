public class Carte {
    private String nom;
    private String numero;
    private String dateExpiration;
    private String CCV;

    public Carte(String nom, String numero, String dateExpiration, String CCV) {
        this.nom = nom;
        this.numero = numero;
        this.dateExpiration = dateExpiration;
        this.CCV = CCV;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getCCV() {
        return CCV;
    }

    public void setCCV(String cCV) {
        CCV = cCV;
    }

}
