package m1.ingelo;

public class Undo implements Commande {
    protected Commande commande;

    public Undo(Commande commande) {
        this.commande = commande;
    }

    public void executer() {
        commande.annuler();
    }

    public void annuler() {
        commande.executer();
    }

    public String afficherAction() {
        return "Undo-" + commande.afficherAction();
    }
}
