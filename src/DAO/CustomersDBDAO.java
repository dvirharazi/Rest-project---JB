package DAO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import ConnectionPool.ConnectionPool;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;

public class CustomersDBDAO implements CustomersDAO{
	
	private ConnectionPool connectionPool = ConnectionPool.getInstance();
	/**
	 * checking if customer exists by email and name. 
	 */
	public boolean isCustomerExists(String email, String password) throws Exception {
		
		Connection conn = null;
		
		try {
			conn = connectionPool.getConnection();
			
			String sql = String.format("SELECT Count(*) AS Count FROM CUSTOMERS WHERE EMAIL='%s' AND PASSWORD='%s'", email, password);
			
			try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);
				try (ResultSet resultSet = preparedStatement.executeQuery()){
					
					resultSet.next();
					
					int count = resultSet.getInt("Count");
					
					return count == 1;
				}
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}
	}

	/**
	 * creating customer and put in database.
	 */
	public void addCustomer(Customer customer) throws Exception {
		

		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("INSERT INTO CUSTOMERS(FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES('%s', '%s', '%s', '%s')",
					customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword());

			try (PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

				preparedStatement.executeUpdate();

				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					resultSet.next();
					int id = resultSet.getInt(1);
					customer.setId(id); // Add the new created id into customer object.
				}
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}
	}


	/**
	 * update customer and put in database.
	 */
	public void updateCustomer(Customer customer) throws Exception {
		
		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("UPDATE CUSTOMERS SET FIRST_NAME = '%s', LAST_NAME = '%s', EMAIL = '%s', PASSWORD = '%s' WHERE ID = %d",
					customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPassword(), customer.getId());

			try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) { 

				preparedStatement.executeUpdate();

				System.out.println("Cusotmer Update Succeeded");
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}
	}

	/**
	 * delete customer by customer id .
	 */
	public void deleteCustomer(int customerID) throws Exception {
		
		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("DELETE FROM CUSTOMERS WHERE ID = %d", customerID);

			try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) { 

				preparedStatement.executeUpdate();

				System.out.println("Customer Delete Succeeded");
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}
	}

	/**
	 * getting all customers from database.
	 */
	public ArrayList<Customer> getAllCustomers() throws Exception {
		
		Connection connection = null;

		try {
			connection = connectionPool.getConnection();

			String sql = "SELECT * FROM CUSTOMERS";

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try(ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Customer> allCustomers = new ArrayList<Customer>();
					
					while(resultSet.next()) {
						
						int id = resultSet.getInt("id");
						String firstName = resultSet.getString("FIRST_NAME");
						String lastName = resultSet.getString("LAST_NAME");
						String email = resultSet.getString("EMAIL");
						String password = resultSet.getString("PASSWORD");
						ArrayList<Coupon> coupons = getCouponsByCustomerID(id);

						Customer customer = new Customer(id, firstName, lastName, email, password, coupons);

						allCustomers.add(customer);
					}
					
					return allCustomers;
				}
			}
		}
		finally {
			connectionPool.restoreConnection(connection);
		}
	}
	/**
	 * get one customer by customer id .
	 */
	public Customer getOneCustomer(int customerID) throws Exception {
		
		Connection connection = null;

		try {
			connection = connectionPool.getConnection();

			String sql = String.format("SELECT * FROM CUSTOMERS WHERE ID=%d", customerID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try(ResultSet resultSet = preparedStatement.executeQuery()) {

					resultSet.next();

					String firstName = resultSet.getString("FIRST_NAME");
					String lastName = resultSet.getString("LAST_NAME");
					String email = resultSet.getString("EMAIL");
					String password = resultSet.getString("PASSWORD");
					ArrayList<Coupon> coupons = getCouponsByCustomerID(customerID);

					Customer customer = new Customer(customerID, firstName, lastName, email, password, coupons);

					return customer;
				}
			}
		}
		finally {
			connectionPool.restoreConnection(connection);
		}
	}
		/**
		 * getting all coupons of specific customer by customer id .
		 * @param customerID customer id.
		 * @return all coupons.
		 * @throws Exception
		 */
	private ArrayList<Coupon> getCouponsByCustomerID(int customerID) throws Exception {

		Connection connection = null;

		try {
			connection = connectionPool.getConnection();

			String sql = String.format("SELECT * FROM COUPONS JOIN CUSTOMERS_Vs_COUPONS ON COUPONS.ID = CUSTOMERS_Vs_COUPONS.COUPON_ID WHERE CUSTOMER_ID=%d", customerID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try(ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while(resultSet.next()) {

						int id = resultSet.getInt("id");
						int companyID = resultSet.getInt("COMPANY_ID");
						String type = resultSet.getString("TYPE");
						String title = resultSet.getString("TITLE");
						String description = resultSet.getString("DESCRIPTION");
						LocalDate startDate = LocalDate.parse(resultSet.getString("START_DATE"));
						LocalDate endDate = LocalDate.parse(resultSet.getString("END_DATE"));
						int amount = resultSet.getInt("AMOUNT");
						double price = resultSet.getInt("PRICE");
						String image = resultSet.getString("IMAGE");

						Coupon coupon = new Coupon(id, companyID, CouponType.getCouponType(type),
								title, description, startDate, endDate,
								amount, price, image);

						allCoupons.add(coupon);
					}

					return allCoupons;
				}
			}
		}
		finally {
			connectionPool.restoreConnection(connection);
		}
	}
}