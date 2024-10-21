package ca.ulaval.glo4002.cafe.unit.domain.cafe.reservation;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatState;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.DefaultReservationStrategy;

class DefaultReservationStrategyTest {

    DefaultReservationStrategy defaultReservationStrategy;

    @BeforeEach
    void setup() {
        defaultReservationStrategy = new DefaultReservationStrategy();
    }

    @Test
    void givenTwoAvailableSeats_whenFindTwoAvailableSeats_thenTwoSeatsAreReturned() {
        List<Seat> seats = Arrays.asList(
            getSeatWithState(new Seat(1), SeatState.Available),
            getSeatWithState(new Seat(2), SeatState.Available));

        assertEquals(2, defaultReservationStrategy.findAvailableCubeSeats(2, seats).size());
    }

    @Test
    void givenOneAvailableSeats_whenFindTwoAvailableSeats_thenOneSeatIsReturned() {
        List<Seat> seats = Arrays.asList(
            getSeatWithState(new Seat(1), SeatState.Occupied),
            getSeatWithState(new Seat(2), SeatState.Available));

        assertEquals(1, defaultReservationStrategy.findAvailableCubeSeats(2, seats).size());
    }

    @Test
    void givenFirstSeatIsOccupied_whenFindOneAvailableSeat_thenSecondSeatIsReturned() {
        Seat seat2 = new Seat(2);
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Occupied),
                getSeatWithState(seat2, SeatState.Available));

        assertEquals(seat2, defaultReservationStrategy.findAvailableCubeSeats(2, seats).get(0));
    }

    @Test
    void givenAllSeatsAreOccupied_whenFindOneAvailableSeat_thenNoIsReturned() {
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Occupied),
                getSeatWithState(new Seat(2), SeatState.Occupied));

        assertEquals(0, defaultReservationStrategy.findAvailableCubeSeats(1, seats).size());
    }


    private Seat getSeatWithState(Seat seat, SeatState seatState) {
        seat.setState(seatState);
        return seat;
    }

}