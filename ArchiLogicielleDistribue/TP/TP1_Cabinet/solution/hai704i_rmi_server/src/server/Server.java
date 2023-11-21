package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import commons.IAnimal;
import commons.ICabinet;
import commons.Species;

/**
 * The Server class is responsible for setting up and running the RMI server for the veterinary cabinet service.
 * It initializes the RMI registry, sets security policies, and binds the cabinet implementation to the registry.
 * 
 * @author anonbnr
 */
public class Server {
	/* ATTRIBUTES */
	private static final int RMI_REGISTRY_PORT = 1099;
	private static final String SECURITY_POLICY_PATH = "security/server.policy";
	private static final String CODEBASE_PATH = "file:///C:/Users/Bachar.RIMA/eclipse-workspace/hai704i_rmi_client/codebase/";
	private Registry registry;
	
	/* METHODS */
	/**
     * Sets up and starts the RMI server, including security settings, codebase, RMI registry, and cabinet initialization.
     * 
     * @throws RemoteException if an RMI-related error occurs.
     * @throws AlreadyBoundException if the cabinet is already bound in the registry.
     */
	public void setUp() throws RemoteException, AlreadyBoundException {
		setUpSecurity();
		setUpCodeBase();
		setUpRegistry();
		setUpCabinet();
		System.out.println("Codebase path: " + System.getProperty("java.rmi.server.codebase"));
		System.err.println("Server ready");
	}
	
	/**
     * Sets up the security manager and policy for the RMI server.
     */
	private void setUpSecurity() {
		System.setProperty("java.security.policy", SECURITY_POLICY_PATH);
		System.setSecurityManager(new SecurityManager());
	}
	
	/**
     * Sets up the codebase for RMI to locate remote classes.
     */
	private void setUpCodeBase() {
		System.setProperty("java.rmi.server.codebase", CODEBASE_PATH);
	}
	
	/**
     * Sets up the RMI registry on the specified port.
     * 
     * @throws RemoteException if an RMI-related error occurs.
     */
	private void setUpRegistry() throws RemoteException {
		registry = LocateRegistry.createRegistry(RMI_REGISTRY_PORT);
		if (registry == null)
			System.err.println("Error: RMI registry not found at port " + RMI_REGISTRY_PORT);
	}
	
	/**
     * Sets up the veterinary cabinet and binds it to the RMI registry.
     * 
     * @throws RemoteException if an RMI-related error occurs.
     * @throws AlreadyBoundException if the cabinet is already bound in the registry.
     */
	private void setUpCabinet() throws RemoteException, AlreadyBoundException {
		Species dog = new Species("Dog", 20);
		Species cat = new Species("Cat", 30);
		
		IAnimal rex = new AnimalImpl("Rex", "Paul", 
				dog, "Boxer", "In good shape");
		IAnimal fluffy = new AnimalImpl("Fluffy", "Jessy", cat, "Siamese", "In great shape");
		
		ICabinet cabinet = new CabinetImpl("Nirvana of Pets");
		cabinet.submitPatient(rex.getName(), rex.getOwnerName(), 
				rex.getSpecies().getName(), rex.getSpecies().getAverageLife(), 
				rex.getRace(), rex.getState());
		cabinet.submitPatient(fluffy.getName(), fluffy.getOwnerName(), 
				fluffy.getSpecies().getName(), fluffy.getSpecies().getAverageLife(), 
				fluffy.getRace(), fluffy.getState());
		
		registry.bind("Nirvana of Pets", cabinet);
	}
}
