package ca.ulaval.glo4002.cafe.domain.client.exception;

import jakarta.ws.rs.core.Response;

public class ClientIsCheckedOutException extends ClientException {
    public ClientIsCheckedOutException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "CUSTOMER_IS_CHECKED_OUT", "The customer has already checked out from the café.");
    }
}
