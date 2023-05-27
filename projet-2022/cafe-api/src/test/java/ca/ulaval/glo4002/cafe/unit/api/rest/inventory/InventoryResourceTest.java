package ca.ulaval.glo4002.cafe.unit.api.rest.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4002.cafe.api.rest.inventory.InventoryResource;
import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;

class InventoryResourceTest {

    private static final int DEFAULT_QUANTITY_ADDITION = 10;
    private final static InventoryDTO INVENTORY_PUT = new InventoryDTO(DEFAULT_QUANTITY_ADDITION, DEFAULT_QUANTITY_ADDITION,
            DEFAULT_QUANTITY_ADDITION ,DEFAULT_QUANTITY_ADDITION);
    private CafeService cafeService;
    private InventoryResource inventoryResource;

    @BeforeEach
    public void setUp() {
        cafeService = mock(CafeService.class);
        inventoryResource = new InventoryResource(cafeService);
    }

    @Test
    void whenPutRequestOnInventory_thenAddStockIsCalledWithRightArgs() {
        inventoryResource.putInventory(INVENTORY_PUT);

        verify(cafeService).addStock(new Ingredients(INVENTORY_PUT.Chocolate(),  INVENTORY_PUT.Espresso(),
                INVENTORY_PUT.Milk(), INVENTORY_PUT.Water()));
    }

}