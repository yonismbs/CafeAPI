package ca.ulaval.glo4002.cafe.domain.exception;

public class DomainException extends Exception {

    private final int status;
    private final String errorName;
    private final String errorDescription;

    public DomainException(int status, String errorName, String errorDescription) {
        this.status = status;
        this.errorName = errorName;
        this.errorDescription = errorDescription;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public String getErrorName() {
        return errorName;
    }
}
