package com.cabinet.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.cabinet.common.rmi.Espece;
import com.cabinet.common.rmi.IAnimal;
import com.cabinet.common.rmi.ICabinetMedical;

public class CabinetMedical extends UnicastRemoteObject implements ICabinetMedical {

    private ArrayList<IAnimal> patients;

    public CabinetMedical() throws RemoteException {
        patients = new ArrayList<IAnimal>();
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, Espece espece, String cri)
            throws RemoteException {
        IAnimal patient = new AnimalImpl(nom, maitre, race, espece, cri);

        return patients.add(patient);
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne,
            String cri)
            throws RemoteException {
        IAnimal patient = new AnimalImpl(nom, maitre, race, nomEspece, dureeDeVieMoyenne, cri);

        return patients.add(patient);
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne,
            String cri, String etat)
            throws RemoteException {
        IAnimal patient = new AnimalImpl(nom, maitre, race, nomEspece, dureeDeVieMoyenne, cri, etat);

        return patients.add(patient);
    }

    @Override
    public IAnimal chercherAnimal(String nom) throws RemoteException {
        for (IAnimal patient : patients) {
            if (patient.getNom().equals(nom)) {
                return patient;
            }
        }
        return null;
    }

    @Override
    public boolean supprimerAnimal(String nom) throws RemoteException {
        for (IAnimal patient : patients) {
            if (patient.afficherNomAnimal().equals(nom)) {
                return patients.remove(patient);
            }
        }
        return false;
    }

    @Override
    public ArrayList<IAnimal> getPatients() throws RemoteException {
        return patients;
    }

}
