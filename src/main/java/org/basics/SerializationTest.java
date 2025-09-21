package org.basics;

import Pojo.GoogleMaps.AddPlace;
import Pojo.GoogleMaps.Location;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class SerializationTest {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com";
        AddPlace ap = getAddPlace();
        Response response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(ap)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();
        System.out.println(response.asString());
    }

    private static AddPlace getAddPlace() {
        AddPlace ap = new AddPlace();
        Location lc = new Location();
        lc.setLat(-38.383494);
        lc.setLng(34.427362);
        ap.setLocation(lc);
        ap.setAccuracy(50);
        ap.setAddress("89, Down town");
        ap.setName("Albert Avenue");
        ap.setWebsite("http://google.com");
        ap.setLanguage("French-IN");
        ap.setPhone_number("(+91) 996 362 9618");
        List<String > types = new ArrayList<>();
        types.add("Shoe part");
        types.add("Shop");
        ap.setTypes(types);
        return ap;
    }
}
