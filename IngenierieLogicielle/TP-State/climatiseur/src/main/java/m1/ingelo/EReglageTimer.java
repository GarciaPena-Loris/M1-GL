package m1.ingelo;

public class EReglageTimer extends EAllume {
    EReglageTimer(Climatiseur c) {
        super(c);
    }

    void incr() {
        clim.incrDuree();
    }

    void decr() {
        clim.decrDuree();
    }
    
    int timer(){
        clim.armerTimer(clim.getDuree());
        return (2);
    }

    int airFlow() {
        throw new ClimatiseurException("Impossible de r�gler le d�bit d'air, le climatiseur est en mode timer");
    }

    @Override
    public String toString() {
        return "Etat reglage timer";
    }

}
