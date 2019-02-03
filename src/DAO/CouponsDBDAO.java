package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

import ConnectionPool.ConnectionPool;
import JavaBeans.Coupon;
import JavaBeans.CouponType;


public class CouponsDBDAO implements CouponsDAO{
//	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	/**
	 * get connection from connection pool.
	 */
	private ConnectionPool connectionPool = ConnectionPool.getInstance();
	/**
	 * create coupon , id created automatic. 
	 */
	public void addCoupon(Coupon coupon) throws Exception {


		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("INSERT INTO COUPONS(COMPANY_ID, TITLE, START_DATE, END_DATE, AMOUNT, TYPE, DESCRIPTION, PRICE, IMAGE)"
					+ " values(%d, '%s', '%s', '%s', %d, '%s', '%s', %.2f, '%s')",
					coupon.getCompany_ID(), coupon.getTitle(), coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(), coupon.getType(), coupon.getDescription(), coupon.getPrice(), coupon.getImage());

			try (PreparedStatement preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

				preparedStatement.executeUpdate();

				try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					resultSet.next();
					int id = resultSet.getInt(1);
					coupon.setId(id); // Add the new created id into company object.
				}
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}

	}
	/**
	 * update coupon 
	 */
	public void updateCoupon(Coupon coupon) throws Exception {

		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("Update Coupons set COMPANY_ID = %d, TITLE = '%s', START_DATE = '%s', END_DATE = '%s', AMOUNT = %d, TYPE = '%s', DESCRIPTION = '%s', PRICE = %.2f, IMAGE = '%s' where id = %d",
					coupon.getCompany_ID(), coupon.getTitle(), coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(), coupon.getType().name(), coupon.getDescription(), coupon.getPrice(), coupon.getImage(), coupon.getId());
			
			try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) { 

				preparedStatement.executeUpdate();

			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}

	}
	/**
	 * delete coupon by coupon id.
	 */
	public void deleteCoupon(int couponID) throws Exception {

		Connection conn = null;

		try {
			conn = connectionPool.getConnection();

			String sql = String.format("DELETE FROM COUPONS WHERE ID = %d", couponID);

			try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) { 

				preparedStatement.executeUpdate();

				System.out.println("Coupon Delete Succeeded");
			}
		}
		finally {
			connectionPool.restoreConnection(conn);
		}

	}
	/**
	 * get one coupon by coupon id.
	 */
	public Coupon getOneCoupon(int couponID) throws Exception {

		Connection connection = null;

		try {
			connection = connectionPool.getConnection();

			String sql = String.format("SELECT * FROM COUPONS WHERE ID=%d", couponID);

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try(ResultSet resultSet = preparedStatement.executeQuery()) {

					resultSet.next();

					int id = resultSet.getInt("id");
					int company_ID = resultSet.getInt("COMPANY_ID");
					String title = resultSet.getString("TITLE");
					LocalDate startDate = LocalDate.parse(resultSet.getString("START_DATE"));
					LocalDate endDate =LocalDate.parse(resultSet.getString("END_DATE"));
					int amount = resultSet.getInt("AMOUNT");
					String type = resultSet.getString("TYPE");
					String description = resultSet.getString("DESCRIPTION");
					double price = resultSet.getDouble("PRICE");
					String image = resultSet.getString("IMAGE");

					Coupon coupon = new Coupon(id, company_ID, CouponType.getCouponType(type), title, description, startDate, endDate, amount, price, image);

					return coupon;
				}
			}
		}
		finally {
			connectionPool.restoreConnection(connection);
		}

	}
	/**
	 * get all coupons
	 */
	public ArrayList<Coupon> getAllCoupons() throws Exception {

		Connection connection = null;

		try {
			connection = connectionPool.getConnection();

			String sql = "SELECT * FROM COUPONS";

			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				try(ResultSet resultSet = preparedStatement.executeQuery()) {

					ArrayList<Coupon> allCoupons = new ArrayList<Coupon>();

					while(resultSet.next()) {

						int id = resultSet.getInt("id");
						int company_ID = resultSet.getInt("COMPANY_ID");
						String title = resultSet.getString("TITLE");
						LocalDate startDate = LocalDate.parse(resultSet.getString("START_DATE"));
						LocalDate endDate = LocalDate.parse(resultSet.getString("END_DATE"));
						int amount = resultSet.getInt("AMOUNT");
						String type = resultSet.getString("TYPE");
						String description = resultSet.getString("DESCRIPTION");
						double price = resultSet.getDouble("PRICE");
						String image = resultSet.getString("IMAGE");

						Coupon coupon = new Coupon(id, company_ID, CouponType.getCouponType(type), title, description, startDate, endDate, amount, price, image);

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
	/**
	 * adding coupon to join table ,by customer id and coupon id .
	 */
	public void addCouponPurchase(int customerID, int couponID) throws Exception {

		Connection connection = null;

		try {
			connection = connectionPool.getConnection();
			String sql = String.format(
					"INSERT INTO CUSTOMERS_VS_COUPONS(CUSTOMER_ID, COUPON_ID) VALUES(%d, %d)",
					customerID, couponID);
			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.executeUpdate();
			}
		}
		finally {
			connectionPool.restoreConnection(connection);
		}
	}
	/**
	 * deleted coupon purchase from join table by customer id and coupon id. 
	 */
	public void deleteCouponPurchase(int customerID, int couponID) throws Exception {

		Connection connection = null;

		try {
			connection = connectionPool.getConnection();
			String sql = String.format(
					"DELETE FROM CUSTOMERS_VS_COUPONS WHERE CUSTOMER_ID=%d AND COUPON_ID=%d",
					customerID, couponID);
			try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				preparedStatement.executeUpdate();
				
			}
		}
		finally {
			connectionPool.restoreConnection(connection);
		}
	}
}