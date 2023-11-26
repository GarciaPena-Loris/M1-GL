package m1.ingelo;

public class EEteint extends EtatClimatiseur {
    EEteint(Climatiseur c) {
        super(c);
    }

    public int onOff() {
        return (2);
    }

    public void incr() {
        throw new ClimatiseurException("Impossible d'incrémenter, le climatiseur est éteint");
    }

    public void decr() {
        throw new ClimatiseurException("Impossible de décrémenter, le climatiseur est éteint");
    }

    @Override
    public String toString() {
        return "Etat eteint";
    }
}
