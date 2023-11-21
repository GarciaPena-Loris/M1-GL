/**
 * The `ui` package contains classes related to the user interface of the RMI client
 * for the veterinary cabinet service.
 * 
 * @see ui.CabinetController
 * @see ui.ComplexUserInputProcessor
 * 
 * @author anonbnr
 */
package ui;

/**
 * The `AbstractController` class is an abstract base class for implementing controllers
 * in the veterinary cabinet service. It provides common attributes and methods for
 * managing user interface logic.
 * 
 * @see ui.CabinetController
 * @see ui.ComplexUserInputProcessor
 * 
 * @author anonbnr
 */
public abstract class AbstractController {
	/* ATTRIBUTES */
	/**
     * A `StringBuilder` used to accumulate and build output messages for display.
     */
	protected StringBuilder outputBuilder = new StringBuilder();
	
	/**
     * The user input processor associated with this controller.
     */
	protected ComplexUserInputProcessor<?> processor;
	
	/**
     * A constant string representing the "Quit" option in the controller's menu.
     */
	protected static final String QUIT = "0";
	
	/* METHODS */
	// Getters
	/**
     * Get the `StringBuilder` used to accumulate and build output messages.
     *
     * @return The `StringBuilder` for output messages.
     */
	public StringBuilder getOutputBuilder() {
		return outputBuilder;
	}
	
	// Abstract methods
	/**
     * Defines the menu structure or options available in the controller's user interface.
     */
	protected abstract void menu();
	
	/**
     * The main method responsible for running the controller's user interface.
     */
	public abstract void run();
}
