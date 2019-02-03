package Exceptions;
@SuppressWarnings("serial")
public class CompanyEmailAlreadyExistsException extends Exception {
	public String getMessage() {
		return "Company email already exists";
	}
	
}
