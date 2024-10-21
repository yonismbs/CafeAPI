package ca.ulaval.glo4002.cafe.domain.product;

import java.math.BigDecimal;

public record ProductInfo(BigDecimal price, Ingredients ingredientCost) {
}
