package BusinessDelegate;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import johnbryce.beans.Income;
import projectRest.JS;

public class BusinessDelegate {

	
	public synchronized void storeIncome(Income income, int description) throws Exception {
		Client client = Client.create();
		String json = JS.jsonCreateIncome(income, description).toString();
		String url = "http://localhost:8888/income";
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.type(MediaType.APPLICATION_JSON);
		ClientResponse respone = wb.post(ClientResponse.class,json);
	}
	
	
	public synchronized String getAllIncomes() {
		Client client = Client.create();
		String url = "http://localhost:8888/income";
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse respone = wb.get(ClientResponse.class);
		
		if(respone.getStatus() != 200) {
			throw new RuntimeException("Failed: Http Error Code: " + respone.getStatus());
		}
		
		String output = respone.getEntity(String.class);
		return output;
	}
	
	public synchronized String getIncomesByCustomer(int customerID) {
		Client client = Client.create();
		String url = "http://localhost:8888/income/customer/" + customerID;
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse respone = wb.get(ClientResponse.class);
		
		if(respone.getStatus() != 200) {
			throw new RuntimeException("Failed: Http Error Code: " + respone.getStatus());
		}
		
		String output = respone.getEntity(String.class);
		return output;
	}
	
	public synchronized String getIncomesByCompany(int companyID) {
		Client client = Client.create();
		String url = "http://localhost:8888/income/company/" + companyID;
		WebResource webResource = client.resource(url);
		WebResource.Builder wb = webResource.accept(MediaType.APPLICATION_JSON);
		ClientResponse respone = wb.get(ClientResponse.class);
		
		if(respone.getStatus() != 200) {
			throw new RuntimeException("Failed: Http Error Code: " + respone.getStatus());
		}
		
		String output = respone.getEntity(String.class);
		return output;
	}
}