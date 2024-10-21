package ca.ulaval.glo4002.cafe.domain.client.exception;


import jakarta.ws.rs.core.Response;

public class InvalidClientIdException extends ClientException {
    public InvalidClientIdException() {
        super(Response.Status.NOT_FOUND.getStatusCode(), "INVALID_CUSTOMER_ID", "The customer does not exist.");
    }
}
