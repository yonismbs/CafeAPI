package ca.ulaval.glo4002.cafe.application.service.order.billing;

import java.util.List;
import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.BillFactory;
import ca.ulaval.glo4002.cafe.domain.billing.BillRepository;
import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeId;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeRepository;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.ClientRepository;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.product.Product;

public class BillService {

    private final ClientRepository localClientRepository;
    private final BillFactory billFactory;
    private final CafeRepository cafeRepository;
    private final BillRepository billRepository;

    public BillService(ClientRepository localClientRepository, BillFactory billFactory,
        CafeRepository cafeRepository, BillRepository billRepository) {
        this.billRepository = billRepository;
        this.localClientRepository = localClientRepository;
        this.billFactory = billFactory;
        this.cafeRepository = cafeRepository;
    }

    public void createReceiptForClient(ClientId clientId) throws InvalidClientIdException {
        Client client = localClientRepository.findClient(clientId);
        List<Product> clientsProductList = client.getListOfProducts();

        Cafe cafe = cafeRepository.find(CafeId.DEFAULT_CAFE_ID);
        boolean isClientInGroup = client.isClientInGroup();

        Bill clientBill = billFactory.create(clientsProductList, cafe.getBillStrategy(), isClientInGroup);
        client.setCheckedOut(true);
        billRepository.addBill(clientId, clientBill);
    }

    public void archive() {
        billRepository.reset();
    }
}
