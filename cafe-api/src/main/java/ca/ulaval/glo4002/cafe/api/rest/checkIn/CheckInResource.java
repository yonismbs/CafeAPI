package ca.ulaval.glo4002.cafe.api.rest.checkIn;

import ca.ulaval.glo4002.cafe.api.rest.checkIn.dto.CheckoutDTO;
import ca.ulaval.glo4002.cafe.api.rest.client.ClientInformation;
import ca.ulaval.glo4002.cafe.api.rest.exceptions.InvalidRequestParameterException;
import ca.ulaval.glo4002.cafe.application.service.CheckInService;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.DuplicateClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CheckInResource {

    private static final String LOCATION = "Location";
    private static final String CUSTOMERS_PATH = "/customers/";
    private static final String BILL_PATH = "/bill";
    private final CheckInService checkInService;

    public CheckInResource(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @Path("check-in")
    @POST
    public Response checkInClient(final ClientInformation clientInformation)
            throws InsufficientSeatsException, NoGroupSeatsException, NoGroupFoundException, DuplicateClientIdException {
        if (clientInformation.customer_name().isBlank() || clientInformation.customer_id().isBlank()) {
            throw new InvalidRequestParameterException();
        }

        Client client = checkInService.checkInClient(new ClientId(clientInformation.customer_id()),
            clientInformation.customer_name(),
            new GroupName(clientInformation.group_name()));
        return Response
            .status(Response.Status.CREATED)
            .header(LOCATION, CUSTOMERS_PATH + client.getId().id())
            .build();
    }

    @POST
    @Path("checkout")
    public Response checkoutClient(final CheckoutDTO checkoutDTO) throws Exception {
        ClientId clientId = new ClientId(checkoutDTO.customer_id());
        checkInService.checkOutClient(clientId);
        return Response
              .status(Status.CREATED)
              .header(LOCATION, CUSTOMERS_PATH + checkoutDTO.customer_id() + BILL_PATH)
              .build();
    }

}
