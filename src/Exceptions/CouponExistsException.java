package Exceptions;

@SuppressWarnings("serial")
public class CouponExistsException extends Exception {
	public String getMessage() {
		return "Coupon already exists ";
	}
}
