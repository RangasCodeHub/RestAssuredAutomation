package org.basics;

import files.Payloads;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Main {
    public static void main(String[] args) {
        //Validate if Add API is working as expected
       RestAssured.baseURI="https://rahulshettyacademy.com";
       given().queryParam("key","qaclick123")
               .header("Content-Type","application/json")
               .body(Payloads.addPlace())
               .when().post("/maps/api/place/add/json")
               .then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
               .header("Server","Apache/2.4.52 (Ubuntu)");
    }
}