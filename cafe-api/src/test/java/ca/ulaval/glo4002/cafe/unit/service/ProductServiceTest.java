package ca.ulaval.glo4002.cafe.unit.service;

import ca.ulaval.glo4002.cafe.application.service.ProductService;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductFactory;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductServiceTest {

    ProductService productService;
    ProductFactory productFactory;

    private static final int DEFAULT_INGREDIENT_STOCK = 20;
    private final static Ingredients BASIC_INGREDIENTS_STOCK = new Ingredients(DEFAULT_INGREDIENT_STOCK, DEFAULT_INGREDIENT_STOCK,
            DEFAULT_INGREDIENT_STOCK, DEFAULT_INGREDIENT_STOCK);
    private final static ProductInfo BASIC_PRODUCT_INFO = new ProductInfo(new BigDecimal(10),
            BASIC_INGREDIENTS_STOCK);
    private final static Product BASIC_PRODUCT = new Product("Name", BASIC_PRODUCT_INFO);


    @BeforeEach
    public void setUp() {
        productFactory = mock(ProductFactory.class);
        productService = new ProductService(productFactory);
    }

    @Test
    void whenAddMenu_thenAddProductIsCalled() {
        productService.addMenu(BASIC_PRODUCT);

        verify(productFactory).addProduct(any());
    }

    @Test
    void whenAddMenu_thenAddProductIsCalledWithRightArgs() {
        productService.addMenu(BASIC_PRODUCT);

        verify(productFactory).addProduct(BASIC_PRODUCT);
    }
}
