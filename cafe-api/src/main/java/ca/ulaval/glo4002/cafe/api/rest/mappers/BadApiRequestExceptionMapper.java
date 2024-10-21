package ca.ulaval.glo4002.cafe.api.rest.mappers;

import ca.ulaval.glo4002.cafe.api.rest.exceptions.BadApiRequestException;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.ExceptionDTO;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadApiRequestExceptionMapper implements ExceptionMapper<BadApiRequestException> {

    @Override
    public Response toResponse(BadApiRequestException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.error = e.getErrorName();
        exceptionDTO.description = e.getErrorDescription();

        return Response.status(e.getStatus())
            .type(MediaType.APPLICATION_JSON)
            .entity(exceptionDTO)
            .build();
    }
}
