package m1.ingelo;

public class TransvaserBidonCommande extends Commande {

    private Bidon autreBidon;

    public TransvaserBidonCommande(Bidon bidon, Bidon autreBidon) {
        super(bidon, Math.min(bidon.volume, autreBidon.capacite - autreBidon.volume));
        this.autreBidon = autreBidon;
    }

    @Override
    public void executer() {
        bidon.transvaserVers(autreBidon);
    }

    @Override
    public void annuler() {
        autreBidon.transvaserVers(bidon);
    }

    @Override
    public String afficherAction() {
        return "TransvaserBidon-" + bidon.numero + "-" + autreBidon.numero;
    }

}
