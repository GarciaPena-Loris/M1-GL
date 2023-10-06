package com.cabinet.client.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import com.cabinet.common.rmi.Espece;
import com.cabinet.common.rmi.IAnimal;
import com.cabinet.common.rmi.ICabinetMedical;

public class Client {
	private Client() {
	}

	private static void afficherAnimaux(ICabinetMedical cabinet) {
		// Afficher tous les animaux
		try {
			System.out.println("Liste des animaux: ");
			cabinet.getPatients().forEach((animal) -> {
				try {
					System.out.println("\t" + animal.afficherAnimal());
					Thread.sleep(30);

				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private static void afficherAnimauxComplet(ICabinetMedical cabinet) {
		// Afficher tous les animaux complet
		try {
			System.out.println("Liste des animaux: ");
			cabinet.getPatients().forEach((animal) -> {
				try {
					System.out.println("\t" + animal.afficherAnimalComplet());
					Thread.sleep(50);

				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private static void chercherAnimal(ICabinetMedical cabinet) {
		// Chercher un animal
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Saisir un nom d'animal: ");
			String inputString = scanner.nextLine();
			IAnimal animal = cabinet.chercherAnimal(inputString);

			if (animal != null) {
				// System.out.println("Object : " + animal + "\n");

				System.out.println(animal.afficherAnimalComplet());

				// Choix modification dossier de suivi
				System.out.println("Voulez-vous modifier le dossier de suivi ? (oui/non)");
				inputString = scanner.nextLine();
				if (inputString.equals("oui")) {
					// Saisir nouveau dossier de suivi
					System.out.println("Saisir nouveau dossier de suivi: ");
					inputString = scanner.nextLine();
					animal.modifierDossierDeSuivi(inputString);
					System.out.println("Le nouveau dossier de suivi: " + animal.afficherDossierDeSuivi());
				} else {
					System.out.println("Dossier de suivi non modifié");
				}

			} else {
				System.out.println("Animal non trouvé");
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private static void ajouterAnimal(ICabinetMedical cabinet) {
		System.out.println("Veuillez saisir les informations de l'animal...");
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("  Saisir un nom d'animal: ");
			String nom = scanner.nextLine();
			System.out.println("  Saisir un nom de maitre: ");
			String maitre = scanner.nextLine();
			System.out.println("  Saisir une race: ");
			String race = scanner.nextLine();
			System.out.println("  Saisir une espece: ");
			String nomEspece = scanner.nextLine();
			System.out.println("  Saisir une durée de vie moyenne: ");
			int dureeDeVieMoyenne = scanner.nextInt();
			System.out.println("  Saisir un cri: ");
			String cri = scanner.nextLine();

			boolean succed = cabinet.ajoutAnimal(nom, maitre, race, nomEspece, dureeDeVieMoyenne, cri);
			if (succed) {
				System.out.println("Animal ajouté :");
				System.out.println(cabinet.chercherAnimal(nom).afficherAnimalComplet());
			} else
				System.out.println("Animal non ajouté.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void supprimerAnimal(ICabinetMedical cabinet) {
		// Supprimer un animal
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Saisir un nom d'animal: ");
			String inputString = scanner.nextLine();
			boolean succed = cabinet.supprimerAnimal(inputString);
			if (succed)
				System.out.println("Animal supprimé !");
			else
				System.out.println("Animal non trouvé");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void ajouterOurs(ICabinetMedical cabinet) {
		System.out.println("Veuillez saisir les informations de l'animal...");
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("  Saisir un nom d'animal: ");
			String nom = scanner.nextLine();
			System.out.println("  Saisir un nom de maitre: ");
			String maitre = scanner.nextLine();
			System.out.println("  Saisir une race: ");
			String race = scanner.nextLine();

			Espece ours = new Ours();
			boolean succed = cabinet.ajoutAnimal(nom, maitre, race, ours, "Rouaaaahhh !");

			if (succed) {
				System.out.println("Animal ajouté :");
				System.out.println(cabinet.chercherAnimal(nom).afficherAnimalComplet());
			} else
				System.out.println("Animal non ajouté.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String host = (args.length < 1) ? null : args[0];
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(host);
			System.out.println("Registry : " + registry);
			System.out.println("Registry list : " );
			for (String name : registry.list()) {
				System.out.println("\t" + name);
			}

			// IAnimal animal = (IAnimal) registry.lookup("Animal");
			ICabinetMedical cabinet = (ICabinetMedical) registry.lookup("Cabinet");

			Scanner scanner = new Scanner(System.in);
			while (true) {
				// Choix action
				System.out.println("\nChoisir une action : ");
				System.out.println("\t1. Afficher tous les animaux (court)");
				System.out.println("\t2. Afficher tous les animaux (complet)");
				System.out.println("\t3. Chercher un animal");
				System.out.println("\t4. Ajouter un animal");
				System.out.println("\t5. Supprimer un animal");
				System.out.println("\t6. Ajouter un Ours");
				System.out.println("\t7. Quitter");

				int inputInt = scanner.nextInt();
				switch (inputInt) {
					case 1:
						afficherAnimaux(cabinet);
						break;
					case 2:
						afficherAnimauxComplet(cabinet);
						break;
					case 3:
						chercherAnimal(cabinet);
						break;
					case 4:
						ajouterAnimal(cabinet);
						break;
					case 5:
						supprimerAnimal(cabinet);
						break;
					case 6:
						ajouterOurs(cabinet);
						break;
					case 7:
						System.out.println("Au revoir !");
						scanner.close();
						System.exit(0);
						break;
					default:
						System.out.println("Choix invalide...");
						break;
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
