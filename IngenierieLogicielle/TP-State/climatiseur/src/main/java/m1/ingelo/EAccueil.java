package m1.ingelo;

public class EAccueil extends EAllume {

    EAccueil(Climatiseur c) {
        super(c);
    }

    public int timer() {
        return (3);
    }

    public int airFlow() {
        return (4);
    }

    public void incr() {
        clim.incrTemperature();
    }

    public void decr() {
        clim.decrTemperature();
    }

    @Override
    public String toString() {
        return "Etat accueil";
    }
}
