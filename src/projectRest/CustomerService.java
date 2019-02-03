package projectRest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.container.ResourceFilters;

import BusinessDelegate.BusinessDelegate;
import DAO.CompaniesDAO;
import DAO.CompaniesDBDAO;
import DAO.CouponsDAO;
import DAO.CouponsDBDAO;
import DAO.CustomersDAO;
import DAO.CustomersDBDAO;
import Facades.CustomerFacade;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import johnbryce.beans.Income;
import johnbryce.beans.IncomeType;

@Path("customer")
@ResourceFilters(filters.LoginFilter.class)
@Consumes(MediaType.APPLICATION_JSON) // type of the info we expect to receive
@Produces(MediaType.APPLICATION_JSON) // the type of the info we return
public class CustomerService {
	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	HttpSession session;

	private CustomerFacade cf;

	CompaniesDAO companiesDAO = new CompaniesDBDAO();
	CustomersDAO customersDAO = new CustomersDBDAO();
	CouponsDAO couponsDAO = new CouponsDBDAO();
	BusinessDelegate businessDelegate = new BusinessDelegate();

	public CustomerService() {

	}

	@POST
	@Path("/purchase")
	public Response purchaseCoupon(Coupon coupon) {
		session = request.getSession();
		cf = (CustomerFacade) session.getAttribute("facade");
		String json = "";
		try {
			cf.purchaseCoupon(coupon);
			String name = cf.getCustomerDetails().getFirstName() + " " + cf.getCustomerDetails().getLastName();
			businessDelegate.storeIncome(new Income(cf.getCustomerID(), name , IncomeType.CUSTOMER_PURCHASE, coupon.getPrice()), 0);
			json = JS.getOneCouponJson(coupon);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/coupons")
	public Response getAllPurchasedCoupons() {
		session = request.getSession();
		cf = (CustomerFacade) session.getAttribute("facade");
		String json = "";
		try {
			json = JS.getCustomerCouponsJson(cf);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/purchased/{id}")
	public Response getOnePurchasedCoupon(@PathParam("id") int id) {
		session = request.getSession();
		cf = (CustomerFacade) session.getAttribute("facade");
		String json = "";
		try {
			Coupon coupon = cf.getOneCustomerCoupon(id);
			json = JS.getOneCouponJson(coupon);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("couponType/{couponType}")
	public Response getAllPurcahsedCouponsByType(@PathParam("couponType") CouponType couponType) {
		session = request.getSession();
		cf = (CustomerFacade) session.getAttribute("facade");
		String json = "";
		try {
			json = JS.getCustomerCouponsByTypeJson(couponType, cf);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/price/{price}")
	public Response getAllPurcahsedCouponsByPrice(@PathParam("price") double price) {
		session = request.getSession();
		cf = (CustomerFacade) session.getAttribute("facade");
		String json = "";
		try {
			json = JS.getCustomerCouponsByPriceJson(price, cf);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/allCoupons")
	public Response getAllCoupons() {
		session = request.getSession();
		cf = (CustomerFacade) session.getAttribute("facade");
		String json = "";
		try {
			json = JS.getAllCouponJson(cf);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}
	@GET
	@Path("/{id}")
	public Response getOneCoupon(@PathParam("id") int id) {
		session = request.getSession();
		cf = (CustomerFacade) session.getAttribute("facade");
		String json = "";
		try {
			json = JS.getOneCouponJson(cf.getOneCoupon(id));
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}
	@GET
	@Path("/details")
	public Response getCustomerDetails() {
		session = request.getSession();
		cf = (CustomerFacade) session.getAttribute("facade");
		String json = "";
		try {
			json = JS.getOneCustomerJson(cf.getCustomerDetails());
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}
}
