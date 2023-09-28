package com.cabinet.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DossierDeSuivi extends UnicastRemoteObject {

	protected String contenue;

    protected DossierDeSuivi(String contenue) throws RemoteException {
		this.contenue = contenue;
	}

	/* METHODS */
	public String afficherDossierDeSuivi() throws RemoteException {
		return this.contenue;
	}

	public void modifierDossierDeSuivi(String contenue) throws RemoteException {
		this.contenue += contenue;
	}
}
