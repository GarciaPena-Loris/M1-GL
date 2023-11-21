package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import commons.IAnimal;
import commons.ICabinet;
import commons.IClient;
import model.CodebaseManager;
import model.Snake;

/**
 * The `CabinetController` class serves as the controller for the veterinary cabinet
 * service's user interface. It manages user interactions, menu navigation, and
 * communication with the RMI client.
 * 
 * @see ui.AbstractController
 * @see ui.ComplexUserInputProcessor
 * @see ui.StringProcessor
 * @see ui.StrictlyPositiveIntegerProcessor
 * @see commons.ICabinet
 * @see commons.IClient
 * @see model.CodebaseManager
 * @see model.Snake
 * 
 * @author anonbnr
 */
public class CabinetController extends AbstractController {
	/* ATTRIBUTES */
	/**
     * The RMI client used for communication with the veterinary cabinet service.
     */
	private IClient client;
	
	/**
     * The cabinet instance representing the veterinary cabinet being connected to.
     */
	private ICabinet cabinet;
	
	/**
     * A `BufferedReader` for reading user input.
     */
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/* CONSTRUCTORS */
	/**
     * Constructs a `CabinetController` with the specified RMI client.
     * 
     * @param client The RMI client for the veterinary cabinet service.
     */
	public CabinetController(IClient client) {
		this.client = client;
	}

	/* METHODS */
	/**
     * Displays the main menu for the veterinary cabinet service.
     */
	@Override
	protected void menu() {
		outputBuilder.append("Welcome to the online veterinary cabinet services");
		outputBuilder.append("\nChoose an action:");
		outputBuilder.append("\n" +QUIT+". Exit.");
		outputBuilder.append("\n1. Connect to a cabinet.");

		System.out.println(outputBuilder);

		outputBuilder.setLength(0);
	}

	/**
     * Displays the menu specific to a connected veterinary cabinet.
     * This menu allows users to perform various actions related to the cabinet's services.
     * It includes options to display patients, admit patients, simulate admissions, and more.
     * 
     * @throws RemoteException If an RMI-related error occurs.
     */
	private void cabinetMenu() throws RemoteException {
		outputBuilder.append("\nWelcome to the " + cabinet.getName() + " veterinary cabinet service.");
		outputBuilder.append("\nChoose an action:");
		outputBuilder.append("\n" +QUIT+". Exit.");
		outputBuilder.append("\n1. Display patients currently being cared for.");
		outputBuilder.append("\n2. Admit a patient.");
		outputBuilder.append("\n3. Checkout a patient.");
		outputBuilder.append("\n4. Simulate admission of a patient whose species' "
				+ "implementation is unknown to the server (without codebase enabled).");
		outputBuilder.append("\n5. Simulate admission of a patient whose species' "
				+ "implementation is known to the server (with codebase enabled).");
		outputBuilder.append("\n6. Simulate admission of X number of patients.");
		outputBuilder.append("\n7. Broadcast a message to the cabinet and connected clients.");

		System.out.println(outputBuilder);

		outputBuilder.setLength(0);

	}

	/**
     * The main method responsible for running the controller's user interface.
     */
	@Override
	public void run() {
		// set up client RMI service
		setUpClient();

		// set up default user input processor (by default, string input)
		setUpDefaultUserInputProcessor();

		// browse main menu
		browseMainMenu();
	}
	
