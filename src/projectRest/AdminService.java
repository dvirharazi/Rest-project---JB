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

import org.json.simple.JSONObject;

import com.sun.jersey.spi.container.ResourceFilters;

import BusinessDelegate.BusinessDelegate;
import Facades.AdminFacade;
import JavaBeans.Company;
import JavaBeans.Customer;


@Path("/admin")
@ResourceFilters(filters.LoginFilter.class)
@Consumes(MediaType.APPLICATION_JSON) // type of the info we expect to receive
@Produces(MediaType.APPLICATION_JSON) // the type of the info we return
@SuppressWarnings("unchecked")
public class AdminService {

	@Context // Servlet-הזרקה של האוביקט המתאים מה
	private HttpServletRequest request;

	@Context // Servlet-הזרקה של האוביקט המתאים מה
	private HttpServletResponse response;

	HttpSession session;

	private AdminFacade af;
	BusinessDelegate businessDelegate = new BusinessDelegate();

	public AdminService() {

	}

	@POST
	@Path("/createCompany")
	public Response createCompany(Company company) {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		JSONObject jsonObject = new JSONObject();
		String json = "";
		try {
			af.addCompany(company);
			json = JS.getOneCompanyJson(af.getOneCompany(company.getId()));
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		}
		catch (Exception ex) {
			jsonObject.put("error", ex.getMessage());
			json = jsonObject.toString();
			return Response.serverError().entity(json).build();
		}
	}
	@DELETE
	@Path("/deleteCompany/{id}")
	public Response removeCompany(@PathParam("id") int id) {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		try {
			af.deleteCompany(id);
			return Response.status(204).build();
		} catch (Exception ex) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", ex.getMessage());
			String json = jsonObject.toJSONString();
			return Response.serverError().entity(json).build();
		}
	}

	@PUT
	@Path("/updateCompany")
	public Response updateCompany(Company company) {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		String json = "";
		try {
			af.updateCompany(company);
			json = JS.getOneCompanyJson(af.getOneCompany(company.getId()));
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}

	}

	@GET
	@Path("/company/{id}")
	public Response getCompany(@PathParam("id") int id) {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		try {
			Company company = af.getOneCompany(id);
			String json = JS.getOneCompanyJson(company);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			String json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/companies")
	public Response getAllCompanies() {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		try {
			String json = JS.getAllCompaniesJson();
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			String json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@POST
	@Path("/createCustomer")
	public Response createCustomer(Customer customer) {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		String json = "";

		try {
			af.addCustomer(customer);
			json = JS.getOneCustomerJson(af.getOneCustomer(customer.getId()));
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();

		} catch (Exception ex) {
			 json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@DELETE
	@Path("/deleteCustomer/{id}")
	public Response removeCustomer(@PathParam("id") int id) {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		try {
			af.deleteCustomer(id);
			return Response.status(204).build();
		} catch (Exception ex) {
			String json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@PUT
	@Path("/updateCustomer")
	public Response updateCustomer(Customer customer) {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		String json = "";

		try {
			ArrayList<Customer> allCustomers = af.getAllCustomers();
			for (Customer customer2 : allCustomers) {
				if (customer2.getId() == customer.getId()) {
					af.updateCustomer(customer);
				}
			}
			json = JS.getOneCustomerJson(af.getOneCustomer(customer.getId()));
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			 json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/customer/{id}")
	public Response getCustomer(@PathParam("id") int id) {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		try {
			Customer customer = af.getOneCustomer(id);
			String json = JS.getOneCustomerJson(customer);
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			String json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}

	@GET
	@Path("/customers")
	public Response getAllCustomers() {
		session =request.getSession();
		af = (AdminFacade) session.getAttribute("facade");
		String json = "";

		try {
			json = JS.getAllCustomerJson();
			return Response.ok(json).status(HttpServletResponse.SC_CREATED).build();
		} catch (Exception ex) {
			 json = "{\"error\": \"" + ex.getMessage() + "\"}";
			return Response.serverError().entity(json).build();
		}
	}
	@GET
	@Path("allIncomes")
	public Response getAllIncomes() {
		try {
			String response = businessDelegate.getAllIncomes();
			return Response.ok(response).status(200).build();
		}catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("customerIncome/{id}")
	public Response getCustomerIncome (@PathParam ("id") int customerID) {
		try {
			String response = businessDelegate.getIncomesByCustomer(customerID);
			return Response.ok(response).status(200).build();
		}catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("companyIncome/{id}")
	public Response getCompanyIncome (@PathParam ("id") int companyID) {
		try {
			String response = businessDelegate.getIncomesByCompany(companyID);
			return Response.ok(response).status(200).build();
		}catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}