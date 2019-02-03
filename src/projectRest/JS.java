package projectRest;

import java.util.ArrayList;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import Facades.AdminFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;
import JavaBeans.Company;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import JavaBeans.Customer;
import johnbryce.beans.Income;

@SuppressWarnings("unchecked")
public class JS {

	static AdminFacade adminFacade = new AdminFacade();
	static CompanyFacade companyFacade = new CompanyFacade();
	static CustomerFacade customerFacade = new CustomerFacade();

	public static String getAllCompaniesJson() throws Exception {
		JSONArray jsonArrayComp = new JSONArray();
		ArrayList<Company> allCompanies = adminFacade.getAllCompanies();
		for (Company company : allCompanies) {
			JSONObject jsonObject = new JSONObject();
			ArrayList<Coupon> coupons = company.getCoupon();
			JSONArray array = new JSONArray();
			for (Coupon coupon : coupons) {
				JSONObject jsonCoupon = new JSONObject();
				jsonCoupon.put("id", coupon.getId());
				jsonCoupon.put("company_ID", coupon.getCompany_ID());
				jsonCoupon.put("title", coupon.getTitle());
				jsonCoupon.put("type", coupon.getType().toString());
				jsonCoupon.put("description", coupon.getDescription());
				jsonCoupon.put("startDate", coupon.getStartDate().toString());
				jsonCoupon.put("endDate", coupon.getEndDate().toString());
				jsonCoupon.put("amount", coupon.getAmount());
				jsonCoupon.put("price", coupon.getPrice());
				jsonCoupon.put("image", coupon.getImage());
				array.add(jsonCoupon);
			}
			jsonObject.put("id", company.getId());
			jsonObject.put("name", company.getName());
			jsonObject.put("email", company.getEmail());
			jsonObject.put("password", company.getPassword());
			jsonObject.put("coupon", array);
			jsonArrayComp.add(jsonObject);
		}

		return jsonArrayComp.toString();
	}


	public static String getOneCompanyJson(Company comp) throws Exception {
		JSONObject jsonObject = new JSONObject();
		ArrayList<Coupon> coupons = comp.getCoupon();
		JSONArray jsonArray = new JSONArray();
		for (Coupon coupon : coupons) {
			JSONObject jsonCoupon = new JSONObject();
			jsonCoupon.put("id", coupon.getId());
			jsonCoupon.put("company_ID", coupon.getCompany_ID());
			jsonCoupon.put("title", coupon.getTitle());
			jsonCoupon.put("type", coupon.getType().toString());
			jsonCoupon.put("description", coupon.getDescription());
			jsonCoupon.put("startDate", coupon.getStartDate().toString());
			jsonCoupon.put("endDate", coupon.getEndDate().toString());
			jsonCoupon.put("amount", coupon.getAmount());
			jsonCoupon.put("price", coupon.getPrice());
			jsonCoupon.put("image", coupon.getImage());
			jsonArray.add(jsonCoupon);

		}
		jsonObject.put("id", comp.getId());
		jsonObject.put("name", comp.getName());
		jsonObject.put("email", comp.getEmail());
		jsonObject.put("password", comp.getPassword());
		jsonObject.put("coupon", jsonArray);

		return jsonObject.toJSONString();
	}

	
	public static String getOneCustomerJson(Customer customer) {
		JSONObject jsonObject = new JSONObject();
		ArrayList<Coupon> coupons = customer.getCoupon();
		JSONArray jsonArray = new JSONArray();
		for (Coupon coupon : coupons) {
			JSONObject jsonCoupon = new JSONObject();
			jsonCoupon.put("id", coupon.getId());
			jsonCoupon.put("company_ID", coupon.getCompany_ID());
			jsonCoupon.put("title", coupon.getTitle());
			jsonCoupon.put("type", coupon.getType().toString());
			jsonCoupon.put("description", coupon.getDescription());
			jsonCoupon.put("startDate", coupon.getStartDate().toString());
			jsonCoupon.put("endDate", coupon.getEndDate().toString());
			jsonCoupon.put("amount", coupon.getAmount());
			jsonCoupon.put("price", coupon.getPrice());
			jsonCoupon.put("image", coupon.getImage());
			jsonArray.add(jsonCoupon);
		}
		jsonObject.put("id", customer.getId());
		jsonObject.put("firstName", customer.getFirstName());
		jsonObject.put("lastName", customer.getLastName());
		jsonObject.put("email", customer.getEmail());
		jsonObject.put("password", customer.getPassword());
		jsonObject.put("coupon", jsonArray);

		return jsonObject.toString();
	}


	public static JSONObject getOneCustomerJsonObject(Customer customer) {
		JSONObject jsonObject = new JSONObject();
		ArrayList<Coupon> coupons = customer.getCoupon();
		JSONArray jsonArray = new JSONArray();
		for (Coupon coupon : coupons) {
			JSONObject jsonCoupon = new JSONObject();
			jsonCoupon.put("id", coupon.getId());
			jsonCoupon.put("company_ID", coupon.getCompany_ID());
			jsonCoupon.put("title", coupon.getTitle());
			jsonCoupon.put("type", coupon.getType().toString());
			jsonCoupon.put("description", coupon.getDescription());
			jsonCoupon.put("startDate", coupon.getStartDate().toString());
			jsonCoupon.put("endDate", coupon.getEndDate().toString());
			jsonCoupon.put("amount", coupon.getAmount());
			jsonCoupon.put("price", coupon.getPrice());
			jsonCoupon.put("image", coupon.getImage());
			jsonArray.add(jsonCoupon);
		}
		jsonObject.put("id", customer.getId());
		jsonObject.put("firstName", customer.getFirstName());
		jsonObject.put("lastName", customer.getLastName());
		jsonObject.put("email", customer.getEmail());
		jsonObject.put("password", customer.getPassword());
		jsonObject.put("coupon", jsonArray);

		return jsonObject;
	}

