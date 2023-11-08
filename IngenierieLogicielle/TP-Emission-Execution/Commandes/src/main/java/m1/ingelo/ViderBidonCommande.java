package m1.ingelo;

public class ViderBidonCommande extends Commande {
    public ViderBidonCommande(Bidon bidon) {
        super(bidon, bidon.volume);
    }

    @Override
    public void executer() {
        bidon.vider();
    }

    @Override
    public void annuler() {
        bidon.remplir(volumeDeplace);
    }

    @Override
    public String afficherAction() {
        return "ViderBidon-" + bidon.numero;
    }
}
