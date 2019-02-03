package JavaBeans;
import java.io.Serializable;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@SuppressWarnings("serial")
public class Coupon implements Serializable{
	
	private int id;
	private int company_ID;
	private CouponType type;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private double price;
	private String image;
	
	/**
	 * empty constructor
	 */
	public Coupon() {
		
	}	
	/**
	 * full constructor.
	 */
	
	public Coupon(int id, int company_ID, CouponType type, String title, String description,  LocalDate stratDate,
			LocalDate endDate, int amount, double price, String image) {
		setId(id);
		setCompany_ID(company_ID);
		setType(type);
		setTitle(title);
		setDescription(description);
		setStartDate(stratDate);
		setEndDate(endDate);
		setAmount(amount);
		setPrice(price);
		setImage(image);
		
	}
	public Coupon( int company_ID, CouponType type, String title, String description, LocalDate startDate,
			LocalDate endDate, int amount, double price, String image) {
		setCompany_ID(company_ID);
		setType(type);
		setTitle(title);
		setDescription(description);
		setStartDate(startDate);
		setEndDate(endDate);
		setAmount(amount);
		setPrice(price);
		setImage(image);
		
	}
	public Coupon( CouponType type, String title, String description,LocalDate stratDate,
			LocalDate endDate, int amount, double price, String image) {
		setType(type);
		setTitle(title);
		setDescription(description);
		setStartDate(stratDate);
		setEndDate(endDate);
		setAmount(amount);
		setPrice(price);
		setImage(image);
		
	}
	
	public int getId() {
		return id;
	}
	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	public int getCompany_ID() {
		return company_ID;
	}
	@XmlElement
	public void setCompany_ID(int company_ID) {
		this.company_ID = company_ID;
	}
	public CouponType getType() {
		return type;
	}
	@XmlElement
	public void setType(CouponType type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	@XmlElement
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	@XmlElement
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public int getAmount() {
		return amount;
	}
	@XmlElement
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getPrice() {
		return price;
	}
	@XmlElement
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	@XmlElement
	public void setImage(String image) {
		this.image = image;
	}
	public void Display() {
		System.out.print("id: " + id + ", Company ID: " + company_ID + ", Type: " + type + 
				", Title: " + title + ", Description: " + description + ", Start Date: " + startDate +
				", End Date: " + endDate + ", Amount: " + amount + ", Price: " + price + ", Image: " + image);
	}
	
	/**
	 * toString the coupon details.
	 */
	
	public String toString() {
		return "\n" + "id: " + id + ", Company ID: " + company_ID + ", Type: " + type + 
				", Title: " + title + ", Description: " + description + ", Start Date: " + startDate +
				", End Date: " + endDate + ", Amount: " + amount + ", Price: " + price + ", Image: " + image;
	}
	
}