	/**
     * Stops the application and performs cleanup.
     */
	private void stop() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Bye...");
			System.exit(0);
		}
	}

	/**
     * Sets up the RMI client for communication with the cabinet service.
     */
	private void setUpClient() {
		try {
			client.setUp();
		} catch (RemoteException e) {
			System.err.println("Error: Client cannot communicate with registry");
			e.printStackTrace();
		}
	}
	
	/**
     * Sets up the default user input processor (by default, string input).
     */
	private void setUpDefaultUserInputProcessor() {
		setUpUserInputProcessor(UserInputType.STRING);
	}

	/**
     * Sets up the user input processor based on the specified `UserInputType`.
     * This method is used to configure the type of input expected from the user.
     * 
     * @param type The `UserInputType` indicating the expected input type.
     */
	private void setUpUserInputProcessor(UserInputType type) {
		switch(type) {
		case STRING:
			processor = new StringProcessor(reader);
			break;
		
		case STRICTLY_POSITIVE_INTEGER:
			processor = new StrictlyPositiveIntegerProcessor(reader);
			break;
		}
	}
	
	/**
     * Browses the main menu of the veterinary cabinet service.
     * This method continuously displays the main menu, processes user input, and
     * performs corresponding actions based on the user's choice.
     */
	private void browseMainMenu() {
		String input = "";
		do {
			menu();
			input = (String) processor.process();
			processMainMenuUserInput(input);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while(!input.equals(QUIT));
	}

	/**
     * Processes user input for the main menu and performs corresponding actions.
     * 
     * @param input The user's menu choice input.
     */
	private void processMainMenuUserInput(String input) {
		switch(input) {
		case QUIT:
			stop();

		case "1":
			connectToCabinet();
			browseCabinetMenu();
			break;
		}		
	}

	/**
     * Connects to a veterinary cabinet by looking up the cabinet with the provided name.
     */
	private void connectToCabinet() {
		boolean foundCabinet = false;
		do {
			System.out.println("Please enter the name of a cabinet to connect: ");
			String input = (String) processor.process();
			try {
				cabinet = client.lookupCabinet(input);
				foundCabinet = true;
			} catch (RemoteException e) {
				System.err.println("Error: cannot connect to cabinet server.");
				e.printStackTrace();
				foundCabinet = false;
			} catch (NotBoundException e) {
				System.err.println("Error: cannot find cabinet named \"" + input + "\".");
				foundCabinet = false;
			}
		} while(!foundCabinet);
	}

	/**
     * Allows the user to browse the menu specific to the connected veterinary cabinet.
     * This method continuously displays the cabinet menu, takes user input, and processes
     * the selected action until the user chooses to exit.
     * 
     * @throws RemoteException If an RMI-related error occurs.
     * @throws IOException If an I/O error occurs during input.
     */
	private void browseCabinetMenu() {
		try {
			String input = "";
			do {
				cabinetMenu();
				input = (String) processor.process();
				processCabinetMenuUserInput(input);
				Thread.sleep(3000);
			} while(!input.equals(QUIT));

			System.out.println("Exiting the " + cabinet.getName() + " Veterinary Cabinet Service...");
			cabinet.unsubscribe(client);

		} catch (RemoteException e) {
			System.err.println("Error: cannot connect to cabinet server.");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
     * Processes the user input specific to the cabinet menu.
     * It takes the user's choice as input and performs the corresponding action.
     * 
     * @param input The user's choice from the cabinet menu.
     * @throws RemoteException If an RMI-related error occurs.
     * @throws IOException If an I/O error occurs during input.
     */
	private void processCabinetMenuUserInput(String input) throws IOException {
		switch(input) {
		case QUIT:
			System.out.println("Bye...");
			break;
		case "1":
			displayPatients();
			break;

		case "2":
			admitPatient();
			break;

		case "3":
			checkoutPatient();
			break;

		case "4":
			simulateNewPatientAdmissionWithoutCodebase();
			break;

		case "5":
			simulateNewPatientAdmissionWithCodeBase();
			break;

		case "6":
			simulateMultiplePatientsAdmission();
			break;

		case "7":
			broadcast();
			break;
		}
	}

	/**
     * Displays the list of patients currently being cared for by the veterinary cabinet.
     * This method retrieves the list of patients from the RMI client and prints their information.
     * 
     * @throws RemoteException If an RMI-related error occurs.
     */
	private void displayPatients() throws RemoteException {
		System.out.println("Patients in the " + cabinet.getName() + " Veterinary Clinic Service");
		client.displayPatients();
	}

	/**
     * Admits a new patient to the veterinary cabinet.
     * This method takes user input to gather information about the patient,
     * submits the patient to the cabinet, and prints the result.
     * 
     * @throws RemoteException If an RMI-related error occurs.
     */
	private void admitPatient() throws RemoteException {
		System.out.println("Please fill in the patient info for admission:");

		System.out.println("Name: ");
		String name = (String) processor.process();

		System.out.println("Owner: ");
		String ownerName = (String) processor.process();

		System.out.println("Species: ");
		String speciesName = (String) processor.process();

		System.out.println("Average Life Span: ");
		String speciesAverageLife = (String) processor.process();

		System.out.println("Race: ");
		String race = (String) processor.process();

		System.out.println("State: ");
		String state = (String) processor.process();

		
		boolean result = client.submitPatient(name, ownerName, speciesName, 
				Integer.parseInt(speciesAverageLife), race, state);
		
		if(result)
			System.out.println("Successfully submitted patient "
					+ client.getPatientByName(name).getInfos());
		else
			System.err.println("Error: failed to submit the requested patient, please try again.");
	}

	/**
     * Checks out a patient from the veterinary cabinet.
     * This method takes user input to specify the patient to be checked out,
     * performs the checkout, and prints the result.
     * 
     * @throws RemoteException If an RMI-related error occurs.
     */
	private void checkoutPatient() throws RemoteException {
		System.out.println("Please fill in the patient info for checkout:");

		System.out.println("Name: ");
		String name = (String) processor.process();
		IAnimal target = client.getPatientByName(name);
		boolean result = client.checkoutPatient(name);
		
		if (result)
			System.out.println("Successfully checked out patient "
					+ target.getInfos());
		else
			System.err.println("Error: failed to check out the requested patient, please try again.");
	}

	/**
     * Simulates the admission of a new patient with codebase disabled.
     */
	private void simulateNewPatientAdmissionWithoutCodebase() {
		System.out.println("Simulating admission of a new patient with codebase disabled...");
		
		// delete the content of the codebase folder
		CodebaseManager.emptyCodebaseFolder();
		
		IAnimal target;
		try {
			boolean result = client.submitPatient("Sneaky", "John", new Snake(), "Boa", 
					"Full belly");
			target = client.getPatientByName("Sneaky");
			
			if(result) {
				System.out.println("Successfully submitted patient "
						+ target.getInfos());
				client.checkoutPatient(target.getName());
			}
			else
				System.err.println("Error: failed to submit the requested patient, please try again.");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} finally {
			System.out.println("End of simulation");
		}
	}
	
	/**
     * Simulates the admission of a new patient with codebase enabled.
     */
	private void simulateNewPatientAdmissionWithCodeBase() throws RemoteException {
		if (CodebaseManager.codebaseIsEmpty())
			CodebaseManager.restoreCodebaseFolder();
		
		System.out.println("Simulating admission of a new patient with codebase enabled...");
		
		boolean result = client.submitPatient("Sneaky", "John", new Snake(), "Boa", 
				"Full belly");
		IAnimal target = client.getPatientByName("Sneaky");
		
		if(result) {
			System.out.println("Successfully submitted patient "
					+ target.getInfos());
			client.checkoutPatient(target.getName());
		}
		else
			System.err.println("Error: failed to submit the requested patient, please try again.");
		
		System.out.println("End of simulation");
	}

	/**
     * Simulates the admission of multiple patients to the cabinet.
     * The number of patients to be simulated is obtained from user input.
     * 
     * @throws RemoteException If an RMI-related error occurs.
     */
	private void simulateMultiplePatientsAdmission() throws RemoteException {
		System.out.println("Please choose the number of patients "
				+ "to simulate their admission: ");
		
		// set up user input processor to receive a strictly positive integer
		setUpUserInputProcessor(UserInputType.STRICTLY_POSITIVE_INTEGER);
		
		int numberOfPatients = (int) processor.process();
		
		System.out.println("Simulating admission of "+numberOfPatients+" patients...");
		for (int i=0; i<numberOfPatients; i++)
			client.submitPatient("Dog"+i, "Owner"+i, "Dog", 20, 
					"Bulldog"+i, "Good health");
		
		for (int i=0; i<numberOfPatients; i++)
			client.checkoutPatient("Dog"+i);
		
		// revert user input to default processor
		setUpDefaultUserInputProcessor();
		
		System.out.println("End of simulation");
	}

	/**
     * Broadcast a message to the cabinet and connected clients.
     * 
     * @throws RemoteException If an RMI-related error occurs.
     * @throws IOException     If an I/O error occurs when reading user input.
     */
	private void broadcast() throws RemoteException, IOException {
		System.out.println("Please enter the message you wish to broadcast:");
		cabinet.alert("Broadcast message: "+ reader.readLine());
	}
}