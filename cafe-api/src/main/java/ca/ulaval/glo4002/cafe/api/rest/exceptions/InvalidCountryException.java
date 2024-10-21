package ca.ulaval.glo4002.cafe.api.rest.exceptions;

public class InvalidCountryException extends BadApiRequestException {
    public InvalidCountryException() {
        super("INVALID_COUNTRY", "The specified country is invalid.");
    }
}

