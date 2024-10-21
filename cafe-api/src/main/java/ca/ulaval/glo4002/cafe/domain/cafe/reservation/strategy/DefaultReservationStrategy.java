package ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy;

import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;

public class DefaultReservationStrategy implements ReservationStrategy {
    @Override
    public List<Seat> findAvailableCubeSeats(int remainingSeatCount, List<Seat> cubeSeats) {
        List<Seat> foundSeats = new ArrayList<>();
        for (Seat seat : cubeSeats) {
            if (seat.isAvailable()) {
                foundSeats.add(seat);
            }
            if (foundSeats.size() >= remainingSeatCount) break;
        }
        return foundSeats;
    }
}
