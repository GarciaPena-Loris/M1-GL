package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import commons.IFollowUpFile;

/**
 * The FollowUpFileImpl class represents an implementation of the IFollowUpFile interface in the veterinary cabinet service.
 * It provides functionality for managing the follow-up state of an animal.
 * 
 * @author anonbnr
 */
public class FollowUpFileImpl extends UnicastRemoteObject implements IFollowUpFile {
	/* ATTRIBUTES */
	private String state;
	
	/**
     * Constructs a new FollowUpFileImpl instance.
     * 
     * @throws RemoteException if an RMI-related error occurs.
     */
	protected FollowUpFileImpl() throws RemoteException {
		
	}

	/**
     * Constructs a new FollowUpFileImpl instance with the specified initial state.
     * 
     * @param state The initial state of the follow-up file.
     * @throws RemoteException if an RMI-related error occurs.
     */
	public FollowUpFileImpl(String state) throws RemoteException {
		this.state = state;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public String getState() throws RemoteException {
		return state;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void setState(String state) throws RemoteException {
		this.state = state;	
	}
	
	/**
     * Returns a string representation of the animal's state in the follow-up file.
     * 
     * @return The string representation of the animal's state in the follow-up file.
     */
	@Override
	public String toString() {
		return state;
	}
}
