package m1.ingelo;

public class EEteint extends EtatClimatiseur {
    EEteint(Climatiseur c) {
        super(c);
    }

    public int onOff() {
        return (2);
    }

    public void incr() {
        throw new ClimatiseurException("Impossible d'incr�menter, le climatiseur est �teint");
    }

    public void decr() {
        throw new ClimatiseurException("Impossible de d�cr�menter, le climatiseur est �teint");
    }

    @Override
    public String toString() {
        return "Etat eteint";
    }
}
