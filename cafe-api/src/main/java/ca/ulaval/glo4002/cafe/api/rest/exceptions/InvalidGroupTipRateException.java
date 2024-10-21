package ca.ulaval.glo4002.cafe.api.rest.exceptions;

public class InvalidGroupTipRateException extends BadApiRequestException {
    public InvalidGroupTipRateException() {
        super("INVALID_GROUP_TIP_RATE", "The group tip rate must be set to a value between 0 to 100.");
    }
}
