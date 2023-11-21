package ui;

import java.io.BufferedReader;

/**
 * The `StringProcessor` class is a user input processor for handling string inputs.
 * It checks for non-empty strings and provides a parsing method to convert the input
 * to a string.
 * 
 * This class is a specific implementation of the `ComplexUserInputProcessor` for processing
 * string inputs. It ensures that user input is a non-empty string and provides a simple
 * parsing method that returns the input as a string.
 * 
 * @author anonbnr
 */
public class StringProcessor extends ComplexUserInputProcessor<String> {

	/* CONSTRUCTOR */
	/**
     * Constructs a `StringProcessor` object with the given `BufferedReader`.
     * It initializes the message, validity criterion, and parser for processing string input.
     *
     * @param reader The `BufferedReader` used for reading user input.
     */
	public StringProcessor(BufferedReader reader) {
		super(reader);
	}

	/* METHODS */
	/**
     * Sets the message displayed to the user before prompting for input.
     * This method specifies the message to request a non-empty string input.
     */
	@Override
	protected void setMessage() {
		message = "Please enter a non-empty String";
	}

	/**
     * Sets the validity criterion for user input as a `Predicate<String>`.
     * The `StringProcessor` checks that the input is a non-empty string.
     */
	@Override
	protected void setValidityCriterion() {
		isValid = str -> {
			return !str.isEmpty();
		};
	}

	/**
     * Sets the parser method used to convert user input into a string.
     * The `StringProcessor` class uses the `toString` method from the `Object` class,
     * as strings do not require complex parsing.
     */
	@Override
	protected void setParser() {
		try {
			parser = Object.class.getMethod("toString");
		} catch (NoSuchMethodException e) {
			System.err.println("Error: cannot set the user input parser");
		} catch (SecurityException e) {
			System.err.println("Error: cannot set the user input parser");
			e.printStackTrace();
		}
	}

}
