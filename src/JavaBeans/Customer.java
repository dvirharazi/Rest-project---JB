package JavaBeans;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@SuppressWarnings("serial")
public class Customer implements Serializable{
	
	/**
	 * 
	 */
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private ArrayList<Coupon> coupon;
	/**
	 * empty constructor
	 */
	public Customer() {
		
	}
	
	/**
	 * full constructor.
	 * @param id customer id sent to the constructor.
	 * @param firstName custome'r first name sent to the constructor.
	 * @param lastName custome'r last name sent to constructor.
	 * @param email custome'r email  sent to constructor.
	 * @param password custome'r password sent to constructor.
	 * @param coupon custome'r coupons sent to constructor.
	 */
	public Customer(int id, String firstName, String lastName, String email, String password,
			ArrayList<Coupon> coupon) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
		setCoupon(coupon);
	}
	public Customer(int id, String firstName, String lastName, String email, String password) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
	}

	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	@XmlElement
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	@XmlElement
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
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
	 * display all coupons.
	 */
	public void Display() {
		System.out.print("id: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Password: " + password);
		
		for (Coupon couponInsideArray : coupon) {
			System.out.println(couponInsideArray.toString());
		}
	}
	
	
	public String toString() {
	
		return "Customer ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName + ", Email: " + email + ", Password: " + password + ", Coupon: " + coupon.toString() +"\n";
	}
	
}