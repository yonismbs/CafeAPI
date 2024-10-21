package ca.ulaval.glo4002.cafe.e2e.client;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ClientResourceE2ETestHelper {
	private final static String CLIENT_ID = "11111";
	private final static List<String> PRODUCTS = Arrays.asList("Espresso", "Flat White", "Americano");
	private final static String ORDER_PRODUCT_PATH = "customers/{customer_id}/orders";
	private final static String GET_CUSTOMER_BY_ID_PATH = "customers/{customer_id}";
	private final static String GET_BILL_PATH = "customers/{customer_id}/bill";

	public static Response getCustomerById() {
		return RestAssured.given()
				.pathParams(CUSTOMER_ID_PARAMETER, CLIENT_ID)
				.when()
				.get(GET_CUSTOMER_BY_ID_PATH)
				.then()
				.extract()
				.response();
	}

	public static Response orderProducts(){
		JSONObject jsonObject = new JSONObject()
				.put(ORDERS_PARAMETER, PRODUCTS);

		return RestAssured.given()
				.contentType(CONTENT_TYPE)
				.pathParams(CUSTOMER_ID_PARAMETER, CLIENT_ID)
				.body(jsonObject.toString())
				.when()
				.put(ORDER_PRODUCT_PATH)
				.then()
				.extract()
				.response();
	}

	public static Response getOrderedProducts() {
		return RestAssured.given()
				.contentType(CONTENT_TYPE)
				.pathParams(CUSTOMER_ID_PARAMETER, CLIENT_ID)
				.when()
				.get(ORDER_PRODUCT_PATH)
				.then()
				.extract()
				.response();
	}

	public static Response getBill() {
		return RestAssured.given()
				.contentType(CONTENT_TYPE)
				.pathParams(CUSTOMER_ID_PARAMETER, CLIENT_ID)
				.when()
				.get(GET_BILL_PATH)
				.then()
				.extract()
				.response();
	}

}
