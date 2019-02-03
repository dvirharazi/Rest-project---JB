package Exceptions;

@SuppressWarnings("serial")
public class OutOfStockException extends Exception {
	public String getMessage() {
		return "Out of stock.";
	}
}
