package ca.ulaval.glo4002.cafe.domain.billing.order;

import java.util.List;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public class OrderFactory {

    public Order create(List<Product> productList) {return new Order(productList);}

}
