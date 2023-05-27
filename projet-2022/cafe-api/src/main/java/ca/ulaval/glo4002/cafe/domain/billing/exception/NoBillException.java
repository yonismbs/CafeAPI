package ca.ulaval.glo4002.cafe.domain.billing.exception;

import ca.ulaval.glo4002.cafe.domain.exception.DomainException;
import jakarta.ws.rs.core.Response;

public class NoBillException extends DomainException {
    public NoBillException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "NO_BILL", "The customer needs to do a checkout before receiving his bill.");
    }
}
