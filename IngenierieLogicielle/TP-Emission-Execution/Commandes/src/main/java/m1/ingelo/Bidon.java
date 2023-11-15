package m1.ingelo;

public class Bidon {
    int numero;
    int capacite;
    int volume;

    public Bidon(int numero, int capacite) {
        this.numero = numero;
        this.capacite = capacite;
        this.volume = 0;
    }

    // Remplir le bidon à ras bord
    public void remplir() {
        if (volume == capacite)
            System.out.println("Bidon déjà plein");
        else
            volume = capacite;
    }

    // Remplir une certaine quantité d'eau dans le bidon
    public void remplir(int quantite) {
        if (volume + quantite > capacite)
            System.out.println("Trop d'eau dans le bidon");
        else
            volume += quantite;
    }

    // Vider complètement le bidon
    public void vider() {
        if (volume == 0)
            System.out.println("Bidon déjà vide");
        else
            volume = 0;
    }

    // Vider une certaine quantité d'eau du bidon
    public void vider(int quantite) {
        if (volume < quantite)
            System.out.println("Pas assez d'eau dans le bidon");
        else
            volume -= quantite;
    }

    // Transvaser le contenu de ce bidon dans un autre bidon
    public void transvaserVers(Bidon autreBidon) {
        if (volume == 0)
            System.out.println("Bidon vide");
        else {
            int espaceLibre = autreBidon.capacite - autreBidon.volume;
            int quantiteTransvasee = Math.min(volume, espaceLibre);
            volume -= quantiteTransvasee;
            autreBidon.volume += quantiteTransvasee;
        }
    }
}
