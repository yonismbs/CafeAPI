package ca.ulaval.glo4002.cafe.api.rest.exceptions;

public class InvalidRequestParameterException extends BadApiRequestException {
    public InvalidRequestParameterException() {
        super("INVALID_REQUEST_PARAMETER", "The request has an invalid parameter, please try again!");
    }
}
