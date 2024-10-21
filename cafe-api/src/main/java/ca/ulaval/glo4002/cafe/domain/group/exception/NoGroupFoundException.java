package ca.ulaval.glo4002.cafe.domain.group.exception;

import jakarta.ws.rs.core.Response;

public class NoGroupFoundException extends GroupException {
    public NoGroupFoundException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "NO_RESERVATIONS_FOUND", "No reservations were made today for that group.");
    }
}
