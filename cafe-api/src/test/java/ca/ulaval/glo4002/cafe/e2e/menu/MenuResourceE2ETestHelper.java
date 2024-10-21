package ca.ulaval.glo4002.cafe.e2e.menu;

import ca.ulaval.glo4002.cafe.api.rest.menu.dto.MenuDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.CONTENT_TYPE;

public class MenuResourceE2ETestHelper {
    private static final String INVENTORY_PATH = "/menu";
    public static Response postMenu(MenuDTO menuDTO) {
        return RestAssured.given()
                .contentType(CONTENT_TYPE)
                .body(menuDTO)
                .when()
                .post(INVENTORY_PATH);
    }
}
