package JavaBeans;
/**
 * this enum class , here we define the types of coupons.
 * @author haraz
 *
 */
public enum CouponType {

	CLOTHING,
	VICATION,
	SCHOOL_EQUIPMENT,
	FOOD,
	ELECTRICITY;


	public static CouponType getCouponType (String type) {
		switch (type) {
		case "CLOTHING": return CouponType.CLOTHING;
		case "VICATION": return CouponType.VICATION;
		case "SCHOOL_EQUIPMENT": return CouponType.SCHOOL_EQUIPMENT;
		case "FOOD": return CouponType.FOOD;
		default: return CouponType.ELECTRICITY;

		}
	}
}