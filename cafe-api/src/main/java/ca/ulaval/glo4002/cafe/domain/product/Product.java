package ca.ulaval.glo4002.cafe.domain.product;

import java.math.BigDecimal;

public record Product(String name, ProductInfo productInfo) {

    public Ingredients ingredientCost() {
        return productInfo.ingredientCost();
    }

    public BigDecimal getPrice() {
        return productInfo.price();
    }
}
