package com.cabinet.server.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.cabinet.common.rmi.Espece;

public class Server {
	/* CONSTRUCTOR */
	public Server() {

	}

	private static void ajouterAnimals(CabinetMedical cabinet) throws RemoteException {
		// Especes
		Espece chien = new Chien();
		Espece chat = new Chat();
		Espece poisson = new Poisson();
		Espece oiseau = new Oiseau();
		Espece reptile = new Reptile();
		Espece mammifere = new Mammifere();

		cabinet.ajoutAnimal("Medor", "Jean", "Labrador", chien, "Wouaf !");
		cabinet.ajoutAnimal("Titi", "Warner Bros", "Canari", oiseau, "Cui cui");
		cabinet.ajoutAnimal("Felix", "Marie", "Siamois", chat, "Miaou !");
		cabinet.ajoutAnimal("Tutu", "Pierre", "Canari", oiseau, "Cui cui !");
		cabinet.ajoutAnimal("Rex", "Paul", "Bulldog", chien, "Wouaf !");
		cabinet.ajoutAnimal("Medor", "Jean", "Labrador", chien, "Wouaf !");
		cabinet.ajoutAnimal("Chocolat", "Marie", "Siamois", chat, "Miaou !", "Malade");
		cabinet.ajoutAnimal("Tata", "Pierre", "Canari", oiseau, "Cui cui !");
		cabinet.ajoutAnimal("Rex", "Paul", "Bulldog", chien, "Wouaf !");
		cabinet.ajoutAnimal("Pistache", "Jean", "Chiwawa", chien, "Wif !");
		cabinet.ajoutAnimal("Edouar", "Hugue", "Lezard", reptile, "Sssiou !", "Mort");
		cabinet.ajoutAnimal("Denver", "Hugue", "T-Rex", "Dinausaure", 14000000, "Graou !", "Vieux");
		cabinet.ajoutAnimal("Shrek", "Dreamworks", "Ogre", "Ogre", 150, "Greuh");
		cabinet.ajoutAnimal("Nemo", "Disney", "Poisson-Clown", poisson, "Bloup");
		cabinet.ajoutAnimal("Franklin", "Nickelodeon", "Tortue", reptile, "Je sais faire mes lacets !");
		cabinet.ajoutAnimal("Chat Potte", "Dreamworks", "Chat-Epeiste", "Chat", 10, "En garde !");
		cabinet.ajoutAnimal("Max", "Sophie", "Golden Retriever", chien, "Woof !");
		cabinet.ajoutAnimal("Mia", "Alice", "Persan", chat, "Meow !");
		cabinet.ajoutAnimal("Polly", "David", "Perroquet", oiseau, "Squawk !");
		cabinet.ajoutAnimal("Rax", "Sophie", "Labrador", chien, "Wouaf !");
		cabinet.ajoutAnimal("Coco", "Lucas", "Perroquet", oiseau, "Squawk !");
		cabinet.ajoutAnimal("Whiskers", "Emily", "Siamois", chat, "Miaou !");
		cabinet.ajoutAnimal("Bella", "Michael", "Golden Retriever", chien, "Woof !");
		cabinet.ajoutAnimal("Sparky", "Olivia", "Chiwawa", chien, "Wif !");
		// Ajout de 75 animaux supplémentaires
		cabinet.ajoutAnimal("Luna", "Sophie", "Persan", chat, "Meow !");
		cabinet.ajoutAnimal("Buddy", "Lucas", "Bulldog", chien, "Wouaf !");
		cabinet.ajoutAnimal("Rocky", "Emily", "Labrador", chien, "Wouaf !");
		cabinet.ajoutAnimal("Cleo", "Michael", "Siamois", chat, "Miaou !");
		cabinet.ajoutAnimal("Simba", "Olivia", "Lion", mammifere, "Grrr !");
		cabinet.ajoutAnimal("Spike", "Noah", "Iguane", reptile, "Sssss !");
		cabinet.ajoutAnimal("Buddy", "Emma", "Dalmatien", chien, "Wouaf !");
		cabinet.ajoutAnimal("Oliver", "Liam", "Persan", chat, "Meow !");
		cabinet.ajoutAnimal("Nala", "Ava", "Lion", mammifere, "Grrr !");
		cabinet.ajoutAnimal("Lola", "Jackson", "Perroquet", oiseau, "Squawk !");
		cabinet.ajoutAnimal("Max", "Olivia", "Cocker Spaniel", chien, "Woof !");
		cabinet.ajoutAnimal("Daisy", "Sophia", "Yorkshire Terrier", chien, "Wouaf !");
		cabinet.ajoutAnimal("Charlie", "Lucas", "Chihuahua", chien, "Wif !");
		cabinet.ajoutAnimal("Milo", "Oliver", "Maine Coon", chat, "Miaou !");
		cabinet.ajoutAnimal("Loki", "Ava", "Husky Sibérien", chien, "Wouaf !");
		cabinet.ajoutAnimal("Lily", "Chloe", "Persan", chat, "Meow !");
		cabinet.ajoutAnimal("Zoe", "Jackson", "Poisson Rouge", poisson, "Bloup !");
		cabinet.ajoutAnimal("Oscar", "Liam", "Beagle", chien, "Wouaf !");
		cabinet.ajoutAnimal("Cleo", "Olivia", "Maine Coon", chat, "Miaou !");
		cabinet.ajoutAnimal("Lola", "Emma", "Perroquet", oiseau, "Squawk !");
		cabinet.ajoutAnimal("Maddie", "Sophia", "Golden Retriever", chien, "Woof !");
		cabinet.ajoutAnimal("Romeo", "Lucas", "Siamois", chat, "Miaou !");
		cabinet.ajoutAnimal("Lulu", "Ava", "Labrador", chien, "Wouaf !");
		cabinet.ajoutAnimal("Penny", "Chloe", "Dalmatien", chien, "Wouaf !");
		cabinet.ajoutAnimal("Ziggy", "Jackson", "Chow Chow", chien, "Wouaf !");
		cabinet.ajoutAnimal("Molly", "Oliver", "Persan", chat, "Meow !");
		cabinet.ajoutAnimal("Toby", "Sophie", "Cavalier King Charles", chien, "Woof !");
		cabinet.ajoutAnimal("Misty", "Liam", "Maine Coon", chat, "Miaou !");
		cabinet.ajoutAnimal("Leo", "Lucas", "Bulldog Français", chien, "Wouaf !");
		cabinet.ajoutAnimal("Maggie", "Ava", "Bouledogue Anglais", chien, "Wouaf !");
		cabinet.ajoutAnimal("Bentley", "Emma", "Rottweiler", chien, "Wouaf !");
		cabinet.ajoutAnimal("Sammy", "Jackson", "Caniche", chien, "Wouaf !");
		cabinet.ajoutAnimal("Lola", "Sophia", "Sphynx", chat, "Meow !");
		cabinet.ajoutAnimal("Rosie", "Liam", "Birman", chat, "Miaou !");
		cabinet.ajoutAnimal("Zeus", "Olivia", "Berger Allemand", chien, "Woof !");
		cabinet.ajoutAnimal("Ruby", "Ava", "Yorkshire Terrier", chien, "Wouaf !");
		cabinet.ajoutAnimal("Cocoa", "Lucas", "Cocker Spaniel", chien, "Wouaf !");
		cabinet.ajoutAnimal("Lucky", "Chloe", "Maine Coon", chat, "Meow !");
		cabinet.ajoutAnimal("Ollie", "Jackson", "Himalayen", chat, "Miaou !");
		cabinet.ajoutAnimal("Boomer", "Sophie", "Schnauzer", chien, "Woof !");
		cabinet.ajoutAnimal("Bella", "Liam", "Shih Tzu", chien, "Wouaf !");
		// Ajout de 34 animaux supplémentaires avec des noms différents
		cabinet.ajoutAnimal("Buddy", "Aiden", "Bichon Frisé", chien, "Wouaf !");
		cabinet.ajoutAnimal("Oscar", "Sophie", "Berger Australien", chien, "Wouaf !");
		cabinet.ajoutAnimal("Molly", "Lucas", "Siamois", chat, "Miaou !");
		cabinet.ajoutAnimal("Milo", "Ava", "Bulldog Français", chien, "Wouaf !");
		cabinet.ajoutAnimal("Daisy", "Jackson", "Yorkshire Terrier", chien, "Wouaf !");
		cabinet.ajoutAnimal("Lulu", "Chloe", "Bouledogue Anglais", chien, "Wouaf !");
		cabinet.ajoutAnimal("Simba", "Sophia", "Tigre Blanc", mammifere, "Grrr !");
		cabinet.ajoutAnimal("Rosie", "Liam", "Himalayen", chat, "Miaou !");
		cabinet.ajoutAnimal("Zeus", "Olivia", "Ragdoll", chat, "Meow !");
		cabinet.ajoutAnimal("Leo", "Aiden", "Perroquet", oiseau, "Squawk !");
		cabinet.ajoutAnimal("Sasha", "Sophie", "Cane Corso", chien, "Wouaf !");
		cabinet.ajoutAnimal("Cleo", "Lucas", "Dalmatien", chien, "Wouaf !");
		cabinet.ajoutAnimal("Lola", "Ava", "Bichon Frisé", chien, "Wouaf !");
		cabinet.ajoutAnimal("Milo", "Jackson", "Sphynx", chat, "Meow !");
		cabinet.ajoutAnimal("Bentley", "Sophia", "Bouledogue Français", chien, "Wouaf !");
		cabinet.ajoutAnimal("Rocky", "Liam", "Maine Coon", chat, "Miaou !");
		cabinet.ajoutAnimal("Gizmo", "Olivia", "Cavalier King Charles", chien, "Woof !");
		cabinet.ajoutAnimal("Ziggy", "Aiden", "Golden Retriever", chien, "Woof !");
		cabinet.ajoutAnimal("Lola", "Sophie", "Labrador", chien, "Wouaf !");
		cabinet.ajoutAnimal("Misty", "Lucas", "Maine Coon", chat, "Miaou !");
		cabinet.ajoutAnimal("Oreo", "Ava", "Chihuahua", chien, "Wif !");
		cabinet.ajoutAnimal("Charlie", "Jackson", "Cavalier King Charles", chien, "Woof !");
		cabinet.ajoutAnimal("Buddy", "Chloe", "Persan", chat, "Meow !");
		cabinet.ajoutAnimal("Lulu", "Sophia", "Bulldog", chien, "Wouaf !");
		cabinet.ajoutAnimal("Daisy", "Liam", "Sphynx", chat, "Meow !");
		cabinet.ajoutAnimal("Lola", "Olivia", "Berger Allemand", chien, "Woof !");
		cabinet.ajoutAnimal("Oliver", "Aiden", "Poisson Rouge", poisson, "Bloup !");
		cabinet.ajoutAnimal("Zoe", "Sophie", "Pélican", oiseau, "Squawk !");
		cabinet.ajoutAnimal("Maddie", "Lucas", "Maine Coon", chat, "Miaou !");
		cabinet.ajoutAnimal("Toby", "Ava", "Cocker Spaniel", chien, "Wouaf !");
		cabinet.ajoutAnimal("Molly", "Olivia", "Dalmatien", chien, "Wouaf !");
		cabinet.ajoutAnimal("Luna", "Sophia", "Canari", oiseau, "Cui cui !");
		cabinet.ajoutAnimal("Bella", "Liam", "Siamois", chat, "Miaou !");
		cabinet.ajoutAnimal("Spike", "Aiden", "Bulldog Français", chien, "Wouaf !");

	}

	/* METHODS */
	public static void main(String[] args) {
		try {

			System.setProperty("java.security.policy", "security/security.policy");
			// Security manager
			System.setSecurityManager(new SecurityManager());

			// Codebase
			System.setProperty("java.rmi.server.codebase",
					"file:///F:/DossierLoris/MonProfil/MesDocumentEcole/Fac/M1/M1-GL/ArchiLogicielleDistribue/TP/TP1_Cabinet/RMI_client/codebase/");
			
			// System.setProperty("java.rmi.server.codebase",
			// 		"file:///C:/Users/loloy/Desktop/cours/M1/M1-GL/ArchiLogicielleDistribue/TP/TP1_Cabinet/RMI_client/codebase/");

			CabinetMedical cabinet = new CabinetMedical();

			ajouterAnimals(cabinet);

			Registry registry = LocateRegistry.createRegistry(1099);

			if (registry == null)
				System.err.println("Registry not found on port 1099");
			else {
				registry.bind("Cabinet", cabinet);
				System.out.println("Server ready...");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}
