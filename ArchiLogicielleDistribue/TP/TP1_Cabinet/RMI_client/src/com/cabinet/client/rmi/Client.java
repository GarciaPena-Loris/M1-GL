package com.cabinet.client.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
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
			System.out.println("Liste des \033[1m" + cabinet.nombreAnimaux() + "\033[0m animaux : ");
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
			System.out.println("  - Saisir un nom d'animal: ");
			String nom = scanner.nextLine();
			System.out.println("  - Saisir un nom de maitre: ");
			String maitre = scanner.nextLine();
			System.out.println("  - Saisir une race: ");
			String race = scanner.nextLine();
			System.out.println("  - Saisir une espece: ");
			String nomEspece = scanner.nextLine();
			System.out.println("  - Saisir un cri: ");
			String cri = scanner.nextLine();
			System.out.println("  - Saisir une durée de vie moyenne: ");
			int dureeDeVieMoyenne = scanner.nextInt();

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

	private static void ajouterNombreAnimal(ICabinetMedical cabinet) throws RemoteException {
		System.out.println("Combien souhaitez vous ajouter d'animal ?");
		Scanner scanner = new Scanner(System.in);

		// Création d'un générateur de nombres aléatoires
		Random random = new Random();

		// Listes de chaînes possibles pour les noms, maîtres, races, espèces et cris
		String[] noms = { "Buddy", "Oscar", "Molly", "Milo", "Daisy", "Lulu", "Simba", "Rosie", "Zeus", "Leo",
				"Sasha", "Cleo", "Oreo", "Charlie", "Bella", "Spike", "Coco", "Lily", "Luna", "Maddie",
				"Toby", "Rocky", "Gizmo", "Ziggy", "Lola", "Chloe", "Aiden", "Sophia", "Jackson", "Olivia",
				"Ava", "Noah", "Lucas", "Emily", "Michael", "Emma", "Liam", "Sophie", "Chloe", "Sophia", "Aiden",
				"Noah", "Oliver", "Olivia", "Lucas", "Sophie", "Ava", "Jackson", "Liam", "Emma" };
		String[] maitres = { "Jean", "Warner Bros", "Marie", "Pierre", "Paul", "Alice", "David", "Sophie", "Lucas",
				"Emily", "Michael", "Ava", "Noah", "Oliver", "Liam", "Chloe", "Jackson", "Sophia", "Aiden", "Emma" };
		String[] races = { "Labrador", "Canari", "Siamois", "Bulldog", "Chiwawa", "Lezard", "T-Rex", "Ogre",
				"Poisson-Clown", "Tortue", "Chat-Epeiste", "Golden Retriever", "Persan", "Perroquet", "Lion", "Iguane",
				"Maine Coon", "Husky Sibérien", "Yorkshire Terrier", "Bichon Frisé" };
		String[] nomsEspece = { "Chien", "Chat", "Oiseau", "Reptile", "Mammifere", "Poisson" };
		String[] cris = { "Wouaf !", "Cui cui !", "Miaou !", "Sssss !", "Grrr !", "Bloup", "Squawk !", "Woof !",
				"Wif !", "Meow !", "Greuh", "En garde !", "Squiou !", "Vieux", "Je sais faire mes lacets !" };

		// Nombre d'animaux à générer
		int nombreAnimaux = scanner.nextInt();

		for (int i = 0; i < nombreAnimaux; i++) {
			// Sélectionnez aléatoirement des chaînes dans les listes
			String nom = noms[random.nextInt(noms.length)];
			String maitre = maitres[random.nextInt(maitres.length)];
			String race = races[random.nextInt(races.length)];
			String nomEspece = nomsEspece[random.nextInt(nomsEspece.length)];
			int dureeDeVieMoyenne = random.nextInt(99) + 1; // Durée de vie moyenne entre 1 et 99 ans
			String cri = cris[random.nextInt(cris.length)];

			// Ajoutez l'animal au cabinet
			cabinet.ajoutAnimal(nom, maitre, race, nomEspece, dureeDeVieMoyenne, cri);
			System.out.println("Animal ajouté :");
			System.out.println(cabinet.chercherAnimal(nom).afficherAnimal());
		}

		System.out.println("Ajout de " + nombreAnimaux + " animaux terminé !");
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

			// IAnimal animal = (IAnimal) registry.lookup("Animal");
			ICabinetMedical cabinet = (ICabinetMedical) registry.lookup("Cabinet");

			// Enregistrez le client pour les alertes
			ClientCallbackImpl clientCallback = new ClientCallbackImpl();
			cabinet.enregistrerAlertCallback(clientCallback);

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
				System.out.println("\t7. Ajouter beaucoup d'animaux");
				System.out.println("\t8. Quitter");

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
						ajouterNombreAnimal(cabinet);
						break;
					case 8:
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
