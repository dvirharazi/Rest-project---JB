package Exceptions;

@SuppressWarnings("serial")
public class WrongDetailsException extends Exception{
	public String getMessage() {
		return "Wrong details.";
	}
}
