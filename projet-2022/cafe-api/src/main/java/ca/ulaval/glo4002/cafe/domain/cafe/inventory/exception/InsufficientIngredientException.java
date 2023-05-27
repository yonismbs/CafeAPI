package ca.ulaval.glo4002.cafe.domain.cafe.inventory.exception;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.CafeException;
import jakarta.ws.rs.core.Response;

public class InsufficientIngredientException extends CafeException {
    public InsufficientIngredientException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "INSUFFICIENT_INGREDIENTS_EXCEPTION", "We lack the necessary number of ingredients to fulfill your order.");
    }
}
