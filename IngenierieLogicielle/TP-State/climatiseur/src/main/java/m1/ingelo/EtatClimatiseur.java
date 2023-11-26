package m1.ingelo;

abstract class EtatClimatiseur {
    int onOff() {
        System.out.println("Etat : Allumage");
        return 1;
    }
    int home() {
        System.out.println("Etat : Retour a l'etat initial");
        return 2;
    }
    int timer() {
        System.out.println("Etat : Reglage du timer");
        return 3;
    }
    int airFlow() {
        System.out.println("Etat : Reglage de la vitesse de l'air");
        return 4;
    }
    abstract void incr() throws ClimatiseurException;
    abstract void decr() throws ClimatiseurException;
    Climatiseur clim;

    EtatClimatiseur(Climatiseur c) {
        clim = c;
    }
}
