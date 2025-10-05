package Authorization;

import io.restassured.path.json.JsonPath;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import java.time.Duration;

import static io.restassured.RestAssured.given;
import static java.time.temporal.ChronoUnit.*;

public class OAuthTwo {
    public static void main(String[] args) throws InterruptedException {
        /*System.setProperty("webdriver.edge.driver", "C:/Users/dell/msedgedriver.exe");
        WebDriver driver = getWebDriver();
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(Duration.of(5, SECONDS));
        driver.findElement(By.id("identifierId")).sendKeys("saisha5500@gmail.com");
        driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        driver.findElement(By.xpath("input[@type='password']")).sendKeys("saisha5500@gmail.com");
        driver.findElement(By.xpath("input[@type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        String url = driver.getCurrentUrl();
        assert url != null;
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println(code);
        driver.quit();
*/
        String url = "";
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println(code);
        //Exchanging auth code to get access token
        String acTokenResponse = given().log().all().queryParams("code", code)
                .queryParams("client_id", "")
                .queryParams("client_secret", "")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().log().all()
                .post("https://www.googleapis.com/oauth2/v4/token").then().log().all().extract().response().asString();
        String token = new JsonPath(acTokenResponse).getString("access_token");

        //Getting the protected resource with help of access token
        String response = given().queryParam("access_token", token)
                .when().get("https://rahulshettyacademy.com/getCourse.php").asString();
    }

    private static WebDriver getWebDriver() {
        WebDriver driver = new EdgeDriver();
        driver.get("https://accounts.google.com/v3/signin/identifier?opparams=%253Fopenid.realm%2526ss_domain%253Dhttps%25253A%25252F%25252Fin.bookmyshow.com&dsh=S-182150801%3A1759597411134691&client_id=990572338172-iibth2em4l86htv30eg1v44jia37fuo5.apps.googleusercontent.com&fetch_basic_profile=true&gsiwebsdk=2&include_granted_scopes=true&o2v=1&redirect_uri=storagerelay%3A%2F%2Fhttps%2Fin.bookmyshow.com%3Fid%3Dauth660545&response_type=permission+id_token&scope=email+profile+openid&service=lso&flowName=GeneralOAuthFlow&continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Foauth%2Fconsent%3Fauthuser%3Dunknown%26part%3DAJi8hAM-_P2W3cPtFKbwNV21NKjdvS_Hxnl6YcT8kmPkn7kOLu-M4NYzhPlNz0FsNRWUtsZ_FiwPjC0_unExdAe2e0Qd5N7AvMqlub2nL2dIvGgRxbaVIKCM3VEAeJW8hpHUBdANGWI9Hpr4H11zfFsWj9eWUmwYzj1XptO18QMTF7aQacongnAfnfFZaJbLafxaxMkGwSlU6V49DSOdoSsHrZYGv8AQgfHr75UFtzXE3hw2nsL2BQBRN2xBkZ_5Le8iTCTjq9mXauZ5IB6UE55llI4jxO4RtpZMWW0F27E8RVN2HHps5q9e7xJFJ-fdaS9AAcgN6RqQHPYJJm5EGAClBrY3ViIjjK1X9oX87CLqjSmyOb2fJ5xVW1Beqj-_CakfNvNbgVLC4GRPcsGmA7bzbuhisqOe3E8BPm6lQkj9zZ0jqLXmdnCh4VHTjfZjitBUjiy6YxR5Rjh7F510XplLHbv9mDRezQ%26flowName%3DGeneralOAuthFlow%26as%3DS-182150801%253A1759597411134691%26client_id%3D990572338172-iibth2em4l86htv30eg1v44jia37fuo5.apps.googleusercontent.com%23&app_domain=https%3A%2F%2Fin.bookmyshow.com&rart=ANgoxccI5bFPcTN8YfuAUOh-B0-REvxK-d15aoeL8OKLEeNz1P2KG_BROKlV-yep4TnkAS8xvlhi15AMLwi5b4eYNE0T-yJnNhylvQRXisIp2BvzblE4mPE");
        return driver;
    }
}
