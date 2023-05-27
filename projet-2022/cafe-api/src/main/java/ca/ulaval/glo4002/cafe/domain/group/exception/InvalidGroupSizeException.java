package ca.ulaval.glo4002.cafe.domain.group.exception;

import jakarta.ws.rs.core.Response;

public class InvalidGroupSizeException extends GroupException {
    public InvalidGroupSizeException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "INVALID_GROUP_SIZE", "Groups must reserve at least two seats.");
    }
}
