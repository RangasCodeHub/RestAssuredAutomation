package org.basics;
import files.Payloads;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class JiraBugTest {
    public static void main(String[] args) {
        RestAssured.baseURI="https://saisha5500.atlassian.net";
        String response=given().header("Content-Type","application/json")
                .header("Accept","application/json")
                .header("Authorization","Basic c2Fpc2hhNTUwMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwUGtVZU8zZWM2djlnMHRhWThYX0pLaEZtVGpRNDRKbS04cEU1X0JxRVFxNTdCZl9pa1d5YVVIUmI2VGpjZnpMVjctQS1kUVlVSkNDd0oybDA1THYyWXVFRGtlMDZTQzFBaUREUHM0TzUyRWY3bllBem9iN2x2NzRSQ3BWTzVYZ1QwblRGNzBDb0trV252UGpGdGpwVVBYN2NFeUVqeWlfd1BMN091c3A1QzNzPTBBMkI1QzFB")
                .body(Payloads.createBug("BVT Issue")).log().all()
                .when().post("/rest/api/3/issue")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();
        String id=ReusableMethods.rawToJson(response).getString("id");
        System.out.println(id);

        //Add attachment
        given().pathParam("Key",id)
                .header("Authorization","Basic c2Fpc2hhNTUwMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwUGtVZU8zZWM2djlnMHRhWThYX0pLaEZtVGpRNDRKbS04cEU1X0JxRVFxNTdCZl9pa1d5YVVIUmI2VGpjZnpMVjctQS1kUVlVSkNDd0oybDA1THYyWXVFRGtlMDZTQzFBaUREUHM0TzUyRWY3bllBem9iN2x2NzRSQ3BWTzVYZ1QwblRGNzBDb0trV252UGpGdGpwVVBYN2NFeUVqeWlfd1BMN091c3A1QzNzPTBBMkI1QzFB")
                .header("X-Atlassian-Token","no-check")
                .multiPart("file",new File("/Users/dell/OneDrive/Desktop/LibraryAPIpostmanCollection.docx")).log().all()
                .when().post("/rest/api/3/issue/{Key}/attachments")
                .then().log().all().assertThat().statusCode(200);

        //Get Issue
        given().pathParam("Key",id)
                .header("Authorization","Basic c2Fpc2hhNTUwMEBnbWFpbC5jb206QVRBVFQzeEZmR0YwUGtVZU8zZWM2djlnMHRhWThYX0pLaEZtVGpRNDRKbS04cEU1X0JxRVFxNTdCZl9pa1d5YVVIUmI2VGpjZnpMVjctQS1kUVlVSkNDd0oybDA1THYyWXVFRGtlMDZTQzFBaUREUHM0TzUyRWY3bllBem9iN2x2NzRSQ3BWTzVYZ1QwblRGNzBDb0trV252UGpGdGpwVVBYN2NFeUVqeWlfd1BMN091c3A1QzNzPTBBMkI1QzFB")
                .when().get("/rest/api/3/issue/{Key}")
                .then().log().all().assertThat().statusCode(200);
    }
}
