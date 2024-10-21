package ca.ulaval.glo4002.cafe.unit.domain.billing.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.enums.StateEnum;
import ca.ulaval.glo4002.cafe.domain.billing.strategy.UnitedStatesPaymentStrategy;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;

class UnitedStatesPaymentStrategyTest {
  private static final boolean CLIENT_IS_IN_GROUP = true;
  private static final boolean CLIENT_NOT_IN_GROUP = false;
  private static final int GROUP_TIP_RATE = 5;
  private static final BigDecimal LATTE_PRICE = new BigDecimal("5");
  private static final ProductInfo LATTE_INFO = new ProductInfo(LATTE_PRICE, new Ingredients(0, 0, 0, 0));
  private static final BigDecimal TIP_AMOUNT = new BigDecimal("0.50");
  private static final String PRODUCT_LATTE = "Latte";
  private static final StateEnum STATE_ENUM_NY = StateEnum.NY;
  private static final BigDecimal EXPECTED_TAX_RETURN = new BigDecimal("0.40");
  private List<Product> productList;


  @BeforeEach
  void setUp() {
    productList = new ArrayList<>();
    Product product = new Product(PRODUCT_LATTE, LATTE_INFO);
    Product secondProduct = new Product(PRODUCT_LATTE, LATTE_INFO);
    productList.add(product);
    productList.add(secondProduct);
  }

  @Test
  void givenAListOfProductAndIsClientIsInGroup_whenGeneratingBillWithDefaultStrategy_thenReturnBillWithIncludingGroupTip() {
    UnitedStatesPaymentStrategy unitedStatesPaymentStrategy = new UnitedStatesPaymentStrategy(
        STATE_ENUM_NY, GROUP_TIP_RATE);

    Bill bill = unitedStatesPaymentStrategy.generateBill(productList, CLIENT_IS_IN_GROUP);

    assertEquals(BigDecimal.TEN, bill.subtotal());
    assertEquals(TIP_AMOUNT, bill.tip());
    assertEquals(EXPECTED_TAX_RETURN, bill.taxes());
  }

  @Test
  void givenAListOfProductAndIsClientIsNotInGroup_whenGeneratingBillWithDefaultStrategy_thenReturnBillWithoutIncludingGroupTip() {
    UnitedStatesPaymentStrategy unitedStatesPaymentStrategy = new UnitedStatesPaymentStrategy(
        STATE_ENUM_NY, GROUP_TIP_RATE);

    Bill bill = unitedStatesPaymentStrategy.generateBill(productList, CLIENT_NOT_IN_GROUP);

    assertEquals(BigDecimal.TEN, bill.subtotal());
    assertEquals(BigDecimal.ZERO, bill.tip());
    assertEquals(EXPECTED_TAX_RETURN, bill.taxes());
  }
}