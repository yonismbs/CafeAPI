package ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy;

import java.util.List;
import java.util.Stack;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;

public class NoLonersReservationStrategy implements ReservationStrategy {
    @Override
    public List<Seat> findAvailableCubeSeats(int remainingSeatCount, List<Seat> cubeSeats) {
        Stack<Seat> foundSeats = new Stack<>();
        for (Seat seat : cubeSeats) {
            if (seat.isAvailable()) {
                foundSeats.add(seat);
            }
            if (foundSeats.size() >= remainingSeatCount) break;
        }
        if (foundSeats.size() + 1 == remainingSeatCount) {
            foundSeats.pop();
        }
        if (foundSeats.size() == 1) {
            foundSeats.pop();
        }
        return foundSeats;
    }
}
