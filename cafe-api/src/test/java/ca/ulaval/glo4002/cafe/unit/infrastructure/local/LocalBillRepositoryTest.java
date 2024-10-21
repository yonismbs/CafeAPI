package ca.ulaval.glo4002.cafe.unit.infrastructure.local;

import ca.ulaval.glo4002.cafe.domain.billing.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.exception.NoBillException;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalBillRepository;

class LocalBillRepositoryTest {
    private static final String CLIENT_ID_STRING = "1";
    private static final ClientId CLIENT_ID_OBJECT = new ClientId(CLIENT_ID_STRING);

    private Bill bill;
    private BillRepository billRepository;

    @BeforeEach
    void setUp() {
        billRepository = new LocalBillRepository();

        bill = mock(Bill.class);
    }

    @Test
    void givenAClientId_whenFindingBill_thenReturnBillSaved() throws NoBillException {
        billRepository.addBill(CLIENT_ID_OBJECT, bill);

        Bill returnedBill = billRepository.findBillByClientId(CLIENT_ID_OBJECT);

        assertEquals(bill, returnedBill);
    }

    @Test
    void givenAClientId_whenFindingBillThatIsNotInRepository_thenNoBillException() {
        assertThrows(NoBillException.class, () -> billRepository.findBillByClientId(CLIENT_ID_OBJECT));
    }
}