package ca.ulaval.glo4002.cafe.domain.group.exception;

import ca.ulaval.glo4002.cafe.domain.exception.DomainException;

public class GroupException extends DomainException {
    public GroupException(int status, String errorName, String errorDescription) {
        super(status, errorName, errorDescription);
    }
}
