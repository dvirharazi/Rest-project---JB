package Exceptions;

@SuppressWarnings("serial")
public class CouponDoesntExistsException extends Exception{
	public String getMessage() {
		return "Coupon doesnt exists";
	}
}
