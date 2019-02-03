package DAO;


import java.util.ArrayList;

import JavaBeans.Coupon;

public interface CouponsDAO {

	void addCoupon(Coupon coupon) throws Exception;
	
	void updateCoupon(Coupon coupon) throws Exception;
	
	void deleteCoupon(int couponID) throws Exception;
	
	Coupon getOneCoupon(int couponID) throws Exception;
	
	ArrayList<Coupon> getAllCoupons() throws Exception;
	
	void addCouponPurchase(int customerID, int couponID) throws Exception;
	
	void deleteCouponPurchase(int customerID, int couponID) throws Exception;
}