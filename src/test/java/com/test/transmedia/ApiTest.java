package com.test.transmedia;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class ApiTest {

	@Test
	public void testAddList() {
//		Set the base URI
		RestAssured.baseURI = "http://localhost:3000";

//      Create the JSON body as a string
		String jsonBody = "{\"boardId\":2,\"name\":\"Software Testing\",\"order\":0}";

//      Send the POST request and get the response
		String response = RestAssured.given().log().all().contentType(ContentType.JSON).body(jsonBody).when()
				.post("/api/lists").then().log().all().assertThat().statusCode(201).extract().response().asString();

		System.out.println("A list added successfully using api.");
		
		JsonPath js = new JsonPath(response);
		String id = js.getString("id");

//		Send DELETE request to delete the created list
		RestAssured.given().pathParam("id", id)
				.when().delete("/api/lists/{id}").then().assertThat().statusCode(200);
		
		System.out.println("Newly created list delete successfully using api.");

	}

}
