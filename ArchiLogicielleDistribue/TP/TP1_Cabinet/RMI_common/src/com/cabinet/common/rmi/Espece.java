package com.cabinet.common.rmi;

import java.io.Serializable;

public class Espece implements Serializable {
    private String nom;
    private int dureeDeVieMoyenne;

    public Espece(String nom, int dureeDeVieMoyenne) {
        this.nom = nom;
        this.dureeDeVieMoyenne = dureeDeVieMoyenne;
    }

    public String getNom() {
        return nom;
    }

    public int getDureeDeVieMoyenne() {
        return dureeDeVieMoyenne;
    }
}