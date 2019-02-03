package projectRest;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.container.ResourceFilters;

import BusinessDelegate.BusinessDelegate;
import Facades.CompanyFacade;
import JavaBeans.Coupon;
import JavaBeans.CouponType;
import johnbryce.beans.Income;
import johnbryce.beans.IncomeType;



@Path("/company")
@ResourceFilters(filters.LoginFilter.class)
@Consumes(MediaType.APPLICATION_JSON) // type of the info we expect to receive
@Produces(MediaType.APPLICATION_JSON) // the type of the info we return
public class CompanyService {

	@Context // Servlet-הזרקה של האוביקט המתאים מה
	private HttpServletRequest request;

	@Context // Servlet-הזרקה של האוביקט המתאים מה
	private HttpServletResponse response;
	
	
	HttpSession session;

	public CompanyFacade cf;
	BusinessDelegate businessDelegate = new BusinessDelegate();

	public CompanyService() {

	}

	@POST
	public Response createCoupon(Coupon coupon) {
		session =request.getSession();
		cf = (CompanyFacade) session.getAttribute("facade");
		String json = "";
		coupon.Display();
		try {
			cf.addCoupon(coupon);
			String name = cf.getCompanyDetails().getName();
			System.out.println(name);
			businessDelegate.storeIncome(new Income(cf.getCompanyID()  ,name, IncomeType.COMPANY_NEW_COUPON, 100), 1);
			json = JS.getOneCouponJson(coupon);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@DELETE
	@Path("/deleteCoupon/{id}")
	public Response removeCoupon(@PathParam("id") int id) {
		session =request.getSession();
		cf = (CompanyFacade) session.getAttribute("facade");
		String json = "";
		try {
			cf.deleteCoupon(id);
			return Response.status(204).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@PUT
	@Path("/updateCoupon")
	public Response updateCoupon(Coupon coupon) {
		session =request.getSession();
		cf = (CompanyFacade) session.getAttribute("facade");
		String json = "";
		try {
			coupon.setCompany_ID(cf.getCompanyID());
			cf.updateCoupon(coupon);
			String name = cf.getCompanyDetails().getName();
			businessDelegate.storeIncome(new Income(cf.getCompanyID() ,name, IncomeType.COMPANY_UPDATE_COUPON, 10), 2);
			json = JS.getOneCouponJson(coupon);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("{id}")
	public Response getCoupon(@PathParam("id") int id) {
		session =request.getSession();
		cf = (CompanyFacade) session.getAttribute("facade");
		Coupon coup = new Coupon();
		String json = "";
		try {
			ArrayList<Coupon> coupons = cf.getCompanyCoupons();
			for (Coupon coupon : coupons) {
				if (id == coupon.getId())
					coup = coupon;
			}
			json = JS.getOneCouponJson(coup);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/coupons")
	public Response getAllCoupons() {
		session =request.getSession();
		cf = (CompanyFacade) session.getAttribute("facade");
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
	@Path("/couponType/{couponType}")
	public Response getCouponsByType(@PathParam("couponType") CouponType couponType) {
		session =request.getSession();
		cf = (CompanyFacade) session.getAttribute("facade");
		String json = "";
		try {
			json = JS.getCouponsByTypeJson(couponType, cf);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/price/{price}")
	public Response getCouponsByPrice(@PathParam("price") double maxPrice) {
		session =request.getSession();
		cf = (CompanyFacade) session.getAttribute("facade");
		String json = "";
		try {
			json = JS.getCouponsByPriceJson(maxPrice, cf);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}
	@GET
	@Path("companyIncome")
	public Response getCompanyIncome () {
		session = request.getSession();
		cf = (CompanyFacade) session.getAttribute("facade");
		try {
			String response = businessDelegate.getIncomesByCompany(cf.getCompanyID());
			return Response.ok(response).status(200).build();
		}catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}