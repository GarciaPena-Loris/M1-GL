/**
 * The `client` package contains classes and interfaces related to the RMI client
 * responsible for interacting with the veterinary cabinet service.
 * 
 * This package provides the necessary components for the RMI client, including
 * connecting to the RMI registry, looking up the cabinet, and providing methods
 * for interacting with the cabinet service.
 * 
 * @author anonbnr
 */
package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import commons.ICabinet;
import commons.IClient;

/**
 * The `Client` class represents the RMI client for the veterinary cabinet service.
 * It is responsible for connecting to the RMI registry, looking up the cabinet service,
 * and providing methods for interacting with the cabinet.
 * 
 * This class extends `UnicastRemoteObject` to enable remote method invocation (RMI).
 * 
 * @author anonbnr
 */
public class Client extends UnicastRemoteObject implements IClient {
	/* ATTRIBUTES */
	private static final int RMI_REGISTRY_PORT = 1099;
	private Registry registry;
	private ICabinet cabinet;
	
	/* CONSTRUCTORS */
	/**
     * Constructs a new `Client` object.
     * This constructor initializes the client for RMI communication.
     *
     * @throws RemoteException if there is an issue with remote communication.
     */
	protected Client() throws RemoteException {
		
	}
	
	/* METHODS */
	/**
     * Sets up the client for communication with the RMI registry.
     * This method initializes the RMI registry connection and prepares
     * the client for interaction with the veterinary cabinet service.
     *
     * @throws RemoteException if there is an issue with remote communication.
     */
	@Override
	public void setUp() throws RemoteException {
		setUpRegistry();
		System.err.println("Client ready");
	}
	
	/**
     * Sets up the RMI registry connection.
     * This method locates the RMI registry on the specified port and sets up
     * the client to use it for cabinet lookup and interaction.
     *
     * @throws RemoteException if there is an issue with remote communication.
     */
	private void setUpRegistry() throws RemoteException {
		registry = LocateRegistry.getRegistry(RMI_REGISTRY_PORT);
		if (registry == null)
			System.err.println("Error: RMI registry not found at port " + RMI_REGISTRY_PORT);
	}
	
	/**
     * Looks up and connects to a veterinary cabinet by its key.
     * This method uses the RMI registry to look up the cabinet service
     * identified by its key and subscribes the client to it for updates.
     *
     * @param cabinetKey The key of the cabinet service to connect to.
     * @return The cabinet service interface for interaction.
     * @throws RemoteException if there is an issue with remote communication.
     * @throws NotBoundException if the cabinet is not bound in the registry.
     */
	@Override
	public ICabinet lookupCabinet(String cabinetKey) throws RemoteException, NotBoundException {
		cabinet = (ICabinet) registry.lookup(cabinetKey);
		cabinet.subscribe(this);
		return cabinet;
	}

	/**
     * Gets the reference to the connected cabinet.
     * This method returns the cabinet interface for interaction with the
     * connected veterinary cabinet service.
     *
     * @return The cabinet service interface.
     * @throws RemoteException if there is an issue with remote communication.
     */
	@Override
	public ICabinet getCabinet() throws RemoteException {
		return cabinet;
	}

	/**
     * Notifies the client about updates from the cabinet.
     * This method is invoked by the cabinet service to update the client
     * with new information or events.
     *
     * @param newState The new state or information from the cabinet.
     * @throws RemoteException if there is an issue with remote communication.
     */
	@Override
	public void update(String newState) throws RemoteException {
		System.out.println("Update: " + newState);
	}
}