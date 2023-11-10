package m1.archi.model;

import m1.archi.hotel.Offre;

import java.util.ArrayList;

public class Offres {
    private ArrayList<Offre> offres;

    public Offres() {
        offres = new ArrayList<>();
    }

    public ArrayList<Offre> getOffres() {
        return offres;
    }

    public void setOffres(ArrayList<Offre> offres) {
        this.offres = offres;
    }
}
