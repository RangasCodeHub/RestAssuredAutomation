package org.basics;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class Main {
    public static void main(String[] args) {
        //Validate if Add API is working as expected
       RestAssured.baseURI="https://rahulshettyacademy.com";
       given().queryParam("key","qaclick123")
               .header("Content-Type","application/json")
               .body("{\n" +
                       "\"location\": {\n" +
                       "\"lat\": -36.383494,\n" +
                       "\"lng\": 32.427362\n" +
                       "},\n" +
                       "\"accuracy\": 50,\n" +
                       "\n" +
                       "\"name\": \"Amigo Real Estate\",\n" +
                       "\"phone_number\": \"(+91) 983 893 3937\",\n" +
                       "\"address\": \"29, BTY layout, cohen 09\",\n" +
                       "\"types\": [\n" +
                       "\"shoe park\",\n" +
                       "\"shop\"\n" +
                       "],\n" +
                       "\"website\": \"http://google.com\",\n" +
                       "\"language\": \"French-IN\"\n" +
                       "}")
               .when().post("/maps/api/place/add/json")
               .then().log().all().assertThat().statusCode(200);
    }
}