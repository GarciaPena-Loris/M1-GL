package m1.ingelo;

public abstract class EAllume extends EtatClimatiseur {

    EAllume(Climatiseur c) {
        super(c);
    }

    public int onOff() {
        return (1);
    }

    public int home() {
        return (2);
    }

    @Override
    public String toString() {
        return "Etat allume";
    }
}
