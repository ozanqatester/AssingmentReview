package com.java.stepdefs;

import cucumber.api.java.Before;
import cucumber.api.Scenario;
//import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
//import junit.framework.Assert;
import org.junit.Assert;

import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.*;
import org.apache.commons.io.output.WriterOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.*;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import com.java.runner.TestRunner;
import com.java.utilities.ReusableMethods;

import java.util.Map.Entry;
public class CommonSteps {
	
	static RequestSpecification requestSpecs;
	static ResponseBody body;
	static Response res;
	static String token;
	public static Properties prop = new Properties();
	public static FileInputStream fis;
	public static Properties propResource = new Properties ();
	public static FileInputStream fisResource;
	long currentTimeStamp = System.currentTimeMillis();
	static String environmentName;
	public static String resourceURL;
	 private static   Logger  log= LogManager.getLogger(CommonSteps.class.getName());
	//public static Logger Log = LogManager.getLogger(CustomSearchSerenitysteps.class.getName());
	public static StringWriter requestWriter;
	public static PrintStream requestCapture;
	
	public static StringWriter responseWriter;
	public static PrintStream responseCapture;
	
	public static StringWriter errorWriter;
	public static PrintStream errorCapture;
	
	//URL envPropertyPath = getClass().getClassLoader().getResource("env.properties");
	//URL envPropertyPath = ClassLoader.getSystemClassLoader().getResource("Env.properties");
	
	//URL resourcePropertyPath = getClass().getClassLoader().getResource("resource.properties");
	//URL resourcePropertyPath = ClassLoader.getSystemClassLoader().getResource("resource.properties");
	
	@SuppressWarnings("deprecation")
	@Before
	public void beforeEveryScenario(Scenario s) throws IOException
	{
		System.out.println("------------sceanrio start----------");
		//CommonSteps.log.info("FEATURE +SCENARIO: {}",s);
		
		requestWriter = new StringWriter();
		requestCapture =new PrintStream(new WriterOutputStream(requestWriter),true);
		
		responseWriter = new StringWriter();
		responseCapture = new PrintStream(new WriterOutputStream(requestWriter),true);
		
		errorWriter = new StringWriter();
		errorCapture = new PrintStream(new WriterOutputStream(errorWriter),true);
		
		//load environemnt file
		fis = new FileInputStream(".\\src\\test\\java\\resources\\env.properties");
		prop.load(fis);
		
		//load Resource File
		fisResource =new FileInputStream(".\\src\\test\\java\\resources\\resource.properties");
		propResource.load(fisResource);
		
		environmentName =prop.getProperty("Environment");
		System.out.println("url is ==============="+environmentName);
		//environmentName =System.getenv("env");
		
		// Calling token API 
		//ReusableMethods.getAuthKey(environmentName);
	}
	
	// Get Base url
	
	@Given("^Testing environment$")
	public void getBaseURI() throws Throwable
	{
		RestAssured.baseURI=prop.getProperty(environmentName);
	System.out.println("URL is --------"+RestAssured.baseURI);
	    RestAssured.useRelaxedHTTPSValidation();
	}
	
	 //Set Headers
	
	@When("^I pass headers$")
	public void setHeaders(Map<String,String> headers) throws Throwable
	{
		Iterator<Entry<String,String>> it = headers.entrySet().iterator();
		requestSpecs = given().filter(new RequestLoggingFilter(responseCapture)).filter(new ResponseLoggingFilter(responseCapture)).contentType(ContentType.JSON);
	while (it.hasNext())
	{
		Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
		requestSpecs=requestSpecs.header(pair.getKey(),pair.getValue());
	}
	
	}
	
	//Set queryParameters
	@And("^I pass queryParametres$")
	
	public void setQueryParam(Map<String, String>queryParam) throws Throwable
	{
	
		Iterator<Entry<String,String>> it = queryParam.entrySet().iterator();
		
		while (it.hasNext())
		{
			Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
			requestSpecs=requestSpecs.queryParam(pair.getKey(),pair.getValue());
		}
	}
	
	
	@And("^I set Authorisation token$")
	
	public static void setAuthorisationToken() throws Throwable
	{
		if (token == null)
		{
			token = ReusableMethods.getAuthKey(environmentName);
		}
		
		requestSpecs=requestSpecs.header("Authorization","Basic " + token);
		requestSpecs=requestSpecs.cookie("token",token);
	}
	
	
	
	//Set Path Parameter
		@And("^I pass PathParametres$")
		
