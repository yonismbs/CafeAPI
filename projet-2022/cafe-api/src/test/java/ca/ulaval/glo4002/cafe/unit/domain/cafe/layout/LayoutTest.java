package ca.ulaval.glo4002.cafe.unit.domain.cafe.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.Layout;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.exception.NoAssignableSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatState;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.DefaultReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;

class LayoutTest {

    public static final int SEATS_6 = 6;
    public static final int ZERO_AVAIlABLE_SEAT = 0;
    public static final int MAX_SEAT_AVAILABLE = 4;
    public static final int TWO_AVAILABLE_SEAT = 2;
    public static final int FOUR_AVAILABLE_SEAT = 4;
    private final static int DEFAULT_CUBE_COUNT = 4;
    private final static int DEFAULT_SEAT_COUNT = 4;
    private final static GroupName GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private static final int CUBE_SIZE_4 = 4;
    private static final int SEAT_NUMBER = 1;
    private static final int CUBE_INDEX_0 = 0;
    private static final int CUBE_INDEX_1 = 1;
    private static final int CUBE_INDEX_2 = 2;
    private static final int CUBE_INDEX_3 = 3;
    private ClientId clientId;
    private Seat seat;
    private Layout layout;
    private Cube cube;

    @BeforeEach
    void setup() {
        seat = mock(Seat.class);
        clientId = mock(ClientId.class);
        cube = mock(Cube.class);
        layout = new Layout(Arrays.asList(cube, cube, cube, cube));
    }

    @Test
    void whenCreatingNewFranchise_thenCubeContainerIsNotNull() {
        assertNotNull(layout.getCubes());
    }

    @Test
    void whenCreatingFranchise_thenCubeContainerHasFourCubeEmplacements() {
        assertEquals(DEFAULT_CUBE_COUNT, layout.getCubeCount());
    }

    @Test
    void givenNotEnoughAvailableSeats_whenReservingSeats_thenInsufficientSeatsExceptionIsThrown() {
        givenCubeAvailabilityOnLayout(CUBE_SIZE_4, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE);

        assertThrows(InsufficientSeatsException.class,
                () -> layout.reserveSeats(layout.getMaxCapacity() + 1, GROUP_NAME, new DefaultReservationStrategy()));
    }

    @Test
    void givenEnoughAvailableSeats_whenReservingSeats_thenNoExceptionIsThrown() {
        givenCubeAvailabilityOnLayout(CUBE_SIZE_4, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE);

        assertDoesNotThrow(() -> layout.reserveSeats(layout.getMaxCapacity(), GROUP_NAME, new DefaultReservationStrategy()));
    }

    @Test
    void givenJustEnoughAvailableSeats_whenReservingSeats_thenSeatsBecomeReserved() throws InsufficientSeatsException {
        givenCubeAvailabilityOnLayout(CUBE_SIZE_4, TWO_AVAILABLE_SEAT, FOUR_AVAILABLE_SEAT, ZERO_AVAIlABLE_SEAT, ZERO_AVAIlABLE_SEAT);

        layout.reserveSeats(SEATS_6, GROUP_NAME, new DefaultReservationStrategy());

        verify(seat, times(SEATS_6)).setState(SeatState.Reserved);
    }

    @Test
    void givenAvailableSeats_whenReservingSeats_thenSeatsHaveGroupName() throws InsufficientSeatsException {
        givenCubeAvailabilityOnLayout(CUBE_SIZE_4, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE, MAX_SEAT_AVAILABLE);

        layout.reserveSeats(layout.getMaxCapacity(), GROUP_NAME, new DefaultReservationStrategy());

        verify(seat, times(layout.getMaxCapacity())).setGroupName(GROUP_NAME);
    }

    @Test
    void whenReserveSeats_thenGetAvailableSeatsIsCalledOnCube() throws InsufficientSeatsException {
        ReservationStrategy reservationStrategy = mock(ReservationStrategy.class);
        when(cube.getAvailableSeats(anyInt(), any())).thenReturn(Arrays.asList(seat, seat, seat, seat));

        layout.reserveSeats(DEFAULT_SEAT_COUNT, GROUP_NAME, reservationStrategy);

        verify(cube).getAvailableSeats(DEFAULT_SEAT_COUNT, reservationStrategy);
    }

    @Test
    void givenAReservedGroup_whenAssigningClientIdToSeatFromGroup_thenAssignClientToSeatByGroupNameIsCalledOnCubeWithReservation()
            throws NoGroupSeatsException, NoAssignableSeatsException {
        when(cube.assignClientIdToSeatByGroupName(clientId, GROUP_NAME)).thenThrow(new NoAssignableSeatsException());
        Cube cubeWithReservation = mock(Cube.class);
        when(cubeWithReservation.assignClientIdToSeatByGroupName(clientId, GROUP_NAME)).thenReturn(SEAT_NUMBER);
        layout.getCubes().set(CUBE_INDEX_1, cubeWithReservation);

        layout.assignClientIdToSeatFromGroup(clientId, GROUP_NAME);

        verify(cube).assignClientIdToSeatByGroupName(clientId, GROUP_NAME);
        verify(cubeWithReservation).assignClientIdToSeatByGroupName(clientId, GROUP_NAME);
    }

