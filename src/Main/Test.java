package Main;


import java.time.LocalDate;

import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDAO;
import DAO.CustomersDBDAO;
import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;
import Job.CouponExpirationDailyJob;
import LoginManager.ClientType;
import LoginManager.LoginManager;
/**
 * in this class i checked all the functions.
 * @author haraz
 *
 */
public class Test {

	@SuppressWarnings("null")
	public static void testAll() {
		
		try {
		
			CompaniesDAO companiesDAO = new CompaniesDBDAO();
			CustomersDAO customersDAO = new CustomersDBDAO();
			CouponsDAO couponsDAO = new CouponsDBDAO();
			
		CouponExpirationDailyJob job = new CouponExpirationDailyJob();
		Thread t1 = new Thread(job);
		t1.start();
		
		Company company1 = new Company();
		company1.setName("Sony");
		company1.setEmail("Sony@gmail.com");
		company1.setPassword("123");
		
		Company company2 = new Company();
		company2.setName("Apple");
		company2.setEmail("apple@gmail.com");
		company2.setPassword("456");
		
		Company company3 = new Company();
		company3.setName("Dominos-Pizza");
		company3.setEmail("Dominos@gmail.com");
		company3.setPassword("Do44c");
		
		Company company4 = new Company();
		company4.setName("Nike");
		company4.setEmail("nike@gmail.com");
		company4.setPassword("nike1122");
		
		Company company5 = new Company();
		company5.setName("Booking");
		company5.setEmail("booking@gmail.com");
		company5.setPassword("booking123");
		
		Customer customer1 = new Customer();
		customer1.setFirstName("dvir");
		customer1.setLastName("harazi");
		customer1.setEmail("dvirharazi@gmail.com");
		customer1.setPassword("dgdg");
		
		Customer customer2 = new Customer();
		customer2.setFirstName("Dr");
		customer2.setLastName("Strange");
		customer2.setEmail("strange@gmail.com");
		customer2.setPassword("strange");
		
		Customer customer3 = new Customer();
		customer3.setFirstName("elysha");
		customer3.setLastName("Cohen");
		customer3.setEmail("elysha@gmail.com");
		customer3.setPassword("eli1414");
		
		Customer customer4 = new Customer();
		customer4.setFirstName("yossi");
		customer4.setLastName("ben-dor");
		customer4.setEmail("bendor@gmail.com");
		customer4.setPassword("bd898");
		
		Customer customer5 = new Customer();
		customer5.setFirstName("Eliav");
		customer5.setLastName("Levi");
		customer5.setEmail("Levi@gmail.com");
		customer5.setPassword("levi123");

		
		AdminFacade adminFacade = null;
		adminFacade = (AdminFacade) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
			adminFacade.addCompany(company1);
			adminFacade.addCompany(company2);
			adminFacade.addCompany(company3);
			adminFacade.addCompany(company4);
			adminFacade.addCompany(company5);
			adminFacade.addCustomer(customer1);
			adminFacade.addCustomer(customer2);
			adminFacade.addCustomer(customer3);
			adminFacade.addCustomer(customer4);
			adminFacade.addCustomer(customer5);
			
			System.out.println(adminFacade.getAllCompanies());
			System.out.println(adminFacade.getAllCustomers());
//			adminFacade.deleteCompany(1);
//			adminFacade.deleteCustomer(1);
//			System.out.println(adminFacade.getAllCompanies());
//			System.out.println(adminFacade.getAllCustomers());

			
		Coupon coup1 = new Coupon(CouponType.ELECTRICITY, "I-phone7", "Best phone ever",LocalDate.of(2018, 5, 12), LocalDate.of(2019, 7, 11) , 38, 1000, "image");
		Coupon coup2 = new Coupon(CouponType.CLOTHING, "T-shirt", "T-shirts all colors!!",LocalDate.of(2018, 1, 11), LocalDate.of(2019, 5, 8) ,150, 250, "image");
		Coupon coup3 = new Coupon(CouponType.FOOD, "Pizza", "family pizza only 10$!",LocalDate.of(2018, 3, 7), LocalDate.of(2019, 2, 16) ,55, 10, "image");
		Coupon coup4 = new Coupon(CouponType.ELECTRICITY, "Sony 4", "best sony in world",LocalDate.of(2018, 10, 6), LocalDate.of(2019, 1, 3) ,1500, 150, "image");
		Coupon coup5 = new Coupon(CouponType.CLOTHING, "Shoes", "shoes of nike only this summer",LocalDate.of(2018, 2, 15), LocalDate.of(2019, 7, 9), 89, 100, "image");
		Coupon coup6 = new Coupon(CouponType.FOOD, "melauah pizza", "new in dominos!",LocalDate.of(2018, 6, 11), LocalDate.of(2019, 9, 17), 1200, 30, "image");
		Coupon coup7 = new Coupon(CouponType.VICATION, "Thailand", "7 days ",LocalDate.of(2018, 12, 11), LocalDate.of(2020, 10, 17), 15, 2500, "image");
		Coupon coup8 = new Coupon(CouponType.VICATION, "Greece", "6 days ",LocalDate.of(2018, 12, 9), LocalDate.of(2019, 10, 17), 34, 2000, "image");

		CompanyFacade comp2 = (CompanyFacade) LoginManager.getInstance().login("apple@gmail.com", "456", ClientType.COMPANY);
//		
//		
			comp2.addCoupon(coup1);
//			comp2.addCoupon(coup2);
//			comp2.addCoupon(coup3);
			comp2.addCoupon(coup4);
//			comp2.addCoupon(coup5);
//			comp2.addCoupon(coup6);
//			comp2.deleteCoupon(2);
			System.out.println("first company:");
			System.out.println(comp2.getCompanyCoupons());
//			System.out.println("only clothing:");
//			System.out.println(comp2.getCompanyCoupons(CouponType.CLOTHING));
//			System.out.println("up to 375:");
//			System.out.println(comp2.getCompanyCoupons(375));
//			System.out.println(comp2.getCompanyDetails());
//		 
//		
			CompanyFacade comp1 = (CompanyFacade) LoginManager.getInstance().login("booking@gmail.com", "booking123", ClientType.COMPANY);
			comp1.addCoupon(coup8);
			comp1.addCoupon(coup7);
			System.out.println("second company:");
			System.out.println(comp1.getCompanyCoupons());
			
			CompanyFacade comp3= (CompanyFacade) LoginManager.getInstance().login("nike@gmail.com", "nike1122", ClientType.COMPANY);
			comp3.addCoupon(coup5);
			comp3.addCoupon(coup2);
			System.out.println("third company:");
			System.out.println(comp3.getCompanyCoupons());

			CompanyFacade comp4= (CompanyFacade) LoginManager.getInstance().login("Dominos@gmail.com", "Do44c", ClientType.COMPANY);
			comp4.addCoupon(coup3);
			comp4.addCoupon(coup6);
			System.out.println("last company:");
			System.out.println(comp4.getCompanyCoupons());
			
			
			
		CustomerFacade cust = (CustomerFacade) LoginManager.getInstance().login("strange@gmail.com", "strange", ClientType.CUSTOMER);
//		
			cust.purchaseCoupon(couponsDAO.getOneCoupon(1));
			cust.purchaseCoupon(couponsDAO.getOneCoupon(2));
			cust.purchaseCoupon(couponsDAO.getOneCoupon(3));
//			System.out.println(cust.getCustomerCoupons());
//			System.out.println("only electricy:");
//			System.out.println(cust.getCustomerCoupons(CouponType.ELECTRICITY));
//			System.out.println("up to 300 :");
//			System.out.println(cust.getCustomerCoupons(300));
			System.out.println("customer details:");
			System.out.println(cust.getCustomerDetails());
			CustomerFacade cust2 = (CustomerFacade) LoginManager.getInstance().login("dvirharazi@gmail.com", "dgdg", ClientType.CUSTOMER);
			cust2.purchaseCoupon(couponsDAO.getOneCoupon(4));
			cust2.purchaseCoupon(couponsDAO.getOneCoupon(5));
			cust2.purchaseCoupon(couponsDAO.getOneCoupon(6));
			System.out.println("customer 2 details:");
			System.out.println(cust2.getCustomerDetails());
//			System.out.println(cust.getCustomerDetails());
//		
//			
		job.stop();

		
		}catch (Exception e) {
			System.out.println("Error!! " + e.getMessage());
		}
			
	}
	
}