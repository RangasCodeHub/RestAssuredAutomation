package files;

public class Payloads {

    public static String addPlace()
    {
        return "{\n" +
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
                "}";
    }

    public static String updatePlace(String placeId, String newAddr){
        return "{\n" +
                "\"place_id\":\""+placeId+"\",\n" +
                "\"address\":\""+newAddr+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}";
    }
}
