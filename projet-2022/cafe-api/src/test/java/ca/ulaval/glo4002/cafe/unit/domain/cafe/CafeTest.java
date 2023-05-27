package ca.ulaval.glo4002.cafe.unit.domain.cafe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeConfig;
import ca.ulaval.glo4002.cafe.domain.cafe.CafeId;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;

public class CafeTest {

    private static final CafeId FRANCHISE_ID = CafeId.DEFAULT_CAFE_ID;
    private static final GroupName GROUP_NAME = new GroupName("GroupA");
    private static final int SEAT_COUNT = 4;
    private Cafe cafe;
    private ClientId clientId;
    private CafeConfig cafeConfig;
    private Layout layout;

    @BeforeEach
    void setup() {
        layout = mock(Layout.class);
        clientId = mock(ClientId.class);
        cafeConfig = mock(CafeConfig.class);

        cafe = new Cafe(FRANCHISE_ID, layout, cafeConfig);
    }

    @Test
    void whenAssigningClientIdToSeatFromGroup_thenAssignClientIdToSeatFromGroupIsCalledOnLayout() throws NoGroupSeatsException {
        cafe.assignClientIdToSeatFromGroup(clientId, GROUP_NAME);

        verify(layout).assignClientIdToSeatFromGroup(clientId, GROUP_NAME);
    }

    @Test
    void whenAssigningSeatToCustomer_thenAssignClientIdToSeatIsCalledOnLayout() throws InsufficientSeatsException {
        cafe.assignClientIdToSeat(clientId);

        verify(layout).assignClientIdToSeat(clientId);
    }

    @Test
    void whenReserveSeats_thenReserveSeatsIsCalledOnLayout() throws InsufficientSeatsException {
        cafe.reserveSeats(SEAT_COUNT, GROUP_NAME);

        verify(layout).reserveSeats(SEAT_COUNT, GROUP_NAME, cafe.getReservationStrategy());
    }

    @Test
    void whenResetLayout_thenResetCubesIsCalled() {
        cafe.resetAllSeats();

        verify(layout).resetAllSeats();
    }
}
