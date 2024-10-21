package ca.ulaval.glo4002.cafe.e2e.client;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.CafeServer;
import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.e2e.checkin.CheckInResourceE2ETestHelper;
import ca.ulaval.glo4002.cafe.e2e.inventory.InventoryResourceE2ETestHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

class ClientResourceE2ETest {
    private final static int EXPECTED_OK_STATUS_CODE = 200;
    private final static float EXPECTED_TOTAL_PRICE = 8.95f;
    private final static List<String> PRODUCTS = Arrays.asList("Espresso", "Flat White", "Americano");
    private final static  InventoryDTO MAX_INVENTORY_STOCK = new InventoryDTO(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    private CafeServer cafeServer;
    private Response response;

    @BeforeEach
    public void setUp() {
        RestAssured.port = PORT;
        cafeServer = new CafeServer();
        cafeServer.run();

        CheckInResourceE2ETestHelper.checkInCustomer();
    }

    @AfterEach
    public void tearDown() {
        cafeServer.stop();
    }

    @Test
    void givenExistingCustomer_whenGetRequestToLocateExistingCustomer_thenEndPointReturns200() {
        response = ClientResourceE2ETestHelper.getCustomerById();

        assertEquals(EXPECTED_OK_STATUS_CODE, response.statusCode());
    }

    @Test
    void givenExistingCustomer_whenOrderingProduct_thenReturnsExpectedStatusCode() {
        InventoryResourceE2ETestHelper.addInventory(MAX_INVENTORY_STOCK);
        response = ClientResourceE2ETestHelper.orderProducts();

        assertEquals(EXPECTED_OK_STATUS_CODE, response.statusCode());
    }

    @Test
    void givenExistingOrder_whenGettingOrder_thenReturnsExpectedStatusCode() {
        InventoryResourceE2ETestHelper.addInventory(MAX_INVENTORY_STOCK);
        ClientResourceE2ETestHelper.orderProducts();

        response = ClientResourceE2ETestHelper.getOrderedProducts();

        assertEquals(EXPECTED_OK_STATUS_CODE, response.statusCode());
    }

    @Test
    void givenExistingOrder_whenGettingOrder_thenReturnsExpectedValues() {
        InventoryResourceE2ETestHelper.addInventory(MAX_INVENTORY_STOCK);
        ClientResourceE2ETestHelper.orderProducts();

        response = ClientResourceE2ETestHelper.getOrderedProducts();
        String[] listOfOrderedProducts = response.jsonPath().getObject(ORDERS_PARAMETER, String[].class);

        assertEquals(PRODUCTS, Arrays.stream(listOfOrderedProducts).toList());
    }

    @Test
    void givenCheckedOutCustomer_whenGettingBill_thenReturnsExpectedValues() {
        InventoryResourceE2ETestHelper.addInventory(MAX_INVENTORY_STOCK);
        ClientResourceE2ETestHelper.orderProducts();
        CheckInResourceE2ETestHelper.checkOutCustomer();

        response = ClientResourceE2ETestHelper.getBill();
        String[] listOfOrderedProducts = response.jsonPath().getObject(ORDERS_PARAMETER, String[].class);

        assertEquals(PRODUCTS, Arrays.stream(listOfOrderedProducts).toList());
        assertEquals(EXPECTED_TOTAL_PRICE, (float) response.jsonPath().get(TOTAL_PARAMETER));
    }

    @Test
    void givenCheckedOutCustomer_whenGettingBill_thenReturnsExpectedStatusCode() {
        InventoryResourceE2ETestHelper.addInventory(MAX_INVENTORY_STOCK);
        ClientResourceE2ETestHelper.orderProducts();
        CheckInResourceE2ETestHelper.checkOutCustomer();

        response = ClientResourceE2ETestHelper.getBill();

        assertEquals(EXPECTED_OK_STATUS_CODE, response.statusCode());
    }

}
