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

    int timer() {
        throw new ClimatiseurException("Impossible d'aller en mode timer, le climatiseur est �teint");
    }

    int airFlow() {
        throw new ClimatiseurException("Impossible de r�gler le d�bit d'air, le climatiseur est �teint");
    }

    int home() {
        throw new ClimatiseurException("Impossible d'aller en mode home, le climatiseur est �teint");
    }

    @Override
    public String toString() {
        return "Etat eteint";
    }
}
