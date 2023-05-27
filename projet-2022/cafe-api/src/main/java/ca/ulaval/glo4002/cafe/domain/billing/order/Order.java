package ca.ulaval.glo4002.cafe.domain.billing.order;

import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public record Order(List<Product> productList) {

    public List<Product> getProductList() {
        return productList;
    }

    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        for (Product product : productList) {
            productNames.add(product.name());
        }
        return productNames;
    }

    public Ingredients calculateIngredientCost() {
        Ingredients ingredientCost = new Ingredients(0,0,0,0);
        for (Product product : productList) {
            ingredientCost = ingredientCost.add(product.ingredientCost());
        }
        return ingredientCost;
    }
}
