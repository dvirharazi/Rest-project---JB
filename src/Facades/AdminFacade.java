package Facades;
import java.util.ArrayList;

import DAO.CompaniesDBDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDBDAO;
import Exceptions.ClientDoesntExistsException;
import Exceptions.ComapnyNameAlreadyExistsException;
import Exceptions.CompanyEmailAlreadyExistsException;
import Exceptions.CustomerDoesntExistsException;
import Exceptions.CustomerEmailAlreadyExistsException;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.Customer;

/**
 * Business Logic of administrator
 * @author Dvir.
 */
public class AdminFacade extends ClientFacade {

	public AdminFacade() {
		companiesDAO = new CompaniesDBDAO();
		customersDAO = new CustomersDBDAO();
		couponsDAO = new CouponsDBDAO();
	}
	/**
	 * administrator login by hard-coded email and password . 
	 */
	public  boolean login(String email, String password) {
		return email.equals("admin@admin.com") && password.equals("admin");		
	}
	/**\
	 * the function get company and checking if the company already exists 
	 * if doesn't exists add to database.
	 * @param company - company object
	 * @throws Exception
	 */
	public void addCompany(Company company) throws Exception {

		ArrayList<Company> allCompanies = companiesDAO.getAllCompanies();

		for (Company comp : allCompanies) {

			if(comp.getName().equals(company.getName())) {
				throw new ComapnyNameAlreadyExistsException();
			}

			if(comp.getEmail().equals(company.getEmail())) {
				throw new CompanyEmailAlreadyExistsException();
			}
		}

		companiesDAO.addCompany(company);
	}
	/**
	 * if the name of company we've got exists, do update . 
	 * @param company - company object
	 * @throws Exception
	 */
	public void updateCompany(Company company) throws Exception {


		Company company2 = companiesDAO.getOneCompany(company.getId());
				company2.setEmail(company.getEmail());
				company2.setPassword(company.getPassword());
				companiesDAO.updateCompany(company2);
	}

	/**
	 * delete company , all company's coupons and purchase coupon .
	 * @param companyID - company id.
	 * @throws Exception
	 */
	public void deleteCompany(int companyID) throws Exception {

		ArrayList<Customer> CustomersList = customersDAO.getAllCustomers();
		for (Customer customer : CustomersList) {
			ArrayList <Coupon> couponsList2 = couponsDAO.getAllCoupons();
			for (Coupon coupon : couponsList2) {
				if(coupon.getCompany_ID()==companyID) {
					couponsDAO.deleteCouponPurchase(customer.getId(), coupon.getId());
					couponsDAO.deleteCoupon(coupon.getId());	
				}
			}
			
		ArrayList <Coupon> couponsList = couponsDAO.getAllCoupons();
		for (Coupon value : couponsList) {
			if(value.getCompany_ID()==companyID) {
				couponsDAO.deleteCoupon(value.getId());
			}
		}

			companiesDAO.deleteCompany(companyID);
		}
	}
	/**
	 * get one company by id .
	 * @param companyID - company id.
	 * @return company.
	 * @throws Exception
	 */
	public Company getOneCompany(int companyID) throws Exception {
		return companiesDAO.getOneCompany(companyID);
	}
	/**
	 * get all companies.
	 * @return all companies.
	 * @throws Exception
	 */
	public ArrayList<Company> getAllCompanies() throws Exception {
		return companiesDAO.getAllCompanies();

	}

	/**
	 * add customer if the name and email don't exists.
	 * @param customer - customer object.
	 * @throws Exception
	 */
	public void addCustomer(Customer customer) throws Exception {

		ArrayList<Customer> allCustomers = customersDAO.getAllCustomers();

		for (Customer cust : allCustomers) {

			if(cust.getEmail().equals(customer.getEmail())) {
				throw new CustomerEmailAlreadyExistsException();
			}
		}

		customersDAO.addCustomer(customer);
	}

	/**
	 * if we got customer that is id already exists, making update. 
	 * @param customer- customer update.
	 * @throws Exception
	 */
	public void updateCustomer(Customer customer) throws Exception {

		ArrayList<Customer> allCustomers = customersDAO.getAllCustomers();

		int i = 0;
		int j = allCustomers.size();

		for (Customer cust : allCustomers) {

			if(cust.getId() == customer.getId()) {
				cust.setFirstName(customer.getFirstName());
				cust.setLastName(customer.getLastName());
				cust.setEmail(customer.getEmail());
				cust.setPassword(customer.getPassword());
				customersDAO.updateCustomer(cust);
			}
			else {
				i++;
			}
		}
		if (i == j) {
			throw new CustomerDoesntExistsException();
		}
	}
	/**
	 * delete customer by customer id .
	 * @param customerID - customer id.
	 * @throws Exception
	 */
	public void deleteCustomer(int customerID) throws Exception {

		Customer customer = customersDAO.getOneCustomer(customerID);

		ArrayList <Coupon> couponsList = customer.getCoupon();
		for (Coupon value : couponsList) {
			couponsDAO.deleteCouponPurchase(customerID, value.getId());
		}
		customersDAO.deleteCustomer(customerID);
		System.out.println("Delete Succeeded");
	}
	/**
	 * get all customers
	 * @return all customer.
	 * @throws Exception
	 */
	public ArrayList<Customer> getAllCustomers() throws Exception {		
		return customersDAO.getAllCustomers();
	}
	/**
	 * get one customer.
	 * @param customerID - customer id.
	 * @return one customer.
	 * @throws Exception
	 */
	public Customer getOneCustomer(int customerID) throws Exception {
		return customersDAO.getOneCustomer(customerID);
	}
}