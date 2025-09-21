package Authorization;

import Pojo.RSCourses.API;
import Pojo.RSCourses.GetCourse;
import Pojo.RSCourses.WebAutomation;
import files.ReusableMethods;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthTest {
    public static void main(String[] args) {
        String[] expectedCourses = {"Selenium Webdriver Java","Cypress","Protractor"};
        String response=given().log().all()
                .formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
                .then().log().all().extract().response().asString();
        String acToken=ReusableMethods.rawToJson(response).getString("access_token");

        //Get course details
        GetCourse getCourse=given().log().all().queryParam("access_token",acToken)
                .when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then().log().all().extract().response().as(GetCourse.class);
        System.out.println(getCourse.getLinkedIn());
        System.out.println(getCourse.getInstructor());
        List<API> apiCourses = getCourse.getCourses().getApi();
        for (API apiCours : apiCourses) {
            if (apiCours.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCours.getPrice());
            }
        }
        List<WebAutomation> waList = getCourse.getCourses().getWebAutomation();
        List<String> actualCourses = new ArrayList<>();
        for(WebAutomation wa:waList)
        {
            actualCourses.add(wa.getCourseTitle());
        }
        Assert.assertEquals(Arrays.asList(expectedCourses), actualCourses);
    }
}
