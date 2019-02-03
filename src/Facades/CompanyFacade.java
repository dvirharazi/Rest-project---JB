package Facades;

import java.util.ArrayList;
import DAO.CompaniesDBDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import Exceptions.CategoryDoesntExistsExeption;
import Exceptions.ClientDoesntExistsException;
import Exceptions.CouponDoesntExistsException;
import Exceptions.CouponExistsException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;

public class CompanyFacade extends ClientFacade{

	private int companyID;
	/**
	 * companyfacade ctor.
	 */
	public CompanyFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
//		setCompanyID(companyID);
	}
	/**
	 * get company id.
	 * @return company id .
	 */
	public int getCompanyID() {
		return this.companyID;
	}
	/**
	 * set company id.
	 * @param companyID - company id.
	 */
//	public void setCompanyID(int companyID) {
//		this.companyID = companyID;
//	}
	
	/**
	 * if email and password exists , return true . login by this company. 
	 */
	public boolean login(String email, String password) throws Exception {
		
		ArrayList<Company> companyList = companiesDAO.getAllCompanies();
		
		int i = 0;
		int j = companyList.size();
		
		for (Company company : companyList) {
			if (!(email.equals(company.getEmail()) && password.equals(company.getPassword()))) {
				i++;
			} else {
				this.companyID = company.getId();
				return true;
			}
		}
		if (i == j) {
			throw new ClientDoesntExistsException();
		}
		return false;
	}

	/**
	 * add coupon only if title  and company id different.
	 * @param coupon - coupon object.
	 * @throws Exception
	 */
	public void addCoupon(Coupon coupon) throws Exception {

		ArrayList<Coupon> couponsList = couponsDAO.getAllCoupons();

		for (Coupon value : couponsList) {
			if (value.getTitle().equals(coupon.getTitle())) {
				throw new CouponExistsException();
			}
		}
		coupon.setCompany_ID(this.companyID);
		couponsDAO.addCoupon(coupon);
	}


	/**
	 * update coupon if company id and coupon id equals. 
	 * @param coupon - coupon object
	 * @throws Exception
	 */
	public void updateCoupon(Coupon coupon) throws Exception {
		
		Coupon tempCoupon = couponsDAO.getOneCoupon(coupon.getId());
		if (tempCoupon.getCompany_ID() == coupon.getCompany_ID() && tempCoupon.getId() == coupon.getId()) {
			couponsDAO.updateCoupon(coupon);
		}
		else {
			throw new CouponDoesntExistsException();
		}
	}
		
		
	/**
	 * delete coupon by coupon id.
	 * @param couponID - coupon id.
	 * @throws Exception
	 */
	public void deleteCoupon(int couponID) throws Exception {

		ArrayList <Customer> customersList = customersDAO.getAllCustomers();
		for (Customer value : customersList) {
			ArrayList <Coupon> couponsList = couponsDAO.getAllCoupons();
			for (Coupon coupon : couponsList) {
				if (coupon.getId() == couponID) {
					couponsDAO.deleteCouponPurchase(value.getId(), couponID);	
				}
			}
		}
		couponsDAO.deleteCoupon(couponID);
	}

	/**
	 * get company coupons 
	 * @return company's coupons.
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCompanyCoupons() throws Exception {
		Company company = companiesDAO.getOneCompany(getCompanyID());
		return company.getCoupon();
	}
	
	public Coupon getOneCoupon(int id) throws Exception {
		Coupon coupon = couponsDAO.getOneCoupon(id);
		return coupon;
		
	}
	/**
	 * get company coupons only in specific type.
	 * @param couponType - enum coupon type
	 * @return coupon from this couponType.
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCompanyCoupons(CouponType couponType) throws Exception {
		Company company = companiesDAO.getOneCompany(this.companyID);
		
		ArrayList<Coupon> allCompanyCoupons = company.getCoupon();
		ArrayList<Coupon> couponsByCategory = new ArrayList<>();
		
		for (Coupon coupon : allCompanyCoupons) {
			if(coupon.getType().equals(couponType)) {
			couponsByCategory.add(coupon);
			}
		}
		if (couponsByCategory.size() == 0) {
			throw new CategoryDoesntExistsExeption();
		}
		
		return couponsByCategory;
		
	}
	/**
	 * get company's coupons that less the max price that chosen. 
	 * @param maxPrice - max price.
	 * @return all coupons that less this price.
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws Exception {
		
		Company company = companiesDAO.getOneCompany(this.companyID);
		ArrayList<Coupon> allCompanyCoupons = company.getCoupon();
		ArrayList<Coupon> couponsByPrice = new ArrayList<>();
		
		for (Coupon coupon : allCompanyCoupons) {
			if (coupon.getPrice() <= maxPrice) {
				couponsByPrice.add(coupon);
			}
		}
		
		return couponsByPrice;
		
	}
	/**
	 * get the company that login details.
	 * @return company details
	 * @throws Exception
	 */
	public Company getCompanyDetails() throws Exception {
		Company company = companiesDAO.getOneCompany(this.companyID);
		return company;
		
	}

}