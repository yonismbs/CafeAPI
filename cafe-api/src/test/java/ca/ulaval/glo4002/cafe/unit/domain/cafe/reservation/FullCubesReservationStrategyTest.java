package ca.ulaval.glo4002.cafe.unit.domain.cafe.reservation;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatState;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.FullCubesReservationStrategy;

class FullCubesReservationStrategyTest {

    FullCubesReservationStrategy fullCubesReservationStrategy;

    @BeforeEach
    void setup() {
        fullCubesReservationStrategy = new FullCubesReservationStrategy();
    }

    @Test
    void givenAllAvailableSeats_whenFindOneAvailableSeats_thenAllSeatsAreReturned() {
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Available),
                getSeatWithState(new Seat(2), SeatState.Available));

        assertEquals(2, fullCubesReservationStrategy.findAvailableCubeSeats(1, seats).size());
    }

    @Test
    void givenAllAvailableSeats_whenFindMoreThanSeatCapacity_thenAllSeatsAreReturned() {
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Available),
                getSeatWithState(new Seat(2), SeatState.Available));

        assertEquals(2, fullCubesReservationStrategy.findAvailableCubeSeats(3, seats).size());
    }

    @Test
    void givenOneOccupiedSeat_whenFindOneAvailableSeats_thenNoSeatsAreReturned() {
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Occupied),
                getSeatWithState(new Seat(2), SeatState.Available));

        assertEquals(0, fullCubesReservationStrategy.findAvailableCubeSeats(1, seats).size());
    }


    private Seat getSeatWithState(Seat seat, SeatState seatState) {
        seat.setState(seatState);
        return seat;
    }

}