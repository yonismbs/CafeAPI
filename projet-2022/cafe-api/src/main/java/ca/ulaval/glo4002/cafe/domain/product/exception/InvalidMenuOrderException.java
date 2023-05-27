package ca.ulaval.glo4002.cafe.domain.product.exception;

import ca.ulaval.glo4002.cafe.domain.exception.DomainException;
import jakarta.ws.rs.core.Response;

public class InvalidMenuOrderException extends DomainException {

    public InvalidMenuOrderException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "INVALID_MENU_ORDER", "An item ordered is not on the menu.");
    }
}
