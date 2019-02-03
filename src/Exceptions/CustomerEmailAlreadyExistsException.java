package Exceptions;

@SuppressWarnings("serial")
public class CustomerEmailAlreadyExistsException extends Exception{
	public String getMessage() {
		return "Customer email already exists";
	}
}
