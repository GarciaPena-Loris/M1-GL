package com.cabinet.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cabinet.common.rmi.Espece;
import com.cabinet.common.rmi.IAnimal;

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
		this.dossierDeSuivi = new DossierDeSuivi("\n\t\033[3m" + dateString + "\033[0m : \033[1mRAS\033[0m \n");
	}

	protected AnimalImpl(String nom, String maitre, String race, Espece espece, String cri, String etat)
			throws RemoteException {
		this.nom = nom;
		this.maitre = maitre;
		this.race = race;
		this.espece = espece;
		this.cri = cri;

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		String dateString = formatter.format(date);
		this.dossierDeSuivi = new DossierDeSuivi("\n\t\033[3m" + dateString + "\033[0m : \033[1m" + etat + "\033[0m\n");
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
		this.dossierDeSuivi = new DossierDeSuivi("\n\t\033[3m" + dateString + "\033[0m : \033[1mRAS\033[0m \n");
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
		this.dossierDeSuivi = new DossierDeSuivi("\n\t\033[3m" + dateString + "\033[0m : \033[1m" + etat + "\033[0m\n");
	}

	/* METHODS */
	@Override
	public String getNom() throws RemoteException {
		return this.nom;
	}

	@Override
	public String afficherAnimal() throws RemoteException {
		String detailAnimal = "\033[31m" + this.nom + "\033[0m le \033[33m" + this.race + "\033[0m";

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(date);
		System.out.println("\033[3m" + dateString + "\033[0m - Un client regarde " + detailAnimal + ".");
		return "- " + detailAnimal;
	}

	@Override
	public String afficherAnimalComplet() throws RemoteException {
		Espece copieEspece = new Espece(espece.getNom(), espece.getDureeDeVieMoyenne());
		String detailAnimal = "\033[31m" + this.nom + "\033[0m"
				+ " appartenant à \033[34m" + this.maitre + "\033[0m"
				+ " est un \033[33m" + copieEspece.getNom() + "\033[0m"
				+ " de type \033[32m" + this.race + "\033[0m"
				+ ", ça durée de vie moyenne est de \033[1m" + copieEspece.getDureeDeVieMoyenne() + " ans\033[0m,"
				+ " il crie \033[35m" + this.cri + "\033[0m."
				+ "\n  Son nouveau dossier médical est : " + this.dossierDeSuivi.afficherDossierDeSuivi();

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String dateString = formatter.format(date);

		System.out.println("\033[3m" + dateString + "\033[0m - Un client se renseigne sur l'animal " + detailAnimal);
		return "- L'animal " + detailAnimal;
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
		this.dossierDeSuivi
				.modifierDossierDeSuivi("\t\033[3m" + dateString + "\033[0m : \033[1m" + contenue + "\033[0m\n");
	}

	@Override
	public Espece getEspece() throws RemoteException {
		return new Espece(espece.getNom(), espece.getDureeDeVieMoyenne());
	}

	// toString
	@Override
	public String toString() {
		return "\033[31m" + this.nom + "\033[0m le \033[33m" + this.race + "\033[0m";
	}
}
