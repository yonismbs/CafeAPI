package ca.ulaval.glo4002.cafe.api.rest;

import ca.ulaval.glo4002.cafe.application.service.CheckInService;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CloseResource {

    private final CheckInService checkInService;

    public CloseResource(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @Path("close")
    @POST
    public Response closeCafe() {
        checkInService.close();
        return Response.status(Response.Status.OK).build();
    }

}
