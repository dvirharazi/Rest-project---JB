package DAO;

import java.util.ArrayList;

import JavaBeans.Company;

public interface CompaniesDAO {

	boolean isCompanyExists(String email, String password) throws Exception;
	
	void addCompany(Company company) throws Exception;
	
	void updateCompany(Company company) throws Exception;
	
	void deleteCompany(int companyID) throws Exception;
	
	Company getOneCompany(int companyID) throws Exception;
	
	ArrayList<Company> getAllCompanies() throws Exception;
	
	
}