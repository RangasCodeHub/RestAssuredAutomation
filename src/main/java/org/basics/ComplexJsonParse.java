package org.basics;

import files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(Payloads.coursePrice());
        //Print no.of courses returned by API
        int courseCount=js.getInt("courses.size()");
        System.out.println(courseCount);
        int totalAmt = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmt);
        //print title of the first course
        String firstTitle=js.getString("courses[0].title");
        System.out.println(firstTitle);
        //Print all course titles and respective prices
        for(int i=0;i<courseCount;i++)
        {
            System.out.println("Course Title:"+js.getString("courses["+i+"].title"));
            System.out.println("Price:"+js.getInt("courses["+i+"].price"));
        }
        //Print no.of copies sold by Playwright
        for(int i=0;i<courseCount;i++)
        {
            if((js.getString("courses["+i+"].title")).equalsIgnoreCase("Playwright")) {
                System.out.println("Playwright Copies:" + js.getInt("courses[" + i + "].copies"));
                break;
            }
        }
    }
}
