package m1.ingelo;

public class EReglageVitesse extends EAllume {
    EReglageVitesse(Climatiseur c) {
        super(c);
    }

    int timer() {
        return 0;
    }

    int airFlow() {
        return 0;
    }

    void incr() {
        clim.incrVitesse();

    }

    void decr() {
        clim.decrVitesse();
    }

    @Override
    public String toString() {
        return "Etat reglage vitesse";
    }
}
