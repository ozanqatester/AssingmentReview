package com.java.utilities;

import com.java.stepdefs.CommonSteps;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods { 
	
	public static JsonPath rawToJson (Response r)
	{
		String res = r.asString();
		JsonPath jsonPath = new JsonPath(res);
		return jsonPath;
	}
	
	public static String readPayLoadFromJsonFile(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
		
	}
	
	public static String getAuthKey(String baseURI)
	{
		AuthPayLoad authPayload = new AuthPayLoad("admin", "password123");
		 Response res = given().body(authPayload)
				 .header("Content-Type" ,"application/json").post("https://restful-booker.herokuapp.com/auth");
		 System.out.println(res);
		 res.prettyPrint();
		JsonPath jsonPath =ReusableMethods.rawToJson(res);
		String x = jsonPath.getString("token");
		System.out.println(x);
		return x;
	}

}
