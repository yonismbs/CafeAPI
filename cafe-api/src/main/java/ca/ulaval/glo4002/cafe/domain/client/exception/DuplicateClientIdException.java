package ca.ulaval.glo4002.cafe.domain.client.exception;

import jakarta.ws.rs.core.Response;

public class DuplicateClientIdException extends ClientException {
    public DuplicateClientIdException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "DUPLICATE_CUSTOMER_ID", "The customer cannot visit the café multiple times in the same day.");
    }
}