    @Test
    void givenAReservedGroup_whenAssigningClientIdToSeatFromGroup_thenReservedSeatNumberIsReturned() throws NoGroupSeatsException, NoAssignableSeatsException {
        when(cube.assignClientIdToSeatByGroupName(clientId, GROUP_NAME)).thenThrow(new NoAssignableSeatsException());
        Cube cubeWithReservation = mock(Cube.class);
        when(cubeWithReservation.assignClientIdToSeatByGroupName(clientId, GROUP_NAME)).thenReturn(SEAT_NUMBER);
        layout.getCubes().set(CUBE_INDEX_1, cubeWithReservation);

        assertEquals(SEAT_NUMBER, layout.assignClientIdToSeatFromGroup(clientId, GROUP_NAME));
    }

    @Test
    void givenAReservedGroupWithNoSpace_whenAssigningClientIdToSeatFromGroup_thenNoGroupSeatExceptionIsThrown() throws NoAssignableSeatsException {
        when(cube.assignClientIdToSeatByGroupName(clientId, GROUP_NAME)).thenThrow(new NoAssignableSeatsException());

        assertThrows(NoGroupSeatsException.class, () -> layout.assignClientIdToSeatFromGroup(clientId, GROUP_NAME));
    }

    @Test
    void whenAssignClientIdToSeat_thenAssignClientIdToSeatIsCalledOnCube() throws InsufficientSeatsException, NoAssignableSeatsException {
        layout.assignClientIdToSeat(clientId);

        verify(cube).assignClientIdToSeat(clientId);
    }

    @Test
    void givenNoAssignableSeats_whenAssignClientIdToSeat_thenInsufficientSeatsExceptionIsThrown() throws NoAssignableSeatsException {
        when(cube.assignClientIdToSeat(clientId)).thenThrow(NoAssignableSeatsException.class);

        assertThrows(InsufficientSeatsException.class, () -> layout.assignClientIdToSeat(clientId));
    }

    @Test
    void whenDisbandingGroupIfEmpty_thenGroupSeatsAreReset() {
        when(cube.getSeats()).thenReturn(List.of(seat));
        when(seat.getGroupName()).thenReturn(GROUP_NAME);
        when(seat.getState()).thenReturn(SeatState.Reserved);

        layout.disbandGroupIfEmpty(GROUP_NAME);

        verify(seat, times(DEFAULT_SEAT_COUNT)).reset();
    }

    @Test
    void givenNotEmptyGroup_whenDisbandingGroupIfEmpty_thenNoSeatsAreReset() {
        when(cube.getSeats()).thenReturn(List.of(seat));
        when(seat.getGroupName()).thenReturn(GROUP_NAME);
        when(seat.getState()).thenReturn(SeatState.Reserved);
        Cube cubeNotEmpty = mock(Cube.class);
        Seat notEmptySeat = mock(Seat.class);
        when(cube.getSeats()).thenReturn(List.of(notEmptySeat));
        when(notEmptySeat.getGroupName()).thenReturn(GROUP_NAME);
        when(notEmptySeat.getState()).thenReturn(SeatState.Occupied);
        layout = new Layout(Arrays.asList(cube, cube, cube, cubeNotEmpty));

        layout.disbandGroupIfEmpty(GROUP_NAME);

        verify(seat, never()).reset();
        verify(notEmptySeat, never()).reset();
    }


    private void givenCubeAvailabilityOnLayout(int cubeSize, int availableInCube0, int availableInCube1, int availableInCube2, int availableInCube3) {
        Cube cube0 = mock(Cube.class);
        when(cube0.getAvailableSeats(anyInt(), any())).thenReturn(mockNReturnSeats(availableInCube0));
        when(cube0.getCubeSize()).thenReturn(cubeSize);
        Cube cube1 = mock(Cube.class);
        when(cube1.getAvailableSeats(anyInt(), any())).thenReturn(mockNReturnSeats(availableInCube1));
        when(cube1.getCubeSize()).thenReturn(cubeSize);
        Cube cube2 = mock(Cube.class);
        when(cube2.getAvailableSeats(anyInt(), any())).thenReturn(mockNReturnSeats(availableInCube2));
        when(cube2.getCubeSize()).thenReturn(cubeSize);
        Cube cube3 = mock(Cube.class);
        when(cube3.getAvailableSeats(anyInt(), any())).thenReturn(mockNReturnSeats(availableInCube3));
        when(cube3.getCubeSize()).thenReturn(cubeSize);
        layout.getCubes().set(CUBE_INDEX_0, cube0);
        layout.getCubes().set(CUBE_INDEX_1, cube1);
        layout.getCubes().set(CUBE_INDEX_2, cube2);
        layout.getCubes().set(CUBE_INDEX_3, cube3);
    }

    private List<Seat> mockNReturnSeats(int n) {
        ArrayList<Seat> seats = new ArrayList<>();
        for (int i = ZERO_AVAIlABLE_SEAT; i < n; i++) {
            seats.add(seat);
        }
        return seats;
    }

}