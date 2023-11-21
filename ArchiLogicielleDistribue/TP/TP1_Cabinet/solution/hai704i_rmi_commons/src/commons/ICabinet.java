package commons;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

/**
 * The ICabinet interface represents a veterinary cabinet service. It defines methods
 * for managing patients, clients, and notifications related to the cabinet.
 * 
 * This interface extends the Remote interface, indicating that objects of this type
 * can be accessed remotely using RMI (Remote Method Invocation).
 * 
 * @author anonbnr
 */
public interface ICabinet extends Remote {
	/**
     * Retrieves the name of the cabinet.
     * 
     * @return The name of the cabinet.
     * @throws RemoteException If a remote communication error occurs.
     */
	String getName() throws RemoteException;
	
	/**
     * Retrieves a list of patients currently being cared for in the cabinet.
     * 
     * @return A list of patients.
     * @throws RemoteException If a remote communication error occurs.
     */
	List<IAnimal> getPatients() throws RemoteException;
	
	/**
     * Retrieves a list of clients connected to the cabinet.
     * 
     * @return A list of clients.
     * @throws RemoteException If a remote communication error occurs.
     */
	List<IClient> getClients() throws RemoteException;
	
	/**
     * Retrieves a list of notification thresholds used for monitoring the number of patients.
     * 
     * @return A list of notification thresholds.
     * @throws RemoteException If a remote communication error occurs.
     */
	List<Integer> getNotificationThresholds() throws RemoteException;
	
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
	boolean submitPatient(String name, String ownerName, String speciesName, int speciesAverageLife, String race, String state) throws RemoteException;
	
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
     */
	boolean submitPatient(String name, String owerName, Species species, String race, String state) throws RemoteException;
	
	/**
     * Checks out a patient from the cabinet using the patient's name.
     * 
     * @param name The name of the patient to check out.
     * @return True if the checkout was successful, false otherwise.
     * @throws RemoteException If a remote communication error occurs.
     */
	boolean checkoutPatient(String name) throws RemoteException;
	
	/**
     * Retrieves a patient by name from the cabinet's list of patients.
     * 
     * @param name The name of the patient to retrieve.
     * @return The patient with the specified name or null if not found.
     * @throws RemoteException If a remote communication error occurs.
     */
	default IAnimal getPatientByName(String name) throws RemoteException {
		Optional<IAnimal> patient = getPatients()
		.stream()
		.filter(p -> {
			try {
				return p.getName().equals(name);
			} catch(RemoteException e) {
				e.printStackTrace();
				return false;
			}
		})
		.findFirst();
		
		if (patient.isPresent())
			return patient.get();
		else
			return null;
	}
	
	/**
     * Subscribes a client to the cabinet.
     * 
     * @param client The client to subscribe.
     * @return True if the subscription was successful, false otherwise.
     * @throws RemoteException If a remote communication error occurs.
     */
	default boolean subscribe(IClient client) throws RemoteException {
		return !getClients().contains(client) 
				&& getClients().add(client);
	}
	
	/**
     * Unsubscribes a client from the cabinet.
     * 
     * @param client The client to unsubscribe.
     * @return True if the unsubscription was successful, false otherwise.
     * @throws RemoteException If a remote communication error occurs.
     */
	default boolean unsubscribe(IClient client) throws RemoteException {
		return getClients().contains(client)
				&& getClients().remove(client);
	}
	
	/**
     * Broadcasts a message to all connected clients.
     * 
     * @param newState The message to broadcast.
     * @throws RemoteException If a remote communication error occurs.
     */
	default void alert(String newState) throws RemoteException {
		System.out.println(newState);
		for (IClient client: getClients())
			client.update(newState);
	}
	
	/**
     * Checks for updates in the number of patients and sends notifications to clients if
     * the number of patients crosses certain thresholds.
     * 
     * @param oldSize The previous number of patients.
     * @throws RemoteException If a remote communication error occurs.
     */
	default void checkForUpdates(int oldSize) throws RemoteException {
		int newSize = getPatients().size();
		
		if (getNotificationThresholds().contains(newSize)) {
			if (newSize > oldSize)
				alert("Patients number increased to " + newSize);
			else
				alert("Patients number decreased to " + newSize);
		}
	}
}
