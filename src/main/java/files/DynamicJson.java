package files;

import files.Payloads;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class DynamicJson {

    @Test
    public void addBook()
    {
        RestAssured.baseURI="http://216.10.245.166";
        String response=given().log().all().header("Content-Type","application/json")
                .body(Payloads.addBook())
                .when().post("/Library/Addbook.php")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();
        JsonPath js = ReusableMethods.rawToJson(response);
        String uniqID=js.getString("ID");
        System.out.println(uniqID);
    }
}
