package commons;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The IFollowUpFile interface represents a follow-up file associated with an animal
 * in the veterinary cabinet service. It defines methods to retrieve and set the state
 * of the animal in the follow-up file.
 * 
 * This interface extends the Remote interface, indicating that objects of this type
 * can be accessed remotely using RMI (Remote Method Invocation).
 * 
 * @author anonbnr
 */
public interface IFollowUpFile extends Remote {
	/**
     * Retrieves the state of the animal in the follow-up file.
     * 
     * @return The state of the animal in the follow-up file.
     * @throws RemoteException If a remote communication error occurs.
     */
	String getState() throws RemoteException;
	
	/**
     * Sets the state of the animal in the follow-up file.
     * 
     * @param state The new state to set for the animal in the follow-up file.
     * @throws RemoteException If a remote communication error occurs.
     */
	void setState(String state) throws RemoteException;
}
