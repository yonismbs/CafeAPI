package ca.ulaval.glo4002.cafe.domain.group.exception;

import jakarta.ws.rs.core.Response;

public class DuplicateGroupNameException extends GroupException {
    public DuplicateGroupNameException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "DUPLICATE_GROUP_NAME", "The specified group already made a reservation today.");
    }
}
