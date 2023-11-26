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

    @Override
    public String toString() {
        return "Etat reglage timer";
    }

}
