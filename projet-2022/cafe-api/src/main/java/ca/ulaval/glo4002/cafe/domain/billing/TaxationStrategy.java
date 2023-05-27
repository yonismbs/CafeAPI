package ca.ulaval.glo4002.cafe.domain.billing;

import java.util.List;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public interface TaxationStrategy {
    Bill generateBill(List<Product> listOfOrder, boolean isClientInGroup) ;
}
