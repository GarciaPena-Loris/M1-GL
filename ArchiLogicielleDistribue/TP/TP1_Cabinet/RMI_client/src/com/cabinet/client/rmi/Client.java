package com.cabinet.client.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import com.cabinet.common.rmi.IAnimal;
import com.cabinet.common.rmi.ICabinetMedical;
import com.cabinet.common.rmi.Espece;

public class Client {
	private Client() {
	}

	public static void main(String[] args) {
		String host = (args.length < 1) ? null : args[0];
		try {
			Registry registry = LocateRegistry.getRegistry(host);

			// IAnimal animal = (IAnimal) registry.lookup("Animal");
			ICabinetMedical cabinet = (ICabinetMedical) registry.lookup("Cabinet");

			// Afficher tous les animaux
			System.out.println("Liste des animaux: ");
			cabinet.getPatients().forEach((animal) -> {
				try {
					System.out.println(animal.afficherNomAnimal());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			});

			// Chercher un animal
			Scanner scanner = new Scanner(System.in);
			try {
				System.out.println("Saisir un nom d'animal: ");
				String inputString = scanner.nextLine();
				IAnimal animal = cabinet.chercherAnimal(inputString);

				if (animal != null) {
					System.out.println(animal);

					String nom = animal.afficherNomAnimal();
					String race = animal.afficherRace();
					Espece espece = animal.getEspece();

					System.out.println(nom);
					System.out.println(race);
					System.out.println("Cet animal est un " + espece.getNom()
							+ ", il a donc une durée de vie d'environ " + espece.getDureeDeVieMoyenne() + " ans.");

					animal.crier();

					// Dossier de suivi
					System.out.println("Dossier de suivi: " + animal.afficherDossierDeSuivi());

					// Saisir nouveau dossier de suivi
					try {
						System.out.println("Saisir nouveau dossier de suivi: ");
						inputString = scanner.nextLine();
						animal.modifierDossierDeSuivi(inputString);
						System.out.println("Le nouveau dossier de suivi: " + animal.afficherDossierDeSuivi());
					} finally {
						scanner.close();
					}

					// Nommer
					animal.nommer();

				} else {
					System.out.println("Animal non trouvé");
				}
			} finally {
				scanner.close();
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
