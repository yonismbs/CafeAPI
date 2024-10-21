package ca.ulaval.glo4002.cafe.domain.cafe.exception;

import ca.ulaval.glo4002.cafe.domain.exception.DomainException;

public class CafeException extends DomainException {
    public CafeException(int status, String errorName, String errorDescription) {
        super(status, errorName, errorDescription);
    }
}
