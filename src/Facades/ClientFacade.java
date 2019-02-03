package Facades;
import DAO.CompaniesDAO;
import DAO.CouponsDAO;
import DAO.CustomersDAO;
/**
 * Abstract class that adminfacade/clientfacade/customerfacade inheritors her references and methods.
 * @author harazi
 */
public abstract class ClientFacade {

	protected CompaniesDAO companiesDAO;
	protected CustomersDAO customersDAO;
	protected CouponsDAO couponsDAO;
/**
 * Abstract function that get email and password .
 * @param email - email of company, customer or administrator.
 * @param password - password of company customer or administrator.
 * @throws Exception
 */
	public abstract boolean login(String email, String password) throws Exception;

}