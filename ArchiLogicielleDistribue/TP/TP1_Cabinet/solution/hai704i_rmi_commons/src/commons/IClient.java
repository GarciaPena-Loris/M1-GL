package commons;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The IClient interface defines the methods that a client of the veterinary cabinet
 * service should implement. It includes methods for setting up the client, looking
 * up a cabinet, and receiving updates.
 * 
 * This interface extends the Remote interface, indicating that objects of this type
 * can be accessed remotely using RMI (Remote Method Invocation).
 * 
 * @author anonbnr
 */
public interface IClient extends Remote {
	/**
     * Sets up the client for communication with the veterinary cabinet service.
     * 
     * @throws RemoteException If a remote communication error occurs.
     */
	void setUp() throws RemoteException;
	
	/**
     * Looks up a cabinet in the RMI registry based on the provided cabinet key.
     * 
     * @param cabinetKey The key to look up the cabinet.
     * @return The cabinet if found in the registry.
     * @throws RemoteException If a remote communication error occurs.
     * @throws NotBoundException If the cabinet with the specified key is not bound in the registry.
     */
	ICabinet lookupCabinet(String cabinetkey) throws RemoteException, NotBoundException;
	
	/**
     * Retrieves the cabinet associated with the client.
     * 
     * @return The cabinet associated with the client.
     * @throws RemoteException If a remote communication error occurs.
     */
	ICabinet getCabinet() throws RemoteException;
	
	/**
     * Receives an update from the cabinet, indicating a change in the cabinet's state.
     * 
     * @param newState The new state information received from the cabinet.
     * @throws RemoteException If a remote communication error occurs.
     */
	void update(String newState) throws RemoteException;
	
	/**
     * Retrieves a patient by name from the cabinet's list of patients.
     * 
     * @param name The name of the patient to retrieve.
     * @return The patient with the specified name or null if not found.
     * @throws RemoteException If a remote communication error occurs.
     */
	default IAnimal getPatientByName(String name) throws RemoteException {
		return getCabinet().getPatientByName(name);
	}
	
	/**
     * Submits a new patient to the cabinet with the provided information.
     * 
     * @param name The name of the patient.
     * @param ownerName The name of the patient's owner.
     * @param speciesName The name of the patient's species.
     * @param speciesAverageLife The average life span of the patient's species.
     * @param race The race of the patient.
     * @param state The state of the patient.
     * @return True if the patient submission was successful, false otherwise.
     * @throws RemoteException If a remote communication error occurs.
     */
	default boolean submitPatient(String name, String ownerName, 
			String speciesName, int speciesAverageLife, 
			String race, String state) throws RemoteException {
		return getCabinet().submitPatient(name, ownerName, 
				speciesName, speciesAverageLife, race, state);
	}
	
	/**
     * Submits a new patient to the cabinet with the provided information.
     * 
     * @param name The name of the patient.
     * @param ownerName The name of the patient's owner.
     * @param species The species of the patient.
     * @param race The race of the patient.
     * @param state The state of the patient.
     * @return True if the patient submission was successful, false otherwise.
     * @throws RemoteException If a remote communication error occurs.
     * @see Species
     */
	default boolean submitPatient(String name, String ownerName, Species species, 
			String race, String state) throws RemoteException {
		return getCabinet().submitPatient(name, ownerName, species, race, state);
	}
	
	/**
     * Checks out a patient from the cabinet using the patient's name.
     * 
     * @param name The name of the patient to check out.
     * @return True if the checkout was successful, false otherwise.
     * @throws RemoteException If a remote communication error occurs.
     */
	default boolean checkoutPatient(String name) throws RemoteException {
		return getCabinet().checkoutPatient(name);
	}
	
	/**
     * Displays a list of patients currently being cared for in the cabinet.
     * 
     * @throws RemoteException If a remote communication error occurs.
     */
	default void displayPatients() throws RemoteException {
		for (IAnimal patient: getCabinet().getPatients())
			System.out.println(patient.getInfos());
	}
}
