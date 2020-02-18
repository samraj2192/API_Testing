package test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.RestClient;
import data.Users;
import restAPI.TestBase;

public class PostAPITest extends TestBase {
	TestBase testbase;
	String url;
	String apiURL;
	String URI;
	CloseableHttpResponse closeableHttpResponse;
	RestClient restClient;
	
	@BeforeMethod
	public void setUp() throws ClientProtocolException, IOException {
		testbase = new TestBase();
		url = prop.getProperty("URL");
		apiURL = prop.getProperty("serviceURL");

		URI = url + apiURL;
		System.out.println(URI);
	}
	
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		
		//Jackson API(Marshelling of the POJO)
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("Samraj", "Leader"); //expected users object
		
		//object to JSON file conversion
		mapper.writeValue(new File("C:\\Workspace_API_Testing\\restAPI\\src\\main\\java\\data\\users.json"), users);
		
		//object to JSON in string conversion
		String usersJSONString = mapper.writeValueAsString(users);
		System.out.println(usersJSONString);
		
		//POST call
		closeableHttpResponse = restClient.post(URI, usersJSONString, headerMap);
		
		//validate the response
		//1. get the status code
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code: " + statusCode);
		Assert.assertEquals(statusCode, testbase.RESPONSE_STATUS_CODE_201);
		
		//2. Json String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		//3. converting the responseString to JSON
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("The response from API is: " + responseJSON);
		
		//4. UNMARSHELLING: Json to java object(Validate the response string)
		Users userObj = mapper.readValue(responseString, Users.class); //actual; users object
		System.out.println(userObj);
		
		Assert.assertTrue(users.getName().equals(userObj.getName()));
		Assert.assertTrue(users.getJob().equals(userObj.getJob()));
		
		System.out.println(userObj.getId());
		System.out.println(userObj.getCreatedAt());
		
	}

}
