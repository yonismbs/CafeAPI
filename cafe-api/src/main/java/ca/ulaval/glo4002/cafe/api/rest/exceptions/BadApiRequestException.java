package ca.ulaval.glo4002.cafe.api.rest.exceptions;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;

public class BadApiRequestException extends BadRequestException {

    private final Response.Status status;
    private final String errorName;
    private final String errorDescription;

    public BadApiRequestException(String errorName, String errorDescription) {
        this.status = Response.Status.BAD_REQUEST;
        this.errorName = errorName;
        this.errorDescription = errorDescription;
    }

    public Response.Status getStatus() {
        return status;
    }

    public String getErrorName() {
        return errorName;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
