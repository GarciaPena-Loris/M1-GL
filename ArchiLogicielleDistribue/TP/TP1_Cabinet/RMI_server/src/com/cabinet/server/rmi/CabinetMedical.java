package com.cabinet.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        System.out.println(
                "\033[3m" + dateString + "\033[0m - Un vétérinaire ajoute un nouvel animal : " + patient);
        return patients.add(patient);
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, Espece espece, String cri, String etat)
            throws RemoteException {
        IAnimal patient = new AnimalImpl(nom, maitre, race, espece, cri, etat);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        System.out.println(
                "\033[3m" + dateString + "\033[0m - Un vétérinaire ajoute un nouvel animal : " + patient);
        return patients.add(patient);
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne,
            String cri)
            throws RemoteException {
        IAnimal patient = new AnimalImpl(nom, maitre, race, nomEspece, dureeDeVieMoyenne, cri);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        System.out.println(
                "\033[3m" + dateString + "\033[0m - Un vétérinaire ajoute un nouvel animal : " + patient);
        return patients.add(patient);
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne,
            String cri, String etat)
            throws RemoteException {
        IAnimal patient = new AnimalImpl(nom, maitre, race, nomEspece, dureeDeVieMoyenne, cri, etat);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        System.out.println(
                "\033[3m" + dateString + "\033[0m - Un vétérinaire ajoute un nouvel animal : " + patient);

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
            if (patient.getNom().equals(nom)) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String dateString = formatter.format(date);

                System.out.println(
                        "\033[3m" + dateString + "\033[0m - Un vétérinaire retire l'animal " + patient);

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
