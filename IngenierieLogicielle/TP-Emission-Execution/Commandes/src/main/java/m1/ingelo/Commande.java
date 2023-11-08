package m1.ingelo;

public abstract class Commande {
    protected Bidon bidon;
    protected int volumeDeplace;

    public Commande(Bidon bidon, int volumeDeplace) {
        this.bidon = bidon;
        this.volumeDeplace = volumeDeplace;
    }

    public abstract void executer();

    public abstract void annuler();

    public abstract String afficherAction();

}
