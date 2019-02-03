package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ConnectionPool.ConnectionPool;

public class DatabaseBuilding {

	public static void buildCompaniesTableDB() throws SQLException, InterruptedException {

		Connection connection = null;
		
		try {

			connection = ConnectionPool.getInstance().getConnection();

				String sql = "create table COMPANIES (" +
						"id integer not null primary key " +
						"generated always as identity(start with 1, increment by 1), " +
						"NAME varchar(100) not null, " +
						"PASSWORD varchar(50) not null, " +
						"EMAIL varchar(30) not null)";
				
				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

					preparedStatement.executeUpdate();
					
					ConnectionPool.getInstance().restoreConnection(connection);
					
					System.out.println("Companies table created");
				}
		}
		catch (SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;		
		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	public static void buildCustomersTableDB() throws SQLException, InterruptedException {

		Connection connection = null;
		
		try {

			connection = ConnectionPool.getInstance().getConnection();


				String sql = "create table CUSTOMERS (" +
						"id integer not null primary key " +
						"generated always as identity(start with 1, increment by 1), " +
						"FIRST_NAME varchar(15) not null, " +
						"LAST_NAME varchar(15) not null, " +
						"EMAIL varchar(30) not null, " +
						"PASSWORD varchar(20) not null)";

				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

					preparedStatement.executeUpdate();
					
					ConnectionPool.getInstance().restoreConnection(connection);
					
					System.out.println("Customers table created");
				}
		}

		catch (SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;		
		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	public static void buildCouponsTableDB() throws SQLException, InterruptedException {

		Connection connection = null;
		
		try {

			connection = ConnectionPool.getInstance().getConnection();


				String sql = "create table COUPONS (" +
						"id integer not null primary key " +
						"generated always as identity(start with 1, increment by 1), " +
						"COMPANY_ID integer not null, " +
						"TITLE varchar(100) not null, " +
						"START_DATE date not null, " +
						"END_DATE date not null, " +
						"AMOUNT integer not null, " +
						"TYPE varchar(30) not null, " +
						"DESCRIPTION varchar(300) not null, " +
						"PRICE float not null, " +
						"IMAGE varchar(200) not null)";

				// company id - which company made the coupon, title - short description of the coupon,
				// start date, end date, amount - amount of coupons in stock, type - which catagory (electricity, vications, food...)
				// message - the content of the coupon, more described details. price - coupon worth, 
				// image - a link to the website or picture or something.

				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

					preparedStatement.executeUpdate();
					
					ConnectionPool.getInstance().restoreConnection(connection);
					
					System.out.println("Coupons table created");
				}
		}
		catch (SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;		
		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}

	public static void buildCustomers_Vs_CouponsTableDB() throws SQLException, InterruptedException {
		
		Connection connection = null;
		
		try {

			connection = ConnectionPool.getInstance().getConnection();


				String sql = " CREATE TABLE CUSTOMERS_Vs_COUPONS (" +
						"CUSTOMER_ID NUMERIC, " +
						"COUPON_ID NUMERIC, " +
						"PRIMARY KEY (CUSTOMER_ID, COUPON_ID))";

				try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

					preparedStatement.executeUpdate();
					
					ConnectionPool.getInstance().restoreConnection(connection);
					
					System.out.println("Customers_Vs_Coupons created");
				}
			
		}
		catch (SQLException ex) {
			if(ex.getSQLState().equals("X0Y32")) {
				return;
			}
			throw ex;		
		}
		finally {
			ConnectionPool.getInstance().restoreConnection(connection);
		}
	}



}