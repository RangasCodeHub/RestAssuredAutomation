package org.basics;

import files.Payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Main {
    public static void main(String[] args) {
        //Validate if Add API is working as expected
       RestAssured.baseURI="https://rahulshettyacademy.com";
       String response=given().queryParam("key","qaclick123")
               .header("Content-Type","application/json")
               .body(Payloads.addPlace())
               .when().post("/maps/api/place/add/json")
               .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
               .header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
       JsonPath js = new JsonPath(response);
       String place_id = js.getString("place_id");

       //Update place
        String newAddr="HP Drive, Electronic city phase1";
        given().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Payloads.updatePlace(place_id,newAddr))
                .when().put("/maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
        //Get place
        String getPlaceResponse=given().queryParam("key","qaclick123").queryParam("place_id",place_id)
                .header("Content-Type","application/json")
                .when().get("/maps/api/place/get/json")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js2 = new JsonPath(getPlaceResponse);
        String actualAddr = js2.getString("address");
        System.out.println(actualAddr);
    }
}