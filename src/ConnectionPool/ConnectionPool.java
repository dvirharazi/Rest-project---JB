package ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
	
	static{
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("error!" + e.getMessage());
		}
	}
	/**
	 * Creating a list which holds all the connections.
	 */
	private Stack<Connection> connections = new Stack<>();
	/**
	 * The connection to the Database
	 */
	private static final String connectionString = "jdbc:derby://localhost:1527/CouponsTesting;create=true";
	/**
	 * Creating the only object possible.
	 */
	private static ConnectionPool instance =  new ConnectionPool();
	
	// Private Object lockFlag = new Object();
	
	private int stackSize = 0;
	/**
	 *  Empty constructor which creates connections.
	 */
	private ConnectionPool() {
	
		for (int i = 1; i <= 10; i++) {
			// For each connection created we check if possible to create.
			try {
				Connection conn = DriverManager.getConnection(connectionString);
				connections.push(conn);
				stackSize++;
			} catch (SQLException e) {
				System.out.println("Error! - " + e.getMessage());
			}
		}
	}
	/**
	 *  A function gets that the object.
	 * @return instance.
	 */
	public static ConnectionPool getInstance() {
		return instance;
	}

	public Connection getConnection() throws InterruptedException {

		//	synchronized (lockFlag) {
		synchronized (connections) {
			if(connections.isEmpty()) {
				connections.wait();
			}
			Connection conn = connections.pop();
			return conn;
		}
	}

	public void restoreConnection(Connection conn) {

		synchronized (connections) {
			connections.push(conn);
			connections.notify();
		}
	}
	/**
	 * close all connections.
	 * @throws InterruptedException
	 */
	public void closeAllConnections() throws InterruptedException {

		synchronized (connections) {
			while (connections.size() < stackSize) {
				connections.wait();
			}
			for (Connection conn : connections) {
				try {
					conn.close();
				} 
				catch (SQLException e) {	}
			}
		}
	}
}