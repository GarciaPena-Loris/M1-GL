package m1.ingelo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Climatiseur {
    protected EtatClimatiseur etatCourant;
    protected EtatClimatiseur[] etats = new EtatClimatiseur[4];
    private int duree;
    private Timer timer;
    private int vitesse;
    private int temperature;

    public Climatiseur() {
        etats[0] = new EEteint(this);
        etats[1] = new EAccueil(this);
        etats[2] = new EReglageTimer(this);
        etats[3] = new EReglageVitesse(this);

        etatCourant = etats[0];
        vitesse = 1;
        temperature = 21;
        duree = 0;
    }

    public void incrTemperature() {
        temperature++;
    }

    public void decrTemperature() {
        temperature--;
    }

    public void incrVitesse() {
        vitesse++;
    }

    public void decrVitesse() {
        vitesse--;
    }

    public void incrDuree() {
        duree++;
    }

    public void decrDuree() {
        duree--;
    }

    public void armerTimer(int duree) {
        final AtomicInteger atomicDuree = new AtomicInteger(duree);
        timer = new Timer(); // Remove the array

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (atomicDuree.get() > 0) {
                    atomicDuree.decrementAndGet();
                } else {
                    timer.cancel();
                    try {
                        onOff(); // Assuming this method turns off the air conditioner
                    } catch (ClimatiseurException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Schedule the task to run every second
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void desarmerTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        duree = 0;
    }

    public int getDuree() {
        return duree;
    }

    protected void onOff() throws ClimatiseurException {
        int etatSuivant = etatCourant.onOff();
        etatCourant = etats[etatSuivant - 1];
        System.out.println("Changement d'état : " + etatCourant);
    }

    protected void home() throws ClimatiseurException {
        int etatSuivant = etatCourant.home();
        etatCourant = etats[etatSuivant - 1];
        System.out.println("Changement d'état : " + etatCourant);
    }

    protected void timer() throws ClimatiseurException {
        int etatSuivant = etatCourant.timer();
        etatCourant = etats[etatSuivant - 1];
        System.out.println("Changement d'état : " + etatCourant);
    }

    protected void airFlow() throws ClimatiseurException {
        int etatSuivant = etatCourant.airFlow();
        etatCourant = etats[etatSuivant - 1];
        System.out.println("Changement d'état : " + etatCourant);
    }

    protected void incr() throws ClimatiseurException {
        etatCourant.incr();
        System.out.println("Incrémentation de " + etatCourant);
    }

    protected void decr() throws ClimatiseurException {
        etatCourant.decr();
        System.out.println("Décrémentation de " + etatCourant);
    }

    public String toString() {
        return "L'état courant (très vite) : " + etatCourant;
    }


}
