package ca.ulaval.glo4002.cafe.domain.group.exception;

import jakarta.ws.rs.core.Response;

public class NoGroupSeatsException extends GroupException {
    public NoGroupSeatsException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "NO_GROUP_SEATS", "There are no more seats reserved for that group.");
    }
}
