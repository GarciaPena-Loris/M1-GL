package m1.ingelo;

public abstract class CommandeImpl implements Commande {
    protected Bidon bidon;
    protected int volumeDeplace;

    public CommandeImpl(Bidon bidon, int volumeDeplace) {
        this.bidon = bidon;
        this.volumeDeplace = volumeDeplace;
    }

    public abstract void executer();

    public abstract void annuler();

    public abstract String afficherAction();

}
