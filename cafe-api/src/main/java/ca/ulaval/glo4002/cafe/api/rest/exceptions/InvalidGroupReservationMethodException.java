package ca.ulaval.glo4002.cafe.api.rest.exceptions;

public class InvalidGroupReservationMethodException extends BadApiRequestException {
    public InvalidGroupReservationMethodException() {
        super("INVALID_GROUP_RESERVATION_METHOD", "The group reservation method is not supported.");
    }
}
