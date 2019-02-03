package Exceptions;
@SuppressWarnings("serial")
public class CategoryDoesntExistsExeption extends Exception {
	public String getMessage() {
		return "Coupon Category Doesn't exists in this client.";
	}
}
