package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

/**
 * The Main class contains the main method to start the RMI server application. It creates and sets up 
 * and instance of the Server class to initialize its RMI server components, including security, codebase, registry,
 * and the veterinary cabinet.
 * 
 * @author anonbnr
 */
public class Main {

	/**
     * The entry point of the RMI server application.
     * 
     * @param args The command line arguments (not used in this application).
     */
	public static void main(String[] args) {
		Server server = new Server();
		try {
			server.setUp();
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
}
