package ca.ulaval.glo4002.cafe.api.rest.client;

import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.client.assembler.BillAssembler;
import ca.ulaval.glo4002.cafe.api.rest.client.assembler.ClientAssembler;
import ca.ulaval.glo4002.cafe.api.rest.client.assembler.OrderAssembler;
import ca.ulaval.glo4002.cafe.api.rest.client.dto.OrderDTO;
import ca.ulaval.glo4002.cafe.application.service.ClientService;
import ca.ulaval.glo4002.cafe.application.service.order.OrderService;
import ca.ulaval.glo4002.cafe.domain.billing.exception.NoBillException;
import ca.ulaval.glo4002.cafe.domain.cafe.inventory.exception.InsufficientIngredientException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.ClientIsCheckedOutException;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.product.exception.InvalidMenuOrderException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class ClientResource {

    private final ClientService clientService;
    private final OrderService orderService;
    private final ClientAssembler clientAssembler = new ClientAssembler();
    private final OrderAssembler orderAssembler = new OrderAssembler();
    private final BillAssembler billAssembler = new BillAssembler();

    public ClientResource(ClientService clientService, OrderService orderService) {
        this.clientService = clientService;
        this.orderService = orderService;
    }

    @Path("/{customer_id}")
    @GET
    public Response getCustomer(@PathParam("customer_id") String customer_id) throws InvalidClientIdException {
        Client client = clientService.findClient(new ClientId(customer_id));
        return Response.status(Response.Status.OK).entity(clientAssembler.toClientDTO(client)).build();
    }

    @Path("/{customer_id}/orders")
    @PUT
    public Response orderCoffee(OrderDTO orderDto, @PathParam("customer_id") String customer_id)
            throws InvalidClientIdException, InsufficientIngredientException, InvalidMenuOrderException, ClientIsCheckedOutException {
        ClientId clientId = new ClientId(customer_id);
        orderService.createOrder(orderDto.orders(), clientId);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/{customer_id}/orders")
    @GET
    public Response getOrders(@PathParam("customer_id") String customer_id) throws InvalidClientIdException {
        List<String> orders = orderService.listAllOrdersByCustomerId(new ClientId(customer_id));

        return Response.status(Response.Status.OK)
            .entity(orderAssembler.toOrderListDto(orders)).build();
    }

    @Path("/{customer_id}/bill")
    @GET
    public Response getBill(@PathParam("customer_id")String customer_id)
            throws InvalidClientIdException, NoBillException {
        ClientId clientId = new ClientId(customer_id);

        clientService.findClient(clientId);
        return Response.status(Response.Status.OK)
            .entity(billAssembler.toBillDTO(clientService.getClientBill(clientId))).build();
    }

}
