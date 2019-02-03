package JavaBeans;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author Dvir.
 * company. 
 */
@XmlRootElement
@SuppressWarnings("serial")
public class Company implements Serializable{
	

	private int id;
	private String name;
	private String email;
	private String password;
	private ArrayList<Coupon> coupon;
	/**
	 * empty constructor.
	 */
	public Company() {
		
	}
	/**
	 *  full constructor.
	 */
	public Company(int id, String name, String email, String password, ArrayList<Coupon> coupon) {
		setId(id);
		setName(name);
		setEmail(email);
		setPassword(password);
		setCoupon(coupon);
	}
	public Company(int id, String name, String email, String password) {
		setId(id);
		setName(name);
		setEmail(email);
		setPassword(password);
	}
	
	/**
	 * get company id.
	 * @return company id. 
	 */
	public int getId() {
		return id;
	}
	/**
	 * set company id.
	 * @param id - company id.   
	 */
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	/**
	 *  get company name.
	 *  @return company name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * set company name.
	 *  @param name company name.
	 */
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get company email.
	 *  @return company email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * set company email.
	 *  @param email company email.
	 */
	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<Coupon> getCoupon() {
		return coupon;
	}
	
	@XmlElement
	public void setCoupon(ArrayList<Coupon> coupon) {
		this.coupon = coupon;
	}
	/**
	 * Display company details.
	 */
	public void Display() {
		System.out.print("Company ID: " + id + " Name: " + name + " Email: " + email + " Password: " + password);
		
		for (Coupon couponInsideArray : coupon) {
			System.out.println(couponInsideArray.toString());
		}
	}
	
	/**
	 * toString of company. 
	 */
	public String toString() {
	
		return "Company ID: " + id + ", Name: " + name + ", Email: " + email + ", Password: " + password + ", Coupon: " + coupon.toString() +"\n";

	}
}