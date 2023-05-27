package ca.ulaval.glo4002.cafe.domain.billing;

import java.util.List;

import ca.ulaval.glo4002.cafe.domain.product.Product;

public class BillFactory {

    public Bill create(List<Product> productList, TaxationStrategy taxationStrategy, boolean isClientInGroup) {
        return taxationStrategy.generateBill(productList, isClientInGroup);
    }
}
