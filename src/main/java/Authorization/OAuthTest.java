package Authorization;

import Pojo.GetCourse;
import files.ReusableMethods;

import static io.restassured.RestAssured.*;

public class OAuthTest {
    public static void main(String[] args) {
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
    }
}
