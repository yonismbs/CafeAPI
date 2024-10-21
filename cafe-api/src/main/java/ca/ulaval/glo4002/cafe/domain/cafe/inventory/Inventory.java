package ca.ulaval.glo4002.cafe.domain.cafe.inventory;

import ca.ulaval.glo4002.cafe.domain.cafe.inventory.exception.InsufficientIngredientException;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;

public class Inventory {

    private Ingredients resourceStock;

    public Inventory(Ingredients resourceStock) {
        this.resourceStock = resourceStock;
    }

    public Inventory() {
        this(new Ingredients());
    }

    public void addStock(Ingredients ingredients) {
        resourceStock = resourceStock.add(ingredients);
    }

    public void removeStock(Ingredients ingredientCost) throws InsufficientIngredientException {
        if (resourceStock.anyIngredientLessThan(ingredientCost)) throw new InsufficientIngredientException();
        resourceStock = resourceStock.subtract(ingredientCost);
    }
    public void resetStock() {
        resourceStock = new Ingredients();
    }
    public Ingredients getResourceStock() {
        return this.resourceStock;
    }
}
