package com.cabinet.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cabinet.common.rmi.Espece;
import com.cabinet.common.rmi.IClientCallback;
import com.cabinet.common.rmi.IAnimal;
import com.cabinet.common.rmi.ICabinetMedical;

public class CabinetMedical extends UnicastRemoteObject implements ICabinetMedical {

    private ArrayList<IAnimal> patients;
    // Callback
    private ArrayList<IClientCallback> listeClients;

    public CabinetMedical() throws RemoteException {
        patients = new ArrayList<IAnimal>();
        listeClients = new ArrayList<IClientCallback>();
    }

    // Callback
    public void enregistrerAlertCallback(IClientCallback client) throws RemoteException {
        if (!listeClients.contains(client)) {
            listeClients.add(client);
        }
    }

    public void supprimerAlertCallback(IClientCallback client) throws RemoteException {
        if (!listeClients.contains(client)) {
            listeClients.remove(client);
        }
    }

    private void notifierSeuilAtteint(int nombrePatients) throws RemoteException {
        for (IClientCallback client : listeClients) {
            try {
                client.notifierSeuilAtteint(nombrePatients);
            } catch (RemoteException e) {
                supprimerAlertCallback(client);
            }
        }
    }

    private void verifierSeuilAtteint() throws RemoteException {
        int nombrePatients = patients.size();
        if (nombrePatients == 100 || nombrePatients == 500 || nombrePatients == 1000) {
            notifierSeuilAtteint(nombrePatients);
        }
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, Espece espece, String cri)
            throws RemoteException {
        IAnimal patient = new Animal(nom, maitre, race, espece, cri);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        System.out.println(
                "\033[3m" + dateString + "\033[0m - Un vétérinaire ajoute un nouvel animal : " + patient);

        Boolean result = patients.add(patient);
        if (result) {
            verifierSeuilAtteint();
        }
        return result;
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, Espece espece, String cri, String etat)
            throws RemoteException {
        IAnimal patient = new Animal(nom, maitre, race, espece, cri, etat);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        System.out.println(
                "\033[3m" + dateString + "\033[0m - Un vétérinaire ajoute un nouvel animal : " + patient);

        Boolean result = patients.add(patient);
        if (result) {
            verifierSeuilAtteint();
        }
        return result;
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne,
            String cri)
            throws RemoteException {
        IAnimal patient = new Animal(nom, maitre, race, nomEspece, dureeDeVieMoyenne, cri);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        System.out.println(
                "\033[3m" + dateString + "\033[0m - Un vétérinaire ajoute un nouvel animal : " + patient);

        Boolean result = patients.add(patient);
        if (result) {
            verifierSeuilAtteint();
        }
        return result;
    }

    @Override
    public boolean ajoutAnimal(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne,
            String cri, String etat)
            throws RemoteException {
        IAnimal patient = new Animal(nom, maitre, race, nomEspece, dureeDeVieMoyenne, cri, etat);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(date);

        System.out.println(
                "\033[3m" + dateString + "\033[0m - Un vétérinaire ajoute un nouvel animal : " + patient);

        Boolean result = patients.add(patient);
        if (result) {
            verifierSeuilAtteint();
        }
        return result;
    }

    @Override
    public int nombreAnimaux() throws RemoteException {
        return patients.size();
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

                Boolean result = patients.remove(patient);
                if (result) {
                    verifierSeuilAtteint();
                }
                return result;
            }
        }
        return false;
    }

    @Override
    public ArrayList<IAnimal> getPatients() throws RemoteException {
        return patients;
    }

}
