package ca.ulaval.glo4002.cafe.e2e.inventory;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.CONTENT_TYPE;

import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class InventoryResourceE2ETestHelper {
    private static final String INVENTORY_PATH = "/inventory";


    public static Response getInventory() {
        return RestAssured.given()
                .when()
                .get(INVENTORY_PATH)
                .then()
                .extract()
                .response();
    }

    public static Response addInventory(InventoryDTO inventoryDTO) {
        return RestAssured.given()
                .contentType(CONTENT_TYPE)
                .body(inventoryDTO)
                .when()
                .put(INVENTORY_PATH);
    }

}
