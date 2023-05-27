package ca.ulaval.glo4002.cafe.unit.api.rest.menu;

import ca.ulaval.glo4002.cafe.api.rest.inventory.dto.InventoryDTO;
import ca.ulaval.glo4002.cafe.api.rest.menu.MenuResource;
import ca.ulaval.glo4002.cafe.api.rest.menu.dto.MenuDTO;
import ca.ulaval.glo4002.cafe.application.service.ProductService;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MenuResourceTest {

    MenuResource menuResource;
    ProductService productService;

    private static final int DEFAULT_INVENTORY_STOCK = 20;

    private final static InventoryDTO BASIC_INVENTORY_STOCK = new InventoryDTO(DEFAULT_INVENTORY_STOCK, DEFAULT_INVENTORY_STOCK,
            DEFAULT_INVENTORY_STOCK, DEFAULT_INVENTORY_STOCK);

    private final static MenuDTO BASIC_PRODUCT_DTO = new MenuDTO("SuperCafe", BASIC_INVENTORY_STOCK, new BigDecimal(20));

    private final static Ingredients INGREDIENTS_FROM_DTO = new Ingredients(BASIC_PRODUCT_DTO.ingredients().Chocolate(), BASIC_PRODUCT_DTO.ingredients().Espresso(),
            BASIC_PRODUCT_DTO.ingredients().Milk(), BASIC_PRODUCT_DTO.ingredients().Water());
    private final static ProductInfo PRODUCT_INFO_FROM_DTO = new ProductInfo(BASIC_PRODUCT_DTO.cost(), INGREDIENTS_FROM_DTO);
    private final static Product PRODUCT_FROM_DTO = new Product(BASIC_PRODUCT_DTO.name(), PRODUCT_INFO_FROM_DTO);

    @BeforeEach
    public void setup() {
        productService = mock(ProductService.class);
        menuResource = new MenuResource(productService);
    }

    @Test
    void whenPostRequestOnMenu_thenAddMenuIsCalledWithRightArgs() {
        menuResource.postMenu(BASIC_PRODUCT_DTO);

        verify(productService).addMenu(PRODUCT_FROM_DTO);
    }
}
