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

	/* METHODS */
	public static void main(String[] args) {
		try {

			System.setProperty("java.security.policy", "security/security.policy");
			// Security manager
			System.setSecurityManager(new SecurityManager());

			// Codebase
			System.setProperty("java.rmi.server.codebase",
					"file:///C:/Users/loloy/Desktop/cours/M1/M1-GL/ArchiLogicielleDistribue/TP/TP1_Cabinet/RMI_client/codebase/");

			CabinetMedical cabinet = new CabinetMedical();

			// Especes
			Espece chien = new Chien();
			Espece chat = new Chat();
			Espece poisson = new Poisson();
			Espece oiseau = new Oiseau();
			Espece reptile = new Reptile();

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
