package ca.ulaval.glo4002.cafe.unit.domain.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.domain.cafe.inventory.Inventory;
import ca.ulaval.glo4002.cafe.domain.cafe.inventory.exception.InsufficientIngredientException;
import ca.ulaval.glo4002.cafe.domain.product.Ingredients;

class InventoryTest {

    private static Ingredients resourceStock;
    private static Ingredients resourceDelta;
    Inventory inventory;

    @BeforeEach
    void setup() {
        resourceStock = mock(Ingredients.class);
        resourceDelta = mock(Ingredients.class);
        inventory = new Inventory(resourceStock);
    }

    @Test
    void whenAddingStock_thenStockIsIncreased() {
        inventory.addStock(resourceDelta);

        verify(resourceStock).add(resourceDelta);
    }

    @Test
    void whenRemoveStock_thenStockIsDecreased() throws InsufficientIngredientException {
        inventory.removeStock(resourceDelta);

        verify(resourceStock).subtract(resourceDelta);
    }

    @Test
    void givenNotEnoughStock_whenRemoveStock_thenInsufficientIngredientExceptionIsThrown() {
        when(resourceStock.anyIngredientLessThan(resourceDelta)).thenReturn(true);

        assertThrows(InsufficientIngredientException.class, () -> inventory.removeStock(resourceDelta));
    }
}