package com.cabinet.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ICabinetMedical extends Remote {

        // Callback
        public void enregistrerAlertCallback(IClientCallback callback) throws RemoteException;

        boolean ajoutAnimal(String nom, String maitre, String race, Espece espece, String cri) throws RemoteException;

        boolean ajoutAnimal(String nom, String maitre, String race, Espece espece, String cri, String etat)
                        throws RemoteException;

        boolean ajoutAnimal(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne, String cri)
                        throws RemoteException;

        boolean ajoutAnimal(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne, String cri,
                        String etat)
                        throws RemoteException;

        int nombreAnimaux() throws RemoteException;

        IAnimal chercherAnimal(String nom) throws RemoteException;

        boolean supprimerAnimal(String nom) throws RemoteException;

        ArrayList<IAnimal> getPatients() throws RemoteException;

}
