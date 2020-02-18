package test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import client.RestClient;
import restAPI.TestBase;
import util.TestUtil;

public class GetAPITest extends TestBase {
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

	@Test(priority = 2)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		closeableHttpResponse = restClient.get(URI);

		// a. GET response code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code: " + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "Status code is not as expected");

		// b. GET response body
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString); // "responseString" will be coverted to complete JSON
																	// fromat with the help of JSONObject utility
		System.out.println("Response JSON from API: " + responseJson);
		
		//retrieving the per page value
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is: " + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		//retrieving the total
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Total is: " + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get the value from JSON array
		String LastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		int id = Integer.parseInt(TestUtil.getValueByJPath(responseJson, "/data[0]/id"));
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String first_name = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");
		String email = TestUtil.getValueByJPath(responseJson, "/data[0]/email");
		
		System.out.println("LastName: " + LastName);
		System.out.println("id: " + id);
		System.out.println("avatar: " + avatar);
		System.out.println("first_name: " + first_name);
		System.out.println("email: " + email);
		
		// c. GET header
		Header[] headersArray = closeableHttpResponse.getAllHeaders(); // returns an array of Header
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array: " + allHeaders);

	}

	
	@Test(priority = 1)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
		closeableHttpResponse = restClient.get(URI, headerMap);

		// a. GET response code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code: " + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "Status code is not as expected");

		// b. GET response body
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responseString); // "responseString" will be coverted to complete JSON
																	// fromat with the help of JSONObject utility
		System.out.println("Response JSON from API: " + responseJson);
		
		//retrieving the per page value
		String perPageValue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("Value of per page is: " + perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 6);
		
		//retrieving the total
		String totalValue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("Total is: " + totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get the value from JSON array
		String LastName = TestUtil.getValueByJPath(responseJson, "/data[1]/last_name");
		int id = Integer.parseInt(TestUtil.getValueByJPath(responseJson, "/data[1]/id"));
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[1]/avatar");
		String first_name = TestUtil.getValueByJPath(responseJson, "/data[1]/first_name");
		String email = TestUtil.getValueByJPath(responseJson, "/data[1]/email");
		
		System.out.println("LastName: " + LastName);
		System.out.println("id: " + id);
		System.out.println("avatar: " + avatar);
		System.out.println("first_name: " + first_name);
		System.out.println("email: " + email);
		
		// c. GET header
		Header[] headersArray = closeableHttpResponse.getAllHeaders(); // returns an array of Header
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers Array: " + allHeaders);

	}

}
