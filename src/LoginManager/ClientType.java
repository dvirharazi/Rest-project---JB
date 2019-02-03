package LoginManager;
/**
 * enum class , define the client types .
 * @author haraz
 *
 */
public enum ClientType {
	
	ADMINISTRATOR,
	COMPANY,
	CUSTOMER;
	
	public static ClientType getClientType (String type) {
		switch (type) {
		case "ADMINISTRATOR": return ClientType.ADMINISTRATOR;
		case "COMPANY": return ClientType.COMPANY;
		default: return ClientType.CUSTOMER;		
		}
	}
}