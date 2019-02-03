package LoginManager;

import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;

public class LoginManager {

	private AdminFacade adminFacade = new AdminFacade();
	private CompanyFacade companyFacade = new CompanyFacade();
	private CustomerFacade customerFacade = new CustomerFacade();

	private static LoginManager instance = new LoginManager();

	public static LoginManager getInstance() {
		return instance;
	}

	private LoginManager() {

	}
	/**
	 * 
	 * @param email email we got and checked.
	 * @param password password we got and checked.
	 * @param clientType client type we got ,according to this we checked email and password.
	 * @return null if doesn't exists or one of the objects.
	 * @throws Exception
	 */
	public ClientFacade login(String email, String password, ClientType clientType) throws Exception {

		switch (clientType) {
		case ADMINISTRATOR:
			if (adminFacade.login(email, password)) {
				return adminFacade;
			} else {
				return null;
			}
		case COMPANY:
			if (companyFacade.login(email, password)) {
				return companyFacade;
			} else {
				return null;
			}
		case CUSTOMER:
			if (customerFacade.login(email, password)) {
				return customerFacade;
			} else {
				return null;
			}
		}
		return null;
	}

}