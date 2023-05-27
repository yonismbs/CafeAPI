package ca.ulaval.glo4002.cafe.unit.domain.seat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatState;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;

class SeatTest {

    private static final int SEAT_NB_ONE = 1;
    private static final GroupName GROUP_NAME = new GroupName("groupName");
    private static final GroupName DIFFERENT_GROUP = new GroupName("group2");
    private static final ClientId CLIENT_ID = new ClientId("1");

    @Test
    void whenCreatingNewSeat_thenSeatIsAvailable() {
        Seat seat = new Seat(SEAT_NB_ONE);

        assertEquals(SeatState.Available, seat.getState());
    }

    @Test
    void whenAssignedClientIdToSeat_thenAddClientIdToSeatAndChangeStatus() {
        Seat seat = new Seat(SEAT_NB_ONE);
        seat.assignClientId(CLIENT_ID);
        assertEquals( CLIENT_ID ,seat.getClientId());
        assertEquals(SeatState.Occupied, seat.getState());
    }

    @Test
    void givenNonAvailableState_whenReset_thenStateIsSetToAvailable() {
        Seat seat = new Seat(SEAT_NB_ONE);
        seat.setState(SeatState.Occupied);

        seat.reset();
        assertEquals(SeatState.Available, seat.getState());
    }

    @Test
    void givenNonVoidGroupName_whenReset_thenGroupNameIsSetToVoid() {
        Seat seat = new Seat(SEAT_NB_ONE);
        seat.setGroupName(GROUP_NAME);
        seat.reset();

        assertEquals(GroupName.VOID, seat.getGroupName());
    }

    @Test
    void givenSeatIsNotReserved_whenIsReservedForGroup_thenReturnFalse() {
        Seat seat = new Seat(SEAT_NB_ONE);
        seat.setState(SeatState.Occupied);
        seat.setGroupName(GROUP_NAME);

        assertFalse(seat.isReservedForGroup(GROUP_NAME));
    }

    @Test
    void givenSeatHasNoAssociatedGroup_whenIsReservedForGroup_thenReturnFalse() {
        Seat seat = new Seat(SEAT_NB_ONE);
        seat.setState(SeatState.Reserved);
        seat.setGroupName(GroupName.VOID);

        assertFalse(seat.isReservedForGroup(GROUP_NAME));
    }

    @Test
    void givenSeatHasMismatchedGroupName_whenIsReservedForGroup_thenReturnFalse() {
        Seat seat = new Seat(SEAT_NB_ONE);
        seat.setState(SeatState.Reserved);
        seat.setGroupName(DIFFERENT_GROUP);

        assertFalse(seat.isReservedForGroup(GROUP_NAME));
    }

    @Test
    void givenSeatHasSameGroupNameAndIsReserved_whenIsReservedForGroup_thenReturnTrue() {
        Seat seat = new Seat(SEAT_NB_ONE);
        seat.setState(SeatState.Reserved);
        seat.setGroupName(GROUP_NAME);

        assertTrue(seat.isReservedForGroup(GROUP_NAME));
    }
}