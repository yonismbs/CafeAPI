package ca.ulaval.glo4002.cafe.e2e.config;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.CONTENT_TYPE;

import ca.ulaval.glo4002.cafe.api.rest.config.dto.ConfigDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ConfigResourceE2ETestHelper {
    private static final String CONFIG_PATH = "/config";

    public static Response changeConfig(ConfigDTO configDTO){
        return RestAssured.given()
                .contentType(CONTENT_TYPE)
                .body(configDTO)
                .when()
                .post(CONFIG_PATH)
                .then()
                .extract()
                .response();
    }

}
