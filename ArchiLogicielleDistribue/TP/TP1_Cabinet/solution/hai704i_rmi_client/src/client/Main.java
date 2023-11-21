package client;

import ui.CabinetController;

/**
 * The `Main` class contains the main method to start the RMI client application. It
 * creates an instance of the `CabinetController` and initiates the user interface.
 * 
 * @see ui.CabinetController
 * @see client.Client
 * 
 * @author anonbnr
 */
public class Main {
	
	/**
     * The main entry point of the RMI client application.
     * This method creates an instance of the `CabinetController` and runs the
     * user interface for interacting with the veterinary cabinet service.
     *
     * @param args The command line arguments (not used in this application).
     */
	public static void main(String[] args) {
		try {
			// create and run a CabinetController parameterized with a Client instance
			new CabinetController(new Client()).run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
