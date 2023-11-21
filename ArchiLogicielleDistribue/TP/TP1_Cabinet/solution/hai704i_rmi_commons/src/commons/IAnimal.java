package commons;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The IAnimal interface represents an animal in the veterinary cabinet service.
 * It defines methods to retrieve information about an animal, such as its name,
 * owner's name, species, race, follow-up file, and state.
 * 
 * This interface extends the Remote interface, indicating that objects of this
 * type can be accessed remotely using RMI (Remote Method Invocation).
 * 
 * @author anonbnr
 */
public interface IAnimal extends Remote {
	/**
     * Retrieves the name of the animal.
     * 
     * @return The name of the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	String getName() throws RemoteException;
	
	/**
     * Retrieves the owner's name of the animal.
     * 
     * @return The owner's name of the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	String getOwnerName() throws RemoteException;
	
	/**
     * Retrieves the species of the animal.
     * 
     * @return The species of the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	Species getSpecies() throws RemoteException;
	
	/**
     * Retrieves the race of the animal.
     * 
     * @return The race of the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	String getRace() throws RemoteException;
	
	/**
     * Retrieves the follow-up file associated with the animal.
     * 
     * @return The follow-up file of the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	IFollowUpFile getFollowUpFile() throws RemoteException;
	
	/**
     * Retrieves the state of the animal.
     * 
     * @return The state of the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	String getState() throws RemoteException;
	
	/**
     * sets the state of the animal.
     * 
     * @return The new state of the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	void setState(String state) throws RemoteException;
	
	/**
     * Retrieves the full name of the animal, including its name and owner's name.
     * 
     * @return The full name of the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	default String getFullName() throws RemoteException {
		return getName() + " (owned by " + getOwnerName() + ")";
	}
	
	/**
     * Retrieves detailed information about the animal, including its full name,
     * species, race, and follow-up file.
     * 
     * @return Detailed information about the animal.
     * @throws RemoteException If a remote communication error occurs.
     */
	default String getInfos() throws RemoteException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("(Full name: " + getFullName());
		buffer.append(", Species: " + getSpecies());
		buffer.append(", Race: " + getRace());
		buffer.append(", Follow-up: " + getFollowUpFile() + ")");
		
		return buffer.toString();
	}
}
