package ca.ulaval.glo4002.cafe.infrastructure.local;

import java.util.HashMap;
import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.BillRepository;
import ca.ulaval.glo4002.cafe.domain.billing.exception.NoBillException;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;

public class LocalBillRepository implements BillRepository {

    private final HashMap<ClientId, Bill> billMap = new HashMap<>();

    @Override
    public void addBill(ClientId clientId, Bill bill) {
        billMap.put(clientId, bill);
    }

    @Override
    public Bill findBillByClientId(ClientId clientId) throws NoBillException {
        if(!billMap.containsKey(clientId)) throw new NoBillException();
        return billMap.get(clientId);
    }

    @Override
    public void reset() {
        billMap.clear();
    }
}
