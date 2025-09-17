package files;

import files.Payloads;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn,String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(Payloads.addBook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();
        JsonPath js = ReusableMethods.rawToJson(response);
        String uniqID = js.getString("ID");
        System.out.println(uniqID);
    }
    //DeleteBook, Use data provided to delete multiple books

    @DataProvider(name="BooksData")
    public Object[][] getData()
    {
        return new Object[][] {{"HJKLO","123"},{"HJKLO","124"},{"HJKLO","125"},{"HJKLO","126"}};
    }
}
