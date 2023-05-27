package ca.ulaval.glo4002.cafe.e2e.checkin;

import org.json.JSONObject;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CheckInResourceE2ETestHelper {
    private final static String CLIENT_NAME = "Jason Momoa";
    private final static String CLIENT_ID = "11111";
    private final static String POST_REQUEST_PATH = "check-in";
    private final static String CHECK_OUT_REQUEST_PATH = "checkout";

    public static Response checkInCustomer() {
        JSONObject jsonObject = new JSONObject()
                .put(CUSTOMER_NAME_PARAMETER, CLIENT_NAME)
                .put(CUSTOMER_ID_PARAMETER, CLIENT_ID)
                .put(GROUP_NAME_PARAMETER, JSONObject.NULL);

        return RestAssured.given()
                .contentType(CONTENT_TYPE)
                .body(jsonObject.toString())
                .when()
                .post(POST_REQUEST_PATH)
                .then()
                .extract()
                .response();
    }

    public static Response checkOutCustomer() {
        JSONObject jsonObject = new JSONObject()
                .put(CUSTOMER_ID_PARAMETER, CLIENT_ID);

        return RestAssured.given()
                .contentType(CONTENT_TYPE)
                .body(jsonObject.toString())
                .when()
                .post(CHECK_OUT_REQUEST_PATH)
                .then()
                .extract()
                .response();
    }

}
