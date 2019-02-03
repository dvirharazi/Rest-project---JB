package Exceptions;

@SuppressWarnings("serial")
public class CustomerDoesntExistsException extends Exception{
	public String getMessage() {
		return "Customer doesnt exists.";
	}
}
