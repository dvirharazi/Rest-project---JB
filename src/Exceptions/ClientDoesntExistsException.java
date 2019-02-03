package Exceptions ;
@SuppressWarnings("serial")
public class ClientDoesntExistsException extends Exception {
	public String getMessage() {
		return "Client Doesn't exists.";
	}
}
