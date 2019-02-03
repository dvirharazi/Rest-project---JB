package Job;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import JavaBeans.Coupon;
import JavaBeans.Customer;

public class CouponExpirationDailyJob implements Runnable {

	private boolean quit;
	private CouponsDBDAO couponsDAO;
	private CustomersDBDAO customersDAO;

	public CouponExpirationDailyJob() {
		quit = false;
		couponsDAO = new CouponsDBDAO();
		customersDAO = new CustomersDBDAO();
	}

	public void run() {

		while (!quit) {
			try {
				Thread.sleep(1000);

				if (isTimeToOperate(4, 0)) {
					ArrayList<Coupon> allCoupons = couponsDAO.getAllCoupons();
					for (Coupon coupon : allCoupons) {
						if (coupon.getEndDate().isBefore(LocalDate.now())) {
							ArrayList<Customer> allCustomers = customersDAO.getAllCustomers();
							for (Customer customer : allCustomers) {
								couponsDAO.deleteCouponPurchase(customer.getId(), coupon.getId());
								
							}
							couponsDAO.deleteCoupon(coupon.getId());
						}
					}
				}
			} catch (Exception e1) {
				System.out.println("Error: " + e1.getMessage());
			}
			// finally {
			// System.out.println("stop.");
			// quit = true;
			// }
		}

	}

	/**
	 * 
	 * @param hours
	 *            - hour that we get ,the function check if this hour like the
	 *            current hour.
	 * @param minutes
	 *            - minutes that we get ,the function check if this minutes like the
	 *            current minutes.
	 * @return
	 */
	public boolean isTimeToOperate(int hours, int minutes) {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		return (hours == hour && minutes == minute);
	}

	/**
	 * function that stop the daily job in the end.
	 */
	public void stop() {
		quit = true;
	}

}