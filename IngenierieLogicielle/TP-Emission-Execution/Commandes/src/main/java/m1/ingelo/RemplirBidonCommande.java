package m1.ingelo;

public class RemplirBidonCommande extends Commande {

    public RemplirBidonCommande(Bidon bidon) {
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
