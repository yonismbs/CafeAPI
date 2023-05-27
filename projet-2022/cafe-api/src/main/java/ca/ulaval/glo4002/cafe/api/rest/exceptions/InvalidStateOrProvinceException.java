package ca.ulaval.glo4002.cafe.api.rest.exceptions;

public class InvalidStateOrProvinceException extends BadApiRequestException {
    public InvalidStateOrProvinceException() {
        super("INVALID_STATE_OR_PROVINCE", "The state or province you provided doesn't exist.");
    }
}
