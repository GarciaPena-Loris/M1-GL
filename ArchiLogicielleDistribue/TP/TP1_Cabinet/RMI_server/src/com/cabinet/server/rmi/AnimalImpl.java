package com.cabinet.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cabinet.common.rmi.IAnimal;
import com.cabinet.common.rmi.Espece;

public class AnimalImpl extends UnicastRemoteObject implements IAnimal {

	private String nom;
	private String maitre;
	private Espece espece;
	private String race;

	private String cri;

	private DossierDeSuivi dossierDeSuivi;

	/* CONSTRUCTOR */
	protected AnimalImpl(String nom, String maitre, String race, Espece espece, String cri) throws RemoteException {
		this.nom = nom;
		this.maitre = maitre;
		this.race = race;
		this.espece = espece;
		this.cri = cri;

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		String dateString = formatter.format(date);
		this.dossierDeSuivi = new DossierDeSuivi("\n\t" + dateString + " : RAS \n");
	}

	protected AnimalImpl(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne, String cri)
			throws RemoteException {
		this.nom = nom;
		this.maitre = maitre;
		this.race = race;
		this.espece = new Espece(nomEspece, dureeDeVieMoyenne);
		this.cri = cri;

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		String dateString = formatter.format(date);
		this.dossierDeSuivi = new DossierDeSuivi("\n\t" + dateString + " : RAS \n");
	}

	protected AnimalImpl(String nom, String maitre, String race, String nomEspece, int dureeDeVieMoyenne, String cri,
			String etat)
			throws RemoteException {
		this.nom = nom;
		this.maitre = maitre;
		this.race = race;
		this.espece = new Espece(nomEspece, dureeDeVieMoyenne);
		this.cri = cri;

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		String dateString = formatter.format(date);
		this.dossierDeSuivi = new DossierDeSuivi("\n\t" + dateString + " : " + etat + "\n");
	}

	/* METHODS */
	@Override
	public String getNom() throws RemoteException {
		return this.nom;
	}

	@Override
	public String afficherNomAnimal() throws RemoteException {
		return "L'animal " + this.nom + " appartient à " + this.maitre;
	}

	@Override
	public String afficherRace() throws RemoteException {
		return "L'animal " + this.nom + " est un " + this.race;
	}

	@Override
	public void crier() throws RemoteException {
		System.out.println(this.cri);
	}

	@Override
	public String afficherDossierDeSuivi() throws RemoteException {
		return this.dossierDeSuivi.afficherDossierDeSuivi();
	}

	@Override
	public void modifierDossierDeSuivi(String contenue) throws RemoteException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		String dateString = formatter.format(date);
		this.dossierDeSuivi.modifierDossierDeSuivi("\t" + dateString + " : " + contenue + "\n");
	}

	@Override
	public Espece getEspece() throws RemoteException {
		return new Espece(espece.getNom(), espece.getDureeDeVieMoyenne());
	}

	@Override
	public void nommer() throws RemoteException {
		Espece copieEspece = new Espece(espece.getNom(), espece.getDureeDeVieMoyenne());
		System.out.println("L'animal " + this.nom
				+ " appartient à " + this.maitre
				+ " c'est un " + copieEspece.getNom()
				+ " de type " + this.race
				+ ", ça durée de vie moyenne est de " + copieEspece.getDureeDeVieMoyenne() + " ans."
				+ "\nSon dossier médical est : " + this.dossierDeSuivi.afficherDossierDeSuivi());
	}
}