		public void setPathParam(Map<String, String>pathParam) throws Throwable
		{
		
			Iterator<Entry<String,String>> it = pathParam.entrySet().iterator();
			
			while (it.hasNext())
			{
				Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
				requestSpecs=requestSpecs.pathParam(pair.getKey(),pair.getValue());
			}
		}
		
	
	//Trigger GET Endpoint
	
	@And("^I perform GET operation \"([^\"]*)\"$")
	public void invokeGETOperation(String resourceName) throws Throwable
	{
		resourceURL = propResource.getProperty(resourceName);
		res = requestSpecs.when().get(resourceURL);
	}
	
	
	// Trigger Post Endpoint
	
	@And("^I perform POST operation \"([^\"]*)\"$")
	public void invokePOSTOperation(String resourceName) throws Throwable
	{
		resourceURL = propResource.getProperty(resourceName);
		CommonSteps.res = CommonSteps.requestSpecs.when().post(resourceURL);
		
	}
	
	
	// Trigger PATCH Endpoint
	
	@And("^I perform PATCH operation \"([^\"]*)\"$")
	public void invokePATCHOperation(String resourceName) throws Throwable
	{
		resourceURL = propResource.getProperty(resourceName);
		CommonSteps.res = CommonSteps.requestSpecs.when().patch(resourceURL);
		
	}
	
	// Trigger Delete Endpoint
	
		@And("^I perform DELETE operation \"([^\"]*)\"$")
		public void invokeDELETEOperation(String resourceName) throws Throwable
		{
			resourceURL = propResource.getProperty(resourceName);
			CommonSteps.res = CommonSteps.requestSpecs.when().delete(resourceURL);
			
		}
	
	// Get Request body from Json path and pass it in post operation
	@And("^I pass body as \"([^\"]*)\"$") 
	public void setBody(String jsonFileName, Map<String ,String> headers) throws Throwable
	{
		Iterator<Entry<String ,String>> it = headers.entrySet().iterator();
		String temp = ReusableMethods.readPayLoadFromJsonFile(".\\src\\test\\java\\resources\\payloads\\" + jsonFileName + ".json");
		
		while (it.hasNext())
		{
			Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
			if (temp.contains("%" +pair.getKey()))
			{
			temp = temp.replace("%"+pair.getKey(),pair.getValue());
			}
		}
		
		requestSpecs.body(temp);
	}
	
	
	// Validate Response type is in JSON format
	
	@And("^response content Type is json$")
	public void verifyResponseContentType() throws Throwable
	{
		res.then().assertThat().contentType(ContentType.JSON);
		
	}
	
	//Validates response bidy contains the given values
	

	@And("^response Body contains$") 
	public void verifyResponseBodyValues(DataTable table) throws Throwable
	{
		List <String> list = table.asList(String.class);
		Iterator<String> it = list.iterator();
		body = res.getBody();
		String bodyStringValue = body.asString();
		while (it.hasNext())
		{
			String i = it.next();
			if (!bodyStringValue.contains(i))
			{
				log.info("Scenario failed :" +i+ "not found");
			}
			Assert.assertTrue(bodyStringValue.contains(i));
		}
		
	}
	
	@And("^response Body doesnot contains$") 
	public void verifyResponseBodyDoesnotContain(DataTable table) throws Throwable
	{
		List <String> list = table.asList(String.class);
		Iterator<String> it = list.iterator();
		body = res.getBody();
		String bodyStringValue = body.asString();
		while (it.hasNext())
		{
			String i = it.next();
			if (bodyStringValue.contains(i))
			{
				log.info("Scenario failed :" +i+ "not found");
			}
			Assert.assertFalse(bodyStringValue.contains(i));
		}
		
	}
	
	//Validate the HTTP status code
	
	@Then("^I should get response \"([^\"]*)\"$") 
	public void verifyHTTPStatusCode(String arg1) throws Throwable
	{
	
		if (res.getStatusCode()!=Integer.parseInt(arg1))
{
	log.info("Scenario failed as expected HTTP status is :" +arg1);
}
		res = res.then().assertThat().statusCode(Integer.parseInt(arg1)).extract().response();
	}
	
	@After
	public static void afterEveryScenario(Scenario s) throws IOException
	{
		System.err.println(requestWriter.toString());
		System.err.println(responseWriter.toString());
		log.info(requestWriter.toString());
		log.info(requestWriter.toString());
		
		if (s!=null)
		{
			if (s.isFailed()) {
			}
			
			log.info("Scenario Status -" +s.getStatus());
			log.info("Scenario Status -" +s.getName());
		}
		
		System.out.println("-----------scenario ends----------");
	}

}
