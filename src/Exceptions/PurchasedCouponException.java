package Exceptions;

@SuppressWarnings("serial")
public class PurchasedCouponException extends Exception {
	public String getMessage() {
		return "Coupon already purchase by this client.";
	}
}
