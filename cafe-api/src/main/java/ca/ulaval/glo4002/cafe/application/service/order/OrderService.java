package ca.ulaval.glo4002.cafe.application.service.order;

import java.util.Collection;
import java.util.List;
import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.domain.billing.order.Order;
import ca.ulaval.glo4002.cafe.domain.billing.order.OrderFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.inventory.exception.InsufficientIngredientException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.ClientRepository;
import ca.ulaval.glo4002.cafe.domain.client.exception.ClientIsCheckedOutException;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.domain.product.ProductFactory;
import ca.ulaval.glo4002.cafe.domain.product.exception.InvalidMenuOrderException;

public class OrderService {

    private final OrderFactory orderFactory;
    private final ClientRepository clientRepository;
    private final CafeService cafeService;
    private final ProductFactory productFactory;

    public OrderService(OrderFactory orderFactory, ClientRepository clientRepository, CafeService cafeService, ProductFactory productFactory) {
        this.orderFactory = orderFactory;
        this.clientRepository = clientRepository;
        this.cafeService = cafeService;
        this.productFactory = productFactory;
    }

    public OrderService(ClientRepository clientRepository, CafeService cafeService) {
        this(new OrderFactory(), clientRepository, cafeService, new ProductFactory());
    }

    public OrderService(ClientRepository clientRepository, CafeService cafeService, ProductFactory productFactory) {
        this(new OrderFactory(), clientRepository, cafeService, productFactory);
    }

    public void createOrder(List<String> incomingProducts, ClientId clientId)
        throws InvalidClientIdException, InsufficientIngredientException, InvalidMenuOrderException, ClientIsCheckedOutException {
        Client client = clientRepository.findClient(clientId);

        if (client.isCheckedOut()) throw new ClientIsCheckedOutException();

        List<Product> listOfChosenProducts = productFactory.createAll(incomingProducts);

        Order order = orderFactory.create(listOfChosenProducts);
        cafeService.removeStock(order.calculateIngredientCost());
        client.addOrder(order);
        clientRepository.addClient(client);
    }

    public List<String> listAllOrdersByCustomerId(ClientId clientId) throws InvalidClientIdException {
        Client client = clientRepository.findClient(clientId);
        List<Order> orderList = client.getOrders();

        return orderList.stream().map(Order::getProductNames).flatMap(Collection::stream).toList();
    }
}
