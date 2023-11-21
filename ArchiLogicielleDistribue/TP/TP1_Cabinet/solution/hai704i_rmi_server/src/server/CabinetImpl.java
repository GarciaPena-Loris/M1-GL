package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commons.IAnimal;
import commons.ICabinet;
import commons.IClient;
import commons.Species;

/**
 * The CabinetImpl class represents the implementation of the ICabinet interface in the veterinary cabinet service.
 * It provides functionality for managing the cabinet, including patient submission and client notifications.
 * 
 * @author anonbnr
 */
public class CabinetImpl extends UnicastRemoteObject implements ICabinet {
	/* ATTRIBUTES */
	private String name;
	private List<IAnimal> patients = new ArrayList<>();
	private List<IClient> clients = new ArrayList<>();
	private List<Integer> notificationThresholds = Arrays.asList(2, 10, 100, 500, 1000);
	
	/* CONSTRUCTORS */
	/**
     * Constructs a new CabinetImpl instance with the specified name.
     * 
     * @param name The name of the veterinary cabinet.
     * @throws RemoteException if an RMI-related error occurs.
     */
	public CabinetImpl(String name) throws RemoteException {
		this.name = name;
	}
	
	/**
     * Constructs a new CabinetImpl instance.
     * 
     * @throws RemoteException if an RMI-related error occurs.
     */
	public CabinetImpl() throws RemoteException {
		
	}

	/* METHODS */
	/**
     * {@inheritDoc}
     */
	@Override
	public String getName() throws RemoteException {
		return name;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public List<IAnimal> getPatients() throws RemoteException {
		return patients;
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public List<IClient> getClients() throws RemoteException {
		return clients;
	}
	
	/**
     * {@inheritDoc}
     */
	@Override
	public List<Integer> getNotificationThresholds() throws RemoteException {
		return notificationThresholds;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public boolean submitPatient(String name, String ownerName, 
			String speciesName, int speciesAverageLife, 
			String race, String state) 
					throws RemoteException {
		IAnimal patient = new AnimalImpl(name, ownerName, speciesName, speciesAverageLife, race, state);
		int oldSize = patients.size();
		boolean result = patients.add(patient);
		
		if(result) {
			System.out.println("Successfully submitted patient " + patients.get(patients.size() - 1).getInfos());
			checkForUpdates(oldSize);
		}
		else
			System.err.println("Error: failed to submit patient " + patient.getInfos());
		return result;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public boolean submitPatient(String name, String ownerName, Species species, 
			String race, String state) throws RemoteException {
		IAnimal patient = new AnimalImpl(name, ownerName, species, race, state);
		int oldSize = patients.size();
		boolean result = patients.add(patient);
		
		if(result) {
			System.out.println("Successfully submitted patient " + patient.getInfos());
			checkForUpdates(oldSize);
		}
		else
			System.err.println("Error: failed to submit patient " + patient.getInfos());
		return result;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public boolean checkoutPatient(String name) throws RemoteException {
		IAnimal patient = getPatientByName(name);
		
		if (patient == null) {
			System.err.println("Error: patient " + name + " is not found");
			return false;
		}
		
		int oldSize = patients.size();
		boolean result = patients.remove(patient);
		
		if (result) {
			System.out.println("Successfully checked out patient " + patient.getInfos());
			checkForUpdates(oldSize);
		}
		else
			System.err.println("Error: failed to check out patient named " + name);
		
		return result;
	}
}
