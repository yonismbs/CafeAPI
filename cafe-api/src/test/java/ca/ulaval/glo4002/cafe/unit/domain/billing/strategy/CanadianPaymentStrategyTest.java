package ca.ulaval.glo4002.cafe.unit.domain.billing.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.enums.ProvinceEnum;
import ca.ulaval.glo4002.cafe.domain.billing.strategy.CanadianPaymentStrategy;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductInfo;

class CanadianPaymentStrategyTest {

  private static final boolean CLIENT_IS_IN_GROUP = true;
  private static final boolean CLIENT_NOT_IN_GROUP = false;
  private static final int GROUP_TIP_RATE = 5;

  private static final ProvinceEnum ALBERTA_ENUM = ProvinceEnum.AB;
  private static final String PRODUCT_NAME = "Latte";
  private static final BigDecimal PRODUCT_PRICE = new BigDecimal("5");
  private static final ProductInfo PRODUCT_INFO = new ProductInfo(PRODUCT_PRICE, new Ingredients(0, 0, 0, 0));
  private static final BigDecimal TIP_AMOUNT = new BigDecimal("0.50");
  private static final BigDecimal TAX_AMOUNT = new BigDecimal("0.50");
  private List<Product> productList;


  @BeforeEach
  void setUp() {
    productList = new ArrayList<>();
    Product product = new Product(PRODUCT_NAME, PRODUCT_INFO);
    Product secondProduct = new Product(PRODUCT_NAME, PRODUCT_INFO);
    productList.add(product);
    productList.add(secondProduct);
  }

  @Test
  void givenAListOfProductAndClientIsInGroup_whenGeneratingBillWithCanadianStrategy_thenReturnBillWithIncludingGroupTip() {
    CanadianPaymentStrategy canadianPaymentStrategy = new CanadianPaymentStrategy(ALBERTA_ENUM,
        GROUP_TIP_RATE);

    Bill bill = canadianPaymentStrategy.generateBill(productList, CLIENT_IS_IN_GROUP);

    assertEquals(BigDecimal.TEN, bill.subtotal());
    assertEquals(TIP_AMOUNT, bill.tip());
    assertEquals(TAX_AMOUNT, bill.taxes());
  }

  @Test
  void givenAListOfProductAndIsClientIsNotInGroup_whenGeneratingBillWithCanadianStrategy_thenReturnBillWithoutGroupTip() {
    CanadianPaymentStrategy canadianPaymentStrategy = new CanadianPaymentStrategy(ALBERTA_ENUM,
        GROUP_TIP_RATE);


    Bill bill = canadianPaymentStrategy.generateBill(productList, CLIENT_NOT_IN_GROUP);

    assertEquals(BigDecimal.TEN, bill.subtotal());
    assertEquals(BigDecimal.ZERO, bill.tip());
    assertEquals(TAX_AMOUNT, bill.taxes());
  }
}