	public static String getAllCustomerJson() throws Exception {
		JSONArray jsonArrayCust = new JSONArray();
		ArrayList<Customer> allCustomers = adminFacade.getAllCustomers();
		for (Customer cust : allCustomers) {
			jsonArrayCust.add(getOneCustomerJsonObject(cust));
		}
		return jsonArrayCust.toJSONString();
	}

	public static String getOneCouponJson(Coupon coupon) throws Exception {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("id", coupon.getId());
		jsonObject.put("company_ID", coupon.getCompany_ID());
		jsonObject.put("title", coupon.getTitle());
		jsonObject.put("type", coupon.getType().toString());
		jsonObject.put("description", coupon.getDescription());
		jsonObject.put("startDate", coupon.getStartDate().toString());
		jsonObject.put("endDate", coupon.getEndDate().toString());
		jsonObject.put("amount", coupon.getAmount());
		jsonObject.put("price", coupon.getPrice());
		jsonObject.put("image", coupon.getImage());

		return jsonObject.toString();
	}

	public static JSONObject getOneCouponJsonObject(Coupon coupon) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", coupon.getId());
		jsonObject.put("company_ID", coupon.getCompany_ID());
		jsonObject.put("title", coupon.getTitle());
		jsonObject.put("type", coupon.getType().toString());
		jsonObject.put("description", coupon.getDescription());
		jsonObject.put("startDate", coupon.getStartDate().toString());
		jsonObject.put("endDate", coupon.getEndDate().toString());
		jsonObject.put("amount", coupon.getAmount());
		jsonObject.put("price", coupon.getPrice());
		jsonObject.put("image", coupon.getImage());

		return jsonObject;
	}


	public static String getAllCouponJson(CompanyFacade companyFacade) throws Exception {
		JSONArray jsonArray = new JSONArray();
		ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons();
		for (Coupon coup : coupons) {
			jsonArray.add(getOneCouponJsonObject(coup));
		}
		return jsonArray.toString();
	}

	public static String getAllCouponJson(CustomerFacade customerFacade) throws Exception {
		JSONArray jsonArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getAllCoupons();
		for (Coupon coup : coupons) {
			jsonArray.add(getOneCouponJsonObject(coup));
		}
		return jsonArray.toString();
	}


	public static String getCouponsByTypeJson(CouponType couponType , CompanyFacade companyFacade) throws Exception {
		JSONArray jsonArray = new JSONArray();
		ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons(couponType);
		for (Coupon coup : coupons) {
			jsonArray.add(getOneCouponJsonObject(coup));
		}
		return jsonArray.toString();
	}
	

	public static String getCouponsByPriceJson(double maxPrice , CompanyFacade companyFacade) throws Exception {
		JSONArray jsonArray = new JSONArray();
		ArrayList<Coupon> coupons = companyFacade.getCompanyCoupons(maxPrice);
		for (Coupon coup : coupons) {
			jsonArray.add(getOneCouponJsonObject(coup));
		}
		return jsonArray.toString();
	}

	public static String getCustomerCouponsJson(CustomerFacade customerFacade) throws Exception {
		JSONArray jsonArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getCustomerCoupons();
		for (Coupon coup : coupons) {
			jsonArray.add(getOneCouponJsonObject(coup));
		}
		return jsonArray.toString();
	}

	public static String getCustomerCouponsByPriceJson(double maxPrice , CustomerFacade customerFacade) throws Exception {
		JSONArray jsonArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getCustomerCoupons(maxPrice);
		for (Coupon coup : coupons) {
			jsonArray.add(getOneCouponJsonObject(coup));
		}
		return jsonArray.toString();
	}

	public static String getCustomerCouponsByTypeJson(CouponType couponType ,CustomerFacade customerFacade) throws Exception {
		JSONArray jsonArray = new JSONArray();
		ArrayList<Coupon> coupons = customerFacade.getCustomerCoupons(couponType);
		for (Coupon coup : coupons) {
			jsonArray.add(getOneCouponJsonObject(coup));
		}
		return jsonArray.toString();
	}
	public static JSONObject jsonIncome(Income income) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", income.getId());
		jsonObject.put("clientId", income.getClientId());
		jsonObject.put("name" , income.getName());
		jsonObject.put("description", income.getDescription());
		jsonObject.put("amount", income.getAmount());
		jsonObject.put("date", income.getDate());
		return jsonObject;
	}
	

	public static JSONObject jsonCreateIncome(Income income, int incomeType) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("clientId", income.getClientId());
		jsonObject.put("name", income.getName());
		jsonObject.put("description", incomeType);
		jsonObject.put("amount", income.getAmount());
		return jsonObject;
	}
}
