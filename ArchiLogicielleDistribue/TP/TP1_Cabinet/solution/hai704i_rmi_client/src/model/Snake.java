package model;

import commons.Species;

/**
 * The Snake class is an implementation of the Species class for representing
 * snake species. It provides species-specific information.
 */
public class Snake extends Species {
	/**
     * Initializes a new instance of the Snake class with the species name "Snake"
     * and an average life span of 80 years.
     */
	public Snake() {
		super("Snake", 80);
	}
}
