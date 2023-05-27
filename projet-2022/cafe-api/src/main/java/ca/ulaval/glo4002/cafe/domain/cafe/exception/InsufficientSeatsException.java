package ca.ulaval.glo4002.cafe.domain.cafe.exception;

import jakarta.ws.rs.core.Response;

public class InsufficientSeatsException extends CafeException {
    public InsufficientSeatsException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "INSUFFICIENT_SEATS", "There are currently no available seats. Please come back later.");
    }
}
