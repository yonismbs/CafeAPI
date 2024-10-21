package ca.ulaval.glo4002.cafe.application.service;

import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductFactory;

public class ProductService {

    ProductFactory productFactory;
    public ProductService(ProductFactory productFactory) {
        this.productFactory = productFactory;
    }

    public void addMenu(Product product) {
        productFactory.addProduct(product);
    }

    public void resetProducts() {
        productFactory.resetMap();
    }

}
