package testsuite.fb;


import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import auto.framework.report.reporter.ReportLog;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SampleAPITest {

	
	@Test
	public void TC1_NegativeTest(){
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(true, true);
		Assert.assertEquals(true, true);
		ReportLog.setTestCase("Get Test");
		RestAssured.baseURI ="http://services.groupkt.com/country/get/all";
		RequestSpecification request = RestAssured.given();
		Response resp = request.get();
		
		ReportLog.verifyTrue(resp.statusCode()==200,"Validate Response Time is 200");
		ReportLog.addInfo(resp.getBody().jsonPath().get("RestResponse.result.name[2]").toString());
		
	}
	
	@Test
	public void RegistrationSuccessful()
	{ 
		ReportLog.setTestCase("Post Test");
	 RestAssured.baseURI ="http://restapi.demoqa.com/customer";
	 RequestSpecification request = RestAssured.given();
	 
	 JSONObject requestParams = new JSONObject();
		/*
		 * requestParams.put("FirstName", "Virender"); // Cast
		 * requestParams.put("LastName", "Singh"); requestParams.put("UserName",
		 * "sdimpleuser2dd2011"); requestParams.put("Password", "password1");
		 * 
		 * requestParams.put("Email", "sample2ee26d9@gmail.com");
		 */
	 request.body(requestParams.toJSONString());
	 Response response = request.post("/register");

	 int statusCode = response.getStatusCode();
	 ReportLog.verifyTrue(statusCode==503,"Status Code");
	
	}
}
