package ca.ulaval.glo4002.cafe.unit.domain.billing.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo4002.cafe.domain.billing.order.OrderFactory;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;

class OrderFactoryTest {

    private static final String PRODUCT_NAME = "Latte";
    private static final BigDecimal PRODUCT_PRICE = new BigDecimal("5");
    private static final ProductInfo PRODUCT_INFO = new ProductInfo(PRODUCT_PRICE, new Ingredients(0, 0, 0, 0));
    private List<Product> listOfProduct;
    private OrderFactory orderFactory;

    @BeforeEach
    void setUp() {
        orderFactory = new OrderFactory();
        listOfProduct = new ArrayList<>();
        listOfProduct.add(new Product(PRODUCT_NAME, PRODUCT_INFO));
    }

    @Test
    void givenACustomerIdAndListOfProduct_whenCreatingOrder_thenReturnNewOrderObject() {
        assertNotNull(orderFactory.create(listOfProduct));
    }
}