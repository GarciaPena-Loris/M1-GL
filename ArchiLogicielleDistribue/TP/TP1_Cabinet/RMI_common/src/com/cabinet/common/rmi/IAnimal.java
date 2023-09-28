package com.cabinet.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAnimal extends Remote {
	/* METHODS */
	String getNom() throws RemoteException;
	
	String afficherNomAnimal() throws RemoteException;

	String afficherRace() throws RemoteException;

	void crier() throws RemoteException;

	String afficherDossierDeSuivi() throws RemoteException;

	void modifierDossierDeSuivi(String contenue) throws RemoteException;

	Espece getEspece() throws RemoteException;

	void nommer() throws RemoteException;

}
