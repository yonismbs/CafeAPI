package ca.ulaval.glo4002.cafe.api.rest.mappers.domain;

import ca.ulaval.glo4002.cafe.api.rest.exceptions.ExceptionDTO;
import ca.ulaval.glo4002.cafe.domain.exception.DomainException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DomainExceptionMapper implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.error = e.getErrorName();
        exceptionDTO.description = e.getErrorDescription();

        return Response.status(e.getStatus())
            .type(MediaType.APPLICATION_JSON)
            .entity(exceptionDTO)
            .build();
    }
}
