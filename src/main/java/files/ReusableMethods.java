package files;

import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReusableMethods {
    public static JsonPath rawToJson(String response){
        return new JsonPath(response);
    }

    public static String generateStringFromBytes(String s) throws IOException {
        return new String(Files.readAllBytes(Path.of(s)));
    }
}
