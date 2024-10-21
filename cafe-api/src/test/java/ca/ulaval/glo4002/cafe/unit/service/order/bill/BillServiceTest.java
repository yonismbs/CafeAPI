package ca.ulaval.glo4002.cafe.unit.service.order.bill;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.cafe.domain.billing.BillRepository;
import ca.ulaval.glo4002.cafe.domain.billing.TaxationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.application.service.order.billing.BillService;
import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.BillFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeId;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.product.Product;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalClientRepository;

class BillServiceTest {
    private static final boolean CLIENT_IN_GROUP_BOOL = true;
    private static final String CLIENT_ID_STRING = "1";
    private static final ClientId CLIENT_ID_OBJECT = new ClientId(CLIENT_ID_STRING);
    private LocalClientRepository localClientRepository;
    private CafeRepository cafeRepository;
    private BillService billService;
    private BillFactory billFactory;
    private TaxationStrategy taxationStrategy;
    private Client client;
    private Product product;
    private Cafe cafe;
    private Bill bill;
    private BillRepository billRepository;
    private List<Product> listOfProduct;

    @BeforeEach
    public void setUp() {
        client = mock(Client.class);
        product = mock(Product.class);
        cafe = mock(Cafe.class);
        bill = mock(Bill.class);

        billRepository = mock(BillRepository.class);
        localClientRepository = mock(LocalClientRepository.class);
        cafeRepository = mock(CafeRepository.class);
        taxationStrategy = mock(TaxationStrategy.class);

        listOfProduct = new ArrayList<>();
        listOfProduct.add(product);

        billFactory = new BillFactory();
        billService = new BillService(localClientRepository, billFactory, cafeRepository, billRepository);
    }

    @Test
    void givenNonExistingClient_WhenClientQuitsTheCafe_ThenInvalidClientIdExceptionIsThrown() throws InvalidClientIdException {
        when(localClientRepository.findClient(CLIENT_ID_OBJECT)).thenThrow(InvalidClientIdException.class);

        assertThrows(InvalidClientIdException.class, () -> billService.createReceiptForClient(CLIENT_ID_OBJECT));
    }

    @Test
    void givenExistingClient_WhenClientQuitsTheCafe_ThenBillIsGeneratedInRepo() throws InvalidClientIdException {
        when(localClientRepository.findClient(CLIENT_ID_OBJECT)).thenReturn(client);
        when(client.getListOfProducts()).thenReturn(listOfProduct);
        when(cafeRepository.find(CafeId.DEFAULT_CAFE_ID)).thenReturn(cafe);
        when(client.isClientInGroup()).thenReturn(CLIENT_IN_GROUP_BOOL);
        when(cafe.getBillStrategy()).thenReturn(taxationStrategy);
        when(taxationStrategy.generateBill(listOfProduct, CLIENT_IN_GROUP_BOOL)).thenReturn(bill);
        doNothing().when(client).setCheckedOut(anyBoolean());

        billService.createReceiptForClient(CLIENT_ID_OBJECT);

        verify(billRepository).addBill(CLIENT_ID_OBJECT, bill);
    }

}
