package ca.ulaval.glo4002.cafe.domain.cafe.layout.cube;

import java.util.List;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.exception.NoAssignableSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;

public class Cube {

    private List<Seat> seats;
    private final String name;

    public Cube(String name, List<Seat> seats) {
        this.name = name;
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return this.seats;
    }

    public int getCubeSize() {
        return this.seats.size();
    }

    public String getName() {
        return this.name;
    }

    public int assignClientIdToSeatByGroupName(ClientId id, GroupName groupName) throws NoAssignableSeatsException {
        return findFirstAvailableSeat(groupName).assignClientId(id);
    }

    private Seat findFirstAvailableSeat(GroupName groupName) throws NoAssignableSeatsException {
        return seats.stream().filter(seat -> seat.isReservedForGroup(groupName))
            .findFirst().orElseThrow(NoAssignableSeatsException::new);
    }

    public List<Seat> getAvailableSeats(int remainingSeatCount, ReservationStrategy reservationStrategy) {
        return reservationStrategy.findAvailableCubeSeats(remainingSeatCount, seats);
    }

    public int assignClientIdToSeat(ClientId clientId) throws NoAssignableSeatsException {
        for (Seat seat: seats) {
            if (seat.isAvailable()) {
                return seat.assignClientId(clientId);
            }
        }
        throw new NoAssignableSeatsException();
    }

    public void resetAllSeats() {
        for (Seat seat: seats) {
            seat.reset();
        }
    }
}
