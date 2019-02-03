package Exceptions;

@SuppressWarnings("serial")
public class CouponExpiredException extends Exception {
	public String getMessage() {
		return "Coupon expired";
	}
}
