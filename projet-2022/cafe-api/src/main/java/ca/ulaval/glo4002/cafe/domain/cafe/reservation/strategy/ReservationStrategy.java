package ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy;

import java.util.List;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;

public interface ReservationStrategy {

    List<Seat> findAvailableCubeSeats(int remainingSeatCount, List<Seat> cubeSeats);

}
