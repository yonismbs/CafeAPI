package ca.ulaval.glo4002.cafe.unit.domain.seat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatState;

class SeatFactoryTest {

    private static final int SEAT_COUNT = 2;
    private static final int EXPECTED_ZERO = 0;
    private SeatFactory seatFactory;

    @BeforeEach
    public void setup() {
        seatFactory = new SeatFactory();
    }

    @Test
    void whenCreatingSeatFactory_thenPreviousSeatNumberIsZero() {
        assertEquals(EXPECTED_ZERO, seatFactory.getPreviousSeatNumber());
    }

    @Test
    void whenCreatingSeat_thenNewSeatIsNotNull() {
        assertNotNull(seatFactory.getSeat());
    }

    @Test
    void whenCreatingSeat_thenNewSeatIsAvailable() {

        Assertions.assertEquals(SeatState.Available, seatFactory.getSeat().getState());
    }

    @Test
    void whenCreatingSeat_thenPreviousSeatNumberIncrements() {
        int previousSeatNumber = seatFactory.getPreviousSeatNumber();
        seatFactory.getSeat();

        assertEquals(previousSeatNumber + 1, seatFactory.getPreviousSeatNumber());
    }

    @Test
    void whenCreatingSeat_thenNewlyCreatedSeatNumberIsPreviousSeatNumber() {
        Seat seat = seatFactory.getSeat();

        assertEquals(seat.getNumber(), seatFactory.getPreviousSeatNumber());
    }

    @Test
    void givenPreviousSeatNumberIsNotZero_whenReset_thenPreviousSeatNumberIsSetToZero() {
        seatFactory.getSeat();

        seatFactory.reset();
        assertEquals(EXPECTED_ZERO, seatFactory.getPreviousSeatNumber());
    }

    @Test
    void givenASeatCount_whenGetSeats_thenReturnSeatCountSeats() {
        List<Seat> seats = seatFactory.getSeats(SEAT_COUNT);
        assertEquals(SEAT_COUNT, seats.size());
    }

}