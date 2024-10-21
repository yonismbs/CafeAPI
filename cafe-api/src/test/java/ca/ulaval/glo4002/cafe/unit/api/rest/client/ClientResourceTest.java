package ca.ulaval.glo4002.cafe.unit.api.rest.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.api.rest.client.ClientResource;
import ca.ulaval.glo4002.cafe.application.service.ClientService;
import ca.ulaval.glo4002.cafe.application.service.order.OrderService;
import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.exception.NoBillException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import jakarta.ws.rs.core.Response;

class ClientResourceTest {

    private static final String LATTE = "Latte";
    private static final boolean CLIENT_CHECK_OUT = true;
    private static final BigDecimal SAME_PRICE = new BigDecimal("1.0");
    private static final GroupName CLIENT_GROUP_NAME = new GroupName("groupName");
    private static final String CLIENT_ID_STRING = "11111";
    private static final ClientId CLIENT_ID = new ClientId(CLIENT_ID_STRING);
    private static final String CLIENT_NAME = "Jason Momoa";
    private final List<String> PRODUCT_LIST = new ArrayList<>();
    private Client client;
    private ClientService clientService;
    private OrderService orderService;
    private ClientResource clientResource;
    private Bill bill;

    @BeforeEach
    void setup() throws InvalidClientIdException {
        client = mock(Client.class);
        when(client.getId()).thenReturn(CLIENT_ID);
        when(client.getName()).thenReturn(CLIENT_NAME);
        when(client.getGroupName()).thenReturn(CLIENT_GROUP_NAME);
        clientService = mock(ClientService.class);
        orderService = mock(OrderService.class);
        when(clientService.findClient(CLIENT_ID)).thenReturn(client);
        PRODUCT_LIST.add(LATTE);

        clientResource = new ClientResource(clientService, orderService);
    }


    @Test
    void whenTryingToLocateClient_thenShouldReturnClient() throws InvalidClientIdException {

        Response response = clientResource.getCustomer(CLIENT_ID_STRING);

        assertNotNull(response.getEntity());
    }


    @Test
    void givenACustomerId_whenGettingHisPersonalOrder_thenShouldReturnClientOrder()
            throws InvalidClientIdException {
        when(orderService.listAllOrdersByCustomerId(CLIENT_ID)).thenReturn(PRODUCT_LIST);

        Response response = clientResource.getOrders(CLIENT_ID_STRING);

        assertNotNull(response.getEntity());
    }

    @Test
    void givenACustomerId_whenGettingCustomerBill_thenShouldReturnCustomerBill()
            throws InvalidClientIdException, NoBillException {
        bill = mock(Bill.class);
        when(bill.subtotal()).thenReturn(SAME_PRICE);
        when(bill.taxes()).thenReturn(SAME_PRICE);
        when(bill.tip()).thenReturn(SAME_PRICE);
        when(bill.total()).thenReturn(SAME_PRICE);
        when(clientService.findClient(CLIENT_ID)).thenReturn(client);
        when(clientService.getClientBill(CLIENT_ID)).thenReturn(bill);
        when(client.isCheckedOut()).thenReturn(CLIENT_CHECK_OUT);

        Response response = clientResource.getBill(CLIENT_ID_STRING);

        assertNotNull(response.getEntity());
    }

}
