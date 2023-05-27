package ca.ulaval.glo4002.cafe.domain.client.exception;

import ca.ulaval.glo4002.cafe.domain.exception.DomainException;

public class ClientException extends DomainException {
    public ClientException(int status, String errorName, String errorDescription) {
        super(status, errorName, errorDescription);
    }
}
