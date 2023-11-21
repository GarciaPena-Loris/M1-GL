package commons;

import java.io.Serializable;

/**
 * The Species class represents a species of animals in the veterinary cabinet system.
 * It provides information about the species, including its name and average life span.
 * This class implements the Serializable interface to support object serialization.
 * 
 * Serializable allows objects of this class to be converted into a byte stream for
 * network communication or persistence and then reconstituted back into objects
 * at the receiving end.
 * 
 * This class serves as a base class for specific animal species implementations.
 * 
 * @author anonbnr
 */
public class Species implements Serializable {
	/* ATTRIBUTES */
	private String name;
	private int averageLife;
	
	/* CONSTRUCTORS */
	/**
     * Default constructor for the Species class.
     * Creates a new instance with default attribute values.
     */
	public Species() {
		
	}
	
	/**
     * Constructor for the Species class.
     * Creates a new instance with the provided name and average life span.
     * 
     * @param name The name of the species.
     * @param averageLife The average life span of the species.
     */
	public Species(String name, int averageLife) {
		this.name = name;
		this.averageLife = averageLife;
	}
	
	/* METHODS */
	/**
     * Gets the name of the species.
     * 
     * @return The name of the species.
     */
	public String getName() {
		return name;
	}
	
	/**
     * Gets the average life span of the species.
     * 
     * @return The average life span in years.
     */
	public int getAverageLife() {
		return averageLife;
	}
	
	/**
     * Returns a string representation of the species.
     * The format is "(Name: {name}, Average Life: {averageLife})".
     * 
     * @return A string representation of the species.
     */
	public String toString() {
		return "(Name: "+ name + ", Average Life: " + averageLife + ")";
	}
}
