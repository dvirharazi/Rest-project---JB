package Facades;

import java.time.LocalDate;

import java.util.ArrayList;
import DAO.CompaniesDBDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import Exceptions.CategoryDoesntExistsExeption;
import Exceptions.ClientDoesntExistsException;
import Exceptions.CouponExpiredException;
import Exceptions.OutOfStockException;
import Exceptions.PurchasedCouponException;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;

public class CustomerFacade extends ClientFacade {

	private int customerID;
	
	

	public int getCustomerID() {
		return customerID;
	}
//
//	public void setCustomerID(int customerID) {
//		this.customerID = customerID;
//	}

	public CustomerFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}

	public CustomerFacade(int customerID) {
		this.customerID = customerID;
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}
	/**
	 * customer can login only if his email and password appears in database. 
	 */
	public boolean login(String email, String password) throws Exception {

		ArrayList<Customer> customerList = customersDAO.getAllCustomers();

		int i = 0;
		int j = customerList.size();

		for (Customer customer : customerList) {
			if (!(email.equals(customer.getEmail()) && password.equals(customer.getPassword()))) {
				i++;
			} else {
				this.customerID = customer.getId();
				return true;
			}
		}
		if (i == j) {
			throw new ClientDoesntExistsException();
		}
		return false;
	}
	/**
	 * purchase coupon only if is amount more the 0 and is date not expired.
	 * if coupon purchase is amount decrease by 1 in database .
	 * @param coupon - coupon object.
	 * @throws Exception
	 */
	public void purchaseCoupon(Coupon coupon) throws Exception  {

		int newAmount = coupon.getAmount();

		if (coupon.getAmount() > 0 && coupon.getEndDate().isAfter(LocalDate.now())) {
			try {
				couponsDAO.addCouponPurchase(customerID, coupon.getId());
			} catch (PurchasedCouponException e) {
				System.out.println("Error: " + e.getMessage());
			}
			newAmount = coupon.getAmount() - 1;
			coupon.setAmount(newAmount);
			couponsDAO.updateCoupon(coupon);
		} else if (newAmount <= 0) {
			throw new OutOfStockException();
		} if (coupon.getEndDate().isBefore(LocalDate.now())) {
			throw new CouponExpiredException();
		}
	}
	/**
	 * get coupons of the customer that login.
	 * @return customer coupons.
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCustomerCoupons() throws Exception {
		Customer customer = customersDAO.getOneCustomer(customerID);
		return customer.getCoupon();
	}
	public Coupon getOneCustomerCoupon(int id) throws Exception {
		Coupon coupon = couponsDAO.getOneCoupon(id);
//		Customer customer = customersDAO.getOneCustomer(customerID);
		return coupon;
	}
	
	/**
	 * get the coupons of specific type that belongs the customer who lohin.
	 * @param couponType - enum couponType.
	 * @return coupons of the specific category.
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCustomerCoupons(CouponType couponType) throws Exception {
		Customer customer = customersDAO.getOneCustomer(customerID);

		ArrayList<Coupon> allCoupons = customer.getCoupon();
		ArrayList<Coupon> couponsByCategory = new ArrayList<>();

		for (Coupon coupon : allCoupons) {
			if (coupon.getType().equals(couponType)) {
				couponsByCategory.add(coupon);
			}
		}
		if (couponsByCategory.size() == 0) {
			throw new CategoryDoesntExistsExeption();
		}

		return couponsByCategory;
	}
	/**
	 * get all coupons that have price that lower of the max price that belong the customer who logged-in.
	 * @param maxPrice - max price.
	 * @return all coupons their price lower then max price.
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws Exception {
		Customer customer = customersDAO.getOneCustomer(customerID);

		ArrayList<Coupon> allCoupons = customer.getCoupon();
		ArrayList<Coupon> couponsByPrice = new ArrayList<>();

		for (Coupon coupon : allCoupons) {
			if (coupon.getPrice() <= maxPrice) {
				couponsByPrice.add(coupon);
			}
		}
		return couponsByPrice;
	}
	/**
	 * get customer details that login.
	 * @return customer.
	 * @throws Exception
	 */
	public Customer getCustomerDetails() throws Exception {
		Customer customer = customersDAO.getOneCustomer(customerID);
		return customer;
	}
	public ArrayList<Coupon> getAllCoupons() throws Exception{
		ArrayList<Coupon> allCoupons = couponsDAO.getAllCoupons();
		return allCoupons;
		
	}
	public Coupon getOneCoupon(int id) throws Exception{
		Coupon coupon = couponsDAO.getOneCoupon(id);
		return coupon;
		
	}
	
}