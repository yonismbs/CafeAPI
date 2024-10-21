package ca.ulaval.glo4002.cafe.unit.domain.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductFactory;
import ca.ulaval.glo4002.cafe.domain.product.exception.InvalidMenuOrderException;

import static org.junit.jupiter.api.Assertions.*;

class ProductFactoryTest {
    private static final int FIRST_PRODUCT_INDEX = 0;
    private final static String LATTE = "Latte";
    private final static String WRONG_PRODUCT = "Car";
    private final Ingredients DEFAULT_INGREDIENTS_COST = new Ingredients();
    private static final String DEFAULT_PRODUCT_NAME = "NameProduit";
    private final Product DEFAULT_PRODUCT = new Product(DEFAULT_PRODUCT_NAME, new ProductInfo(new BigDecimal(12), DEFAULT_INGREDIENTS_COST ));
    private List<String> listOfPotentialProducts;
    private ProductFactory productFactory;

    @BeforeEach
    void setUp() {
        productFactory = new ProductFactory();

        listOfPotentialProducts = new ArrayList<>();
        listOfPotentialProducts.add(LATTE);
    }

    @Test
    void givenAListOfProductInString_whenCreatingListOfProduct_thenReturnListOfActualProduct()
            throws InvalidMenuOrderException {

        List<Product> listProduct = productFactory.createAll(listOfPotentialProducts);

        assertEquals(LATTE, listProduct.get(FIRST_PRODUCT_INDEX).name());
    }

    @Test
    void givenAListOfProductInString_whenOneOfTheStringIsNotAProduct_thenInvalidMenuOrderException() {
        listOfPotentialProducts.add(WRONG_PRODUCT);

        assertThrows(InvalidMenuOrderException.class, () -> productFactory.createAll(listOfPotentialProducts));
    }

    @Test
    void whenAddingProduct_thenProductInMap() {
        productFactory.addProduct(DEFAULT_PRODUCT);

        assertTrue(productFactory.getMap().containsKey(DEFAULT_PRODUCT_NAME));
    }
}