package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import commons.IAnimal;
import commons.IFollowUpFile;
import commons.Species;

/**
 * The AnimalImpl class represents an implementation of the IAnimal interface in the veterinary cabinet service.
 * It provides information about a specific animal, including its name, owner's name, species, race, and follow-up file.
 * It also allows for retrieving and updating the animal's state.
 * 
 * @author anonbnr
 */
public class AnimalImpl extends UnicastRemoteObject implements IAnimal{
	/* ATTRIBUTES */
	private String name;
	private String ownerName;
	private Species species;
	private String race;
	private IFollowUpFile followUpFile;

	/* CONSTRUCTORS */
	/**
     * Constructs a new AnimalImpl instance.
     * 
     * @throws RemoteException if an RMI-related error occurs.
     */
	protected AnimalImpl() throws RemoteException {

	}
	
	/**
     * Constructs a new AnimalImpl instance with specified attributes.
     * 
     * @param name             The name of the animal.
     * @param ownerName        The name of the owner of the animal.
     * @param speciesName      The name of the species of the animal.
     * @param speciesAverageLife The average life span of the species.
     * @param race             The race of the animal.
     * @param state            The initial state of the animal's follow-up file.
     * @throws RemoteException if an RMI-related error occurs.
     */
	protected AnimalImpl(String name, String ownerName, String speciesName,
			int speciesAverageLife, String race, String state)
					throws RemoteException {
		this.name = name;
		this.ownerName = ownerName;
		this.species = new Species(speciesName, speciesAverageLife);
		this.race = race;
		this.followUpFile = new FollowUpFileImpl(state);
	}
	
	/**
     * Constructs a new AnimalImpl instance with specified attributes.
     * 
     * @param name      The name of the animal.
     * @param ownerName The name of the owner of the animal.
     * @param species   The species of the animal.
     * @param race      The race of the animal.
     * @param state     The initial state of the animal's follow-up file.
     * @throws RemoteException if an RMI-related error occurs.
     * @see Species
     */
	protected AnimalImpl(String name, String ownerName, Species species,
			String race, String state) throws RemoteException {
		this.name = name;
		this.ownerName = ownerName;
		this.species = species;
		this.race = race;
		this.followUpFile = new FollowUpFileImpl(state);
	}

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
	public String getOwnerName() throws RemoteException {
		return ownerName;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public Species getSpecies() throws RemoteException {
		return species;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public String getRace() throws RemoteException {
		return race;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public IFollowUpFile getFollowUpFile() throws RemoteException {
		return followUpFile;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public String getState() throws RemoteException {
		return followUpFile.getState();
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void setState(String state) throws RemoteException {
		followUpFile.setState(state);
	}
}
