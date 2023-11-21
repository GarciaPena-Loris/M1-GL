package ui;

import java.io.BufferedReader;

/**
 * The `StrictlyPositiveIntegerProcessor` class is a user input processor for handling
 * strictly positive integer inputs. It checks for valid integer inputs and provides
 * a parsing method to convert the input to an integer.
 * 
 * This class is a specific implementation of the `ComplexUserInputProcessor` for processing
 * strictly positive integer inputs. It ensures that user input is a valid positive integer
 * and provides a parsing method to convert the input to an integer.
 * 
 * @author anonbnr
 */
public class StrictlyPositiveIntegerProcessor extends ComplexUserInputProcessor<Integer>{

	/**
     * Constructs a `StrictlyPositiveIntegerProcessor` object with the given `BufferedReader`.
     * It initializes the message, validity criterion, and parser for processing positive integer input.
     *
     * @param reader The `BufferedReader` used for reading user input.
     */
	public StrictlyPositiveIntegerProcessor(BufferedReader reader) {
		super(reader);
	}

	/**
     * Sets the message displayed to the user before prompting for input.
     * This method specifies the message to request a strictly positive integer input.
     */
	@Override
	protected void setMessage() {
		message = "Please enter a strictly positive integer";
	}

	/**
     * Sets the validity criterion for user input as a `Predicate<String>`.
     * The `StrictlyPositiveIntegerProcessor` checks that the input is a valid strictly positive integer.
     */
	@Override
	protected void setValidityCriterion() {
		isValid = str -> {
			try {
				int value = Integer.parseInt(str);
				return value > 0;
			} catch (NumberFormatException e) {
				return false;
			}
		};
	}

	/**
     * Sets the parser method used to convert user input into an integer.
     * The `StrictlyPositiveIntegerProcessor` class uses the `parseInt` method from the `Integer` class
     * to parse and convert the input string into an integer.
     */
	@Override
	protected void setParser() {
		try {
			parser = Integer.class.getMethod("parseInt", String.class);
		} catch (SecurityException | NoSuchMethodException e) {
			
			e.printStackTrace();
		}
	}
}
