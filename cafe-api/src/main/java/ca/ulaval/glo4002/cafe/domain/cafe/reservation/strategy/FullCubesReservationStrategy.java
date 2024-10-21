package ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy;

import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;

public class FullCubesReservationStrategy implements ReservationStrategy {
    @Override
    public List<Seat> findAvailableCubeSeats(int remainingSeatCount, List<Seat> cubeSeats) {
        for (Seat seat : cubeSeats) {
            if (!seat.isAvailable()) {
                return new ArrayList<>();
            }
        }
        return cubeSeats;
    }
}
