package projectRest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.sun.jersey.spi.container.ResourceFilters;

import Exceptions.WrongDetailsException;
import Facades.ClientFacade;
import JavaBeans.Login;
import LoginManager.ClientType;
import LoginManager.LoginManager;


@Path("login")
//@ResourceFilters(filters.CorsFilter.class)
@Consumes(MediaType.APPLICATION_JSON) // type of the info we expect to receive
@Produces(MediaType.APPLICATION_JSON) // the type of the info we return
@SuppressWarnings("unchecked")
public class LoginService {

	@Context // Servlet-הזרקה של האוביקט המתאים מה
	public HttpServletRequest generalRequest;
	@Context
	public HttpServletResponse generalResponse;

	LoginManager localLoginManager = LoginManager.getInstance();

	public HttpSession session;

	@POST
	public Response login(Login login) {
		String json = "";
		System.out.println("login function :");
		try {
			System.out.println(login.getEmail() + " "+ login.getPassword() + " "+ login.getClientType());

			ClientType type = ClientType.getClientType(login.getClientType());
			ClientFacade loginUser = localLoginManager.login(login.getEmail(), login.getPassword(), type);
			
			session = generalRequest.getSession();

			if(loginUser==null) {
				throw new WrongDetailsException();
			}

			session.setAttribute("facade", loginUser);
			session.setAttribute("isLoggedIn",true );
//			String facade = loginUser.toString();
			String facade = type + " , login succeed";
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Logged in", facade);
			
			json = jsonObject.toString();
			return Response.ok(json)/*.header("Access-Control-Allow-Credentials", "true").header("Access-Control-Allow-Origin", "*")*/.build();

		}
		catch(Exception WrongDetailsException){
			json = "{\"error\": \""+WrongDetailsException.getMessage()+"\"}";
			return Response.serverError().entity(json).build();
		}
	}

}