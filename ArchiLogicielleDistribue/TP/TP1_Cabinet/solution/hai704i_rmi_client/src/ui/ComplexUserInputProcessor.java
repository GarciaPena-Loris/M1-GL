package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/**
 * The `ComplexUserInputProcessor` class is an abstract base class for processing complex
 * user inputs. Subclasses of this class define specific input processing logic.
 * 
 * This class is designed to facilitate the processing of user input that requires
 * more complex validation or parsing beyond simple types like strings or integers.
 * Subclasses should implement abstract methods to customize the behavior for a
 * specific type of input.
 * 
 * @param <T> The generic type parameter representing the processed input type.
 * 
 * @author anonbnr
 */
public abstract class ComplexUserInputProcessor<T> {
	/* ATTRIBUTES */
	protected BufferedReader reader;       // A buffered reader for reading user input.
    protected String message;              // The message displayed to the user before input.
    protected Predicate<String> isValid;   // Predicate to validate user input.
    protected Method parser;               // Method used to parse user input.
    protected T parameter;                 // The processed user input.
	
	/* CONSTRUCTOR */
    /**
     * Constructs a `ComplexUserInputProcessor` object with the given `BufferedReader`.
     * It initializes the message, validity criterion, and parser for processing user input.
     *
     * @param reader The `BufferedReader` used for reading user input.
     */
	public ComplexUserInputProcessor(BufferedReader reader) {
		this.reader = reader;
		setMessage();
		setValidityCriterion();
		setParser();
	}
	
	/* METHODS */
	// Abstract methods
	/**
     * Sets the message displayed to the user before prompting for input.
     * Subclasses should override this method to provide a specific message.
     */
	protected abstract void setMessage();
	
	/**
     * Sets the validity criterion for user input as a `Predicate<String>`.
     * Subclasses should override this method to define their validation logic.
     */
	protected abstract void setValidityCriterion();
	
	/**
     * Sets the parser method used to convert user input into the desired type.
     * Subclasses should override this method to specify their parsing logic.
     */
	protected abstract void setParser();
	
	// Getters
	/**
     * Retrieves the processed user input.
     *
     * @return The processed user input of type `T`.
     */
	public T getParameter() {
		return parameter;
	}
	
	// Processing user input
	/**
     * Processes user input, displaying a message and validating the input.
     * If the input is valid, it parses and returns the processed value.
     *
     * @return The processed user input of type `T`.
     */
	public T process() {
		System.out.println(message);
		try {
			String userInput = reader.readLine();
			while (!isValid.test(userInput)) {
				System.err.println("Sorry, wrong input. Please try again.");
				System.out.println(message);
				userInput = reader.readLine();
			}
			if (Modifier.isStatic(parser.getModifiers()))
				parameter = (T) parser.invoke(null, userInput);
			else
				parameter = (T) parser.invoke(userInput);
		} catch (SecurityException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException e) {
			System.err.println("Error: Cannot parse user input");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: Cannot read input from input reader");
			e.printStackTrace();
		}
		
		return parameter;
	}
}
