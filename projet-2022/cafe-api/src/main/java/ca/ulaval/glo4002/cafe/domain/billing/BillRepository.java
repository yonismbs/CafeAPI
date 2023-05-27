package ca.ulaval.glo4002.cafe.domain.billing;

import ca.ulaval.glo4002.cafe.domain.billing.exception.NoBillException;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;

public interface BillRepository {

    void addBill(ClientId clientId,Bill bill);

    Bill findBillByClientId(ClientId clientId) throws NoBillException;

    void reset();
}
