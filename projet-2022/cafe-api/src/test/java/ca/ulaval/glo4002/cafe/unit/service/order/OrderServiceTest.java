package ca.ulaval.glo4002.cafe.unit.service.order;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.application.service.order.OrderService;
import ca.ulaval.glo4002.cafe.domain.billing.order.Order;
import ca.ulaval.glo4002.cafe.domain.billing.order.OrderFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.inventory.exception.InsufficientIngredientException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.ClientRepository;
import ca.ulaval.glo4002.cafe.domain.client.exception.ClientIsCheckedOutException;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.product.ProductFactory;
import ca.ulaval.glo4002.cafe.domain.product.exception.InvalidMenuOrderException;

class OrderServiceTest {
    private static final ClientId CUSTOMER_ID = new ClientId("1111");
    private static final String PRODUCT_NAME = "LATTE";
    private static final List<String> INCOMING_PRODUCTS = List.of(PRODUCT_NAME);
    public static final boolean CLIENT_IS_CHECKED_OUT = true;
    private Client client;
    private Order order;
    private ClientRepository clientRepository;
    private ProductFactory productFactory;
    private CafeService cafeService;
    private OrderFactory orderFactory;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
      cafeService = mock(CafeService.class);
      productFactory = mock(ProductFactory.class);
      clientRepository = mock(ClientRepository.class);
      orderFactory = mock(OrderFactory.class);
      orderService = new OrderService(orderFactory,clientRepository,cafeService,productFactory);

      order = mock(Order.class);
      client = mock(Client.class);
    }

    @Test
    void givenAListOfProductAndCustomerId_whenCreatingOrder_thenCreateNewOrder()
        throws InvalidClientIdException, InsufficientIngredientException, InvalidMenuOrderException, ClientIsCheckedOutException {
      when(clientRepository.findClient(any())).thenReturn(client);
      when(orderFactory.create(anyList())).thenReturn(order);

      orderService.createOrder(INCOMING_PRODUCTS, CUSTOMER_ID);

      verify(client).addOrder(order);
    }

    @Test
    void givenACheckedOutCustomer_whenCreatingOrder_thenClientIsCheckedOutExceptionIsThrown() throws InvalidClientIdException {
      when(clientRepository.findClient(any())).thenReturn(client);
      when(client.isCheckedOut()).thenReturn(CLIENT_IS_CHECKED_OUT);

      assertThrows(ClientIsCheckedOutException.class, () -> orderService.createOrder(INCOMING_PRODUCTS, CUSTOMER_ID));
    }

    @Test
    void givenACustomerID_whenGettingAllCustomerProduct_thenReturnListOfProduct() throws InvalidClientIdException {
        when(clientRepository.findClient(CUSTOMER_ID)).thenReturn(client);

        when(client.getOrders()).thenReturn(List.of(order));

        assertNotNull(orderService.listAllOrdersByCustomerId(CUSTOMER_ID));
    }
}