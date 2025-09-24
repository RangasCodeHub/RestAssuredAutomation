package ecom;

import Pojo.ecomLoginTest.LoginReqData;
import Pojo.ecomLoginTest.LoginResponseData;
import Pojo.ecomLoginTest.OrderDetails;
import Pojo.ecomLoginTest.Orders;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcomAPITest {
    public static void main(String[] args) {
        String baseURI="https://rahulshettyacademy.com";
        //Login Call
        RequestSpecification reqSpec= new RequestSpecBuilder().setBaseUri(baseURI).setContentType(ContentType.JSON).build();
        LoginResponseData loginResponseData=given().spec(reqSpec).body(getLoginReqData())
                .when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponseData.class);
        String token=loginResponseData.getToken();
        String userId=loginResponseData.getUserId();
        //Create product
        RequestSpecification reqCreateProduct= new RequestSpecBuilder().setBaseUri(baseURI).addHeader("Authorization",token).build();
        RequestSpecification addPrdReq=given().spec(reqCreateProduct)
                .param("productName","Casual Tees").param("productAddedBy",userId)
                .param("productCategory","Fashion").param("productSubCategory","Shirts")
                .param("productPrice","345").param("productDescription","Adidas Originals")
                .param("productFor","Men")
                .multiPart("productImage",new File("/Users/dell/OneDrive/Desktop/dp.png"));
        String addProdResponse=addPrdReq.post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
        String productId= new JsonPath(addProdResponse).getString("productId");
        //Create order
        RequestSpecification createOrderSpec = new RequestSpecBuilder().setBaseUri(baseURI).addHeader("Authorization",token).setContentType(ContentType.JSON).build();
        String createOrderResponse=given().log().all().spec(createOrderSpec).body(getOrderDetails(productId))
                .when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();
        String orderID = new JsonPath(createOrderResponse).getString("orders[0]");
        System.out.println("OrderID:"+orderID);
        //View order details
        RequestSpecification spec=new RequestSpecBuilder().setBaseUri(baseURI).addHeader("Authorization",token).setContentType(ContentType.JSON).addQueryParam("id",orderID).build();
        given().log().all().spec(spec).when().get("/api/ecom/order/get-orders-details?id="+orderID)
                .then().log().all().extract().response().asString();
        //Delete product
        RequestSpecification deleteSpec=new RequestSpecBuilder().setBaseUri(baseURI).addHeader("Authorization",token).build();
        given().log().all().spec(deleteSpec).when().delete("/api/ecom/product/delete-product/"+productId)
                .then().log().all().extract().response();
    }

    private static LoginReqData getLoginReqData()
    {
        LoginReqData loginReqData = new LoginReqData();
        loginReqData.setUserEmail("saisha5500@gmail.com");
        loginReqData.setUserPassword("Mani@1996");
        return loginReqData;
    }

    private static OrderDetails getOrderDetails(String productId)
    {
        Orders orders = new Orders();
        orders.setCountry("India");
        orders.setProductOrderedId(productId);
        List<Orders> list = new ArrayList<>();
        list.add(orders);
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrders(list);
        return orderDetails;
    }
}
