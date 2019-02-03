package filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

import Facades.AdminFacade;
import Facades.ClientFacade;
import Facades.CompanyFacade;
import Facades.CustomerFacade;

public class LoginFilter implements ResourceFilter, ContainerRequestFilter, ContainerResponseFilter {
	@Context
	private HttpServletRequest generalRequest;
	@Context
	private HttpServletResponse generalResponse;

	@Override
	public ContainerRequest filter(ContainerRequest request) {
		HttpSession session = generalRequest.getSession();
		ClientFacade clientFacade = (ClientFacade) session.getAttribute("facade");
		boolean login = (boolean) session.getAttribute("isLoggedIn");
		System.out.println("this the address : " + clientFacade);
		System.out.println("is logged in ?  : " + login);

		if (clientFacade instanceof AdminFacade && login) {
			return request;
		} else if (clientFacade instanceof CustomerFacade && login) {
			return request;
		} else if (clientFacade instanceof CompanyFacade && login) {
			return request;
		} else {
			ResponseBuilder builder = null;
			String response = "UNAUTHORIZED";
			builder = Response.status(Response.Status.UNAUTHORIZED).entity(response);
			throw new WebApplicationException(builder.build());
		}
	}

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		return response;
	}

	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return this;
	}
}
