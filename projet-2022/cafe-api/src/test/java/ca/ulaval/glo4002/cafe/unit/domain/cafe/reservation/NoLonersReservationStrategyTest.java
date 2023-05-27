package ca.ulaval.glo4002.cafe.unit.domain.cafe.reservation;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatState;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.NoLonersReservationStrategy;

class NoLonersReservationStrategyTest {

    NoLonersReservationStrategy noLonersReservationStrategy;

    @BeforeEach
    void setup() {
        noLonersReservationStrategy = new NoLonersReservationStrategy();
    }

    @Test
    void givenTwoAvailableSeats_whenFindTwoAvailableSeats_thenTwoSeatsAreReturned() {
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Available),
                getSeatWithState(new Seat(2), SeatState.Available));

        assertEquals(2, noLonersReservationStrategy.findAvailableCubeSeats(2, seats).size());
    }

    @Test
    void givenOneAvailable_whenFindTwoAvailableSeats_thenNoSeatsAreReturned() {
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Occupied),
                getSeatWithState(new Seat(2), SeatState.Available));

        assertEquals(0, noLonersReservationStrategy.findAvailableCubeSeats(2, seats).size());
    }

    @Test
    void givenTwoAvailableSeats_whenFindThreeAvailableSeats_thenNoSeatsAreReturned() {
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Available),
                getSeatWithState(new Seat(2), SeatState.Available));

        assertEquals(0, noLonersReservationStrategy.findAvailableCubeSeats(3, seats).size());
    }

    @Test
    void givenAPotentialLoner_whenFindingMoreThanThreeAvailableSeats_thenTheReturnedSeatsIsAvailableSeatsMinusOne() {
        List<Seat> seats = Arrays.asList(
                getSeatWithState(new Seat(1), SeatState.Available),
                getSeatWithState(new Seat(2), SeatState.Available),
                getSeatWithState(new Seat(3), SeatState.Available));

        assertEquals(2, noLonersReservationStrategy.findAvailableCubeSeats(4, seats).size());
    }

    @Test
    void givenAnOccupiedSeat_whenFindingSeatsWithNoLoners_thenFoundSeatsSkipOccupied() {
        Seat seat1 = new Seat(1);
        Seat seat3 = new Seat(3);
        List<Seat> seats = Arrays.asList(
                getSeatWithState(seat1, SeatState.Available),
                getSeatWithState(new Seat(2), SeatState.Occupied),
                getSeatWithState(seat3, SeatState.Available));

        assertEquals(Arrays.asList(seat1, seat3), noLonersReservationStrategy.findAvailableCubeSeats(2, seats));
    }

    @Test
    void givenAnOccupiedSeatAndExtraSeats_whenFindingSeatsWithNoLoners_thenReturnFirstAvailableSeats() {
        Seat seat1 = new Seat(1);
        Seat seat3 = new Seat(3);
        List<Seat> seats = Arrays.asList(
                getSeatWithState(seat1, SeatState.Available),
                getSeatWithState(new Seat(2), SeatState.Occupied),
                getSeatWithState(seat3, SeatState.Available),
                getSeatWithState(new Seat(4), SeatState.Available));

        assertEquals(Arrays.asList(seat1, seat3), noLonersReservationStrategy.findAvailableCubeSeats(2, seats));
    }



    private Seat getSeatWithState(Seat seat, SeatState seatState) {
        seat.setState(seatState);
        return seat;
    }

}