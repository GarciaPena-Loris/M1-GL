package com.cabinet.server.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	/* CONSTRUCTOR */
	public Server() {
		
	}
	
	/* METHODS */
	public static void main(String[] args) {
		try {
			System.setProperty("java.security.policy", "security.policy");

			CabinetMedical cabinet = new CabinetMedical();

			cabinet.ajoutAnimal("Médor", "Jean", "Labrador", "Chien", 15, "Wouaf !");
			cabinet.ajoutAnimal("Félix", "Marie", "Siamois", "Chat", 10, "Miaou !");
			cabinet.ajoutAnimal("Titi", "Pierre", "Canari", "Oiseau", 5, "Cui cui !");
			cabinet.ajoutAnimal("Rex", "Paul", "Bulldog", "Chien", 15, "Wouaf !");
			cabinet.ajoutAnimal("Médor", "Jean", "Labrador", "Chien", 15, "Wouaf !");
			cabinet.ajoutAnimal("Félix", "Marie", "Siamois", "Chat", 10, "Miaou !", "Malade");
			cabinet.ajoutAnimal("Titi", "Pierre", "Canari", "Oiseau", 5, "Cui cui !");
			cabinet.ajoutAnimal("Rex", "Paul", "Bulldog", "Chien", 15, "Wouaf !");
			cabinet.ajoutAnimal("Médor", "Jean", "Labrador", "Chien", 15, "Wouaf !");
			cabinet.ajoutAnimal("Edouar", "Hugue", "Lézard", "Reptile", 10, "Sssiou !", "Mort");
			cabinet.ajoutAnimal("Denver", "Hugue", "T-Rex", "Dinausaure", 100, "Graou !");
			cabinet.ajoutAnimal("Shrek", "Dreamworks", "Ogre", "Ogre", 14000000, "Greuh", "Vieux");
			cabinet.ajoutAnimal("Nemo", "Disney", "Poisson-Clown", "Poisson", 5, "Bloup");
			cabinet.ajoutAnimal("Franklin", "Nickelodeon", "Tortue", "Reptile", 100, "Tortue");
			cabinet.ajoutAnimal("Titi", "Warner Bros", "Canari", "Oiseau", 5, "Cui cui");
			cabinet.ajoutAnimal("Chat Potté", "Dreamworks", "Chat-Epeiste", "Chat", 10, "En garde !");

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
