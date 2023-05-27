package ca.ulaval.glo4002.cafe.domain.billing;

import java.math.BigDecimal;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public record Bill(
    List<Product> listOfProduct, BigDecimal subtotal, BigDecimal taxes, BigDecimal tip, BigDecimal total) {
}
