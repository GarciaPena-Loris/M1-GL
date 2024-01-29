package m1.ingelo;

abstract class EtatClimatiseur {
    abstract int onOff();
    abstract int home();
    abstract int timer();
    abstract int airFlow();
    abstract void incr() throws ClimatiseurException;
    abstract void decr() throws ClimatiseurException;
    Climatiseur clim;

    EtatClimatiseur(Climatiseur c) {
        clim = c;
    }
}
