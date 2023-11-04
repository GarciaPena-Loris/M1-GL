package hai704i.tp2.demo.exceptions;

public class EmployeeAlreadyExistsException extends Exception {
	public EmployeeAlreadyExistsException() {
	
	}
	
	public EmployeeAlreadyExistsException(String message) {
		super(message);
	}
}
