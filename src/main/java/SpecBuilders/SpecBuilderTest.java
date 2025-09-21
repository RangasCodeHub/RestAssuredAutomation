package SpecBuilders;

import Pojo.GoogleMaps.AddPlace;
import Pojo.GoogleMaps.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {
    public static void main(String[] args) {
        String baseURI="https://rahulshettyacademy.com";
        AddPlace ap = getAddPlace();

        RequestSpecification reqSpec =
                new RequestSpecBuilder().setBaseUri(baseURI).setContentType(ContentType.JSON)
                        .addQueryParam("key","qaclick123").build();
        ResponseSpecification resSpec=
                new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        Response response=given().spec(reqSpec)
                .body(ap)
                .when().post("/maps/api/place/add/json")
                .then().spec(resSpec).extract().response();
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
