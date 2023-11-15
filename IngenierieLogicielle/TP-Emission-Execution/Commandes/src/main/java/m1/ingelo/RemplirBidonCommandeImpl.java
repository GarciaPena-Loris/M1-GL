package m1.ingelo;

public class RemplirBidonCommandeImpl extends CommandeImpl {

    public RemplirBidonCommandeImpl(Bidon bidon) {
        super(bidon, bidon.capacite - bidon.volume);
    }

    @Override
    public void executer() {
        bidon.remplir();
    }

    @Override
    public void annuler() {
        bidon.vider(volumeDeplace);
    }

    @Override
    public String afficherAction() {
        return "RemplirBidon-" + bidon.numero;
    }
}
