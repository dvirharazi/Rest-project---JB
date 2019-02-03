package Exceptions;
@SuppressWarnings("serial")
public class ComapnyNameAlreadyExistsException extends Exception{
	public String getMessage() {
		return "Company name already exists.";
	}
}
