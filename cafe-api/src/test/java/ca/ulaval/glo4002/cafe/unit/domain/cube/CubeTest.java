package ca.ulaval.glo4002.cafe.unit.domain.cube;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.exception.NoAssignableSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;

class CubeTest {

    private static final String DEFAULT_CUBE_NAME = "Tinker Bell";
    private static final GroupName GROUP_NAME = new GroupName("GroupA");
    private static final int DEFAULT_SEAT_COUNT = 4;
    private Cube cube;
    private ClientId clientId;

    @BeforeEach
    public void setup() {
        clientId = mock(ClientId.class);
        cube = new Cube(DEFAULT_CUBE_NAME, Collections.emptyList());
    }

    @Test
    void whenGetNAvailableSeats_thenReservationStrategyIsCalled() {
        ReservationStrategy reservationStrategy = mock(ReservationStrategy.class);

        cube.getAvailableSeats(DEFAULT_SEAT_COUNT, reservationStrategy);

        verify(reservationStrategy).findAvailableCubeSeats(DEFAULT_SEAT_COUNT, cube.getSeats());
    }

    @Test
    void whenAssignClientByGroupName_thenCorrectSeatIsAssign() throws NoAssignableSeatsException {
        Seat groupSeat = mock(Seat.class);
        Seat notGroupSeat = mock(Seat.class);
        when(groupSeat.isReservedForGroup(GROUP_NAME)).thenReturn(true);
        when(notGroupSeat.isReservedForGroup(GROUP_NAME)).thenReturn(false);
        cube = new Cube(DEFAULT_CUBE_NAME, List.of(notGroupSeat, groupSeat));

        cube.assignClientIdToSeatByGroupName(clientId, GROUP_NAME);

        verify(groupSeat).assignClientId(clientId);
    }

    @Test
    void givenNoAssignableSeats_whenAssignClientByGroupName_thenNoAssignableSeatsExceptionIsThrown() {
        Seat notGroupSeat = mock(Seat.class);
        when(notGroupSeat.isReservedForGroup(GROUP_NAME)).thenReturn(false);
        cube = new Cube(DEFAULT_CUBE_NAME, List.of(notGroupSeat));

        assertThrows(NoAssignableSeatsException.class, () -> cube.assignClientIdToSeatByGroupName(clientId, GROUP_NAME));
    }

    @Test
    void whenAssignClientToSeat_thenCorrectSeatIsAssign() throws NoAssignableSeatsException {
        Seat availableSeat = mock(Seat.class);
        Seat notAvailableSeat = mock(Seat.class);
        when(availableSeat.isAvailable()).thenReturn(true);
        when(notAvailableSeat.isAvailable()).thenReturn(false);
        cube = new Cube(DEFAULT_CUBE_NAME, List.of(notAvailableSeat, availableSeat));

        cube.assignClientIdToSeat(clientId);

        verify(availableSeat).assignClientId(clientId);
    }

    @Test
    void givenNoAssignableSeats_whenAssignClientToSeat_thenNoAssignableSeatsExceptionIsThrown() {
        Seat notAvailableSeat = mock(Seat.class);
        when(notAvailableSeat.isAvailable()).thenReturn(false);
        cube = new Cube(DEFAULT_CUBE_NAME, List.of(notAvailableSeat));

        assertThrows(NoAssignableSeatsException.class, () -> cube.assignClientIdToSeat(clientId));
    }
}