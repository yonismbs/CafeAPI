package ca.ulaval.glo4002.cafe.e2e.inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.CafeServer;
import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response.Status;

public class InventoryResourceE2ETest {

    private static final int DEFAULT_QUANTITY_ADDITION = 100;
    private final static InventoryDTO BASIC_INVENTORY_STOCK = new InventoryDTO(DEFAULT_QUANTITY_ADDITION, DEFAULT_QUANTITY_ADDITION,
            DEFAULT_QUANTITY_ADDITION ,DEFAULT_QUANTITY_ADDITION);
    private static final int EXPECTED_EMPTY_STOCK = 0;
    private static final int EXPECTED_RESTOCKED_VALUES = 100;
    private static final String CHOCOLATE = "Chocolate";
    private static final String ESPRESSO = "Espresso";
    private static final String MILK = "Milk";
    private static final String WATER = "Water";

    private CafeServer cafeServer;
    private io.restassured.response.Response response;

    @BeforeEach
    public void setUp() {
        RestAssured.port = PORT;
        cafeServer = new CafeServer();
        cafeServer.run();
    }

    @AfterEach
    public void tearDown() { cafeServer.stop(); }

    @Test
    void whenGetRequestOnInventory_thenEndpointReturnsStatus200() {
        response = InventoryResourceE2ETestHelper.getInventory();

        assertEquals(Status.OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    void whenPutRequestInventory_thenEndpointReturnsStatus200() {
        response = InventoryResourceE2ETestHelper.addInventory(BASIC_INVENTORY_STOCK);

        assertEquals(Status.OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    void givenInventoryWasJustGenerated_whenGetRequestOnInventory_thenIngredientsStocksAreZero() {
        response = InventoryResourceE2ETestHelper.getInventory();

        int chocolateInventory = response.jsonPath().get(CHOCOLATE);
        int espressoInventory = response.jsonPath().get(ESPRESSO);
        int milkInventory = response.jsonPath().get(MILK);
        int waterInventory = response.jsonPath().get(WATER);

        assertEquals(EXPECTED_EMPTY_STOCK, chocolateInventory);
        assertEquals(EXPECTED_EMPTY_STOCK, espressoInventory);
        assertEquals(EXPECTED_EMPTY_STOCK, milkInventory);
        assertEquals(EXPECTED_EMPTY_STOCK, waterInventory);
    }

    @Test
    void givenInventoryWithAddedStock_whenGetRequestOnInventory_thenIngredientsStocksAreUpdated() {
        InventoryResourceE2ETestHelper.addInventory(BASIC_INVENTORY_STOCK);
        response = InventoryResourceE2ETestHelper.getInventory();

        int chocolateInventory = response.jsonPath().get(CHOCOLATE);
        int espressoInventory = response.jsonPath().get(ESPRESSO);
        int milkInventory = response.jsonPath().get(MILK);
        int waterInventory = response.jsonPath().get(WATER);

        assertEquals(EXPECTED_RESTOCKED_VALUES, chocolateInventory);
        assertEquals(EXPECTED_RESTOCKED_VALUES, espressoInventory);
        assertEquals(EXPECTED_RESTOCKED_VALUES, milkInventory);
        assertEquals(EXPECTED_RESTOCKED_VALUES, waterInventory);
    }

}
