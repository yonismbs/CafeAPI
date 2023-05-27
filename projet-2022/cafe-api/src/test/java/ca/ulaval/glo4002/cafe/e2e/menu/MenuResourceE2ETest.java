package ca.ulaval.glo4002.cafe.e2e.menu;

import ca.ulaval.glo4002.cafe.CafeServer;
import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.api.rest.menu.dto.MenuDTO;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuResourceE2ETest {

    CafeServer cafeServer;
    private static final int DEFAULT_INVENTORY_STOCK = 20;

    private final static InventoryDTO BASIC_INVENTORY_STOCK = new InventoryDTO(DEFAULT_INVENTORY_STOCK, DEFAULT_INVENTORY_STOCK,
            DEFAULT_INVENTORY_STOCK, DEFAULT_INVENTORY_STOCK);

    private final static MenuDTO BASIC_PRODUCT_DTO = new MenuDTO("SuperCafe", BASIC_INVENTORY_STOCK, new BigDecimal(20));

    private io.restassured.response.Response response;

    @BeforeEach
    public void setUp() {
        RestAssured.port = PORT;
        cafeServer = new CafeServer();
        cafeServer.run();
    }

    @AfterEach
    public void tearDown() {
        cafeServer.stop();
    }

    @Test
    void whenPostMenu_thenEndpointReturnsStatus200() {
        response = MenuResourceE2ETestHelper.postMenu(BASIC_PRODUCT_DTO);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());
    }
}
