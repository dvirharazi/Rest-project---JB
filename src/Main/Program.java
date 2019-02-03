package Main;

import java.sql.SQLException;


public class Program {

	public static void createAllTables() throws SQLException, InterruptedException {
		DatabaseBuilding.buildCompaniesTableDB();
		DatabaseBuilding.buildCustomersTableDB();
		DatabaseBuilding.buildCouponsTableDB();
		DatabaseBuilding.buildCustomers_Vs_CouponsTableDB();

	}
	/**
	 * building database , call to test class with all functions. 
	 */
	public static void main(String[] args) throws Exception {
		
		createAllTables();

		Test.testAll();
		
		
		
		
		
	}
}