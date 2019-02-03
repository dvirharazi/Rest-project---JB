package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import ConnectionPool.ConnectionPool;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;

public class CompaniesDBDAO implements CompaniesDAO{
	
	private ConnectionPool connectionPool = ConnectionPool.getInstance();
	/**
	 * checking if company exists in database.
	 */
	public boolean isCompanyExists(String email, String password) throws Exception {

		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("SELECT Count(*) AS Count FROM COMPANIES WHERE EMAIL='%s' AND PASSWORD='%s'", email, password);

			//	read the value of Count and if its value is 1 return true, else return false;
			try (PreparedStatement preparedStatement = conn.prepareStatement(sql)){
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);
				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					resultSet.next();

					int count = resultSet.getInt("Count");

					//	Returns 1 or 0 depends if there is a company with the exact email and password. 
					//	Can't be 2 or more because there suppose to be only 1 company with the current email and password inside the table.
					return count == 1;
				}
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}
	}
	/**
	 * adding company to database.
	 */
	public void addCompany(Company company) throws Exception {

		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("INSERT INTO COMPANIES(NAME, EMAIL, PASSWORD) VALUES('%s', '%s', '%s')",
					company.getName(), company.getEmail(), company.getPassword());

			try (PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
				preparedStatement.executeUpdate();

				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					resultSet.next();
					int id = resultSet.getInt(1);
					company.setId(id); // Add the new created id into company object.
				}
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}
	}
	/**
	 * update company 
	 */
	public void updateCompany(Company company) throws Exception {

		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("UPDATE COMPANIES SET NAME = '%s', PASSWORD = '%s', EMAIL = '%s' where id = %d",
					company.getName(), company.getPassword(), company.getEmail(), company.getId());

			try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) { 

				preparedStatement.executeUpdate();

			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}
	}

	/**
	 * delete company by company id .
	 */
	public void deleteCompany(int companyID) throws Exception {

		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("DELETE FROM COMPANIES WHERE ID = %d", companyID);

			try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) { 

				preparedStatement.executeUpdate();

				System.out.println("Company Delete Succeeded");
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}
	}
	/**
	 * get all companies.
	 */
	public ArrayList<Company> getAllCompanies() throws Exception {

		Connection connection = null;

		try {
			connection = connectionPool.getConnection();

			String sql = "SELECT * FROM COMPANIES";

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try(ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Company> allCompanies = new ArrayList<Company>();
					
					while(resultSet.next()) {

						int id = resultSet.getInt("id");
						String name = resultSet.getString("NAME");
						String email = resultSet.getString("EMAIL");
						String password = resultSet.getString("PASSWORD");
						ArrayList<Coupon> coupons = getCouponsByCompanyID(id);
	
						Company company = new Company(id, name, email, password, coupons);
						
						allCompanies.add(company);
					}
					
					return allCompanies;
				}
			}
		}
		finally {
			connectionPool.restoreConnection(connection);
		}
	}
	/**
	 * get one company by company id .
	 */
	public Company getOneCompany(int companyID) throws Exception {

		Connection connection = null;

		try {
			connection = connectionPool.getConnection();

			String sql = String.format("SELECT * FROM COMPANIES WHERE ID=%d", companyID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try(ResultSet resultSet = preparedStatement.executeQuery()) {

					resultSet.next();

					String name = resultSet.getString("NAME");
					String email = resultSet.getString("EMAIL");
					String password = resultSet.getString("PASSWORD");
					ArrayList<Coupon> coupons = getCouponsByCompanyID(companyID);

					Company company = new Company(companyID, name, email, password, coupons);

					return company;
				}
			}
		}
		finally {
			connectionPool.restoreConnection(connection);
		}
	}

	/**
	 * getting coupons by company id . 
	 * @param companyID company id .
	 * @return all coupons
	 * @throws Exception
	 */
	private ArrayList<Coupon> getCouponsByCompanyID(int companyID) throws Exception {

		Connection connection = null;

		try {
			connection = connectionPool.getConnection();

			String sql = String.format("SELECT * FROM COUPONS WHERE COMPANY_ID=%d", companyID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try(ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while(resultSet.next()) {

						int id = resultSet.getInt("id");		
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