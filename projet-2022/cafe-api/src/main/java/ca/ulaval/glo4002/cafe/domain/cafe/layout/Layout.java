package ca.ulaval.glo4002.cafe.domain.cafe.layout;

import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.exception.NoAssignableSeatsException;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatState;
import ca.ulaval.glo4002.cafe.domain.cafe.reservation.strategy.ReservationStrategy;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;

public class Layout {

    private final List<Cube> cubes;

    public Layout(List<Cube> cubes) {
        this.cubes = cubes;
    }

    public int getCubeCount() {
        return cubes.size();
    }

    public List<Cube> getCubes() {
        return this.cubes;
    }

    public int assignClientIdToSeatFromGroup(ClientId clientId, GroupName groupName) throws NoGroupSeatsException {
        for (Cube cube : cubes) {
            try {
                return cube.assignClientIdToSeatByGroupName(clientId, groupName);
            } catch (NoAssignableSeatsException ignore) {
            }
        }
        throw new NoGroupSeatsException();
    }

    public Integer assignClientIdToSeat(ClientId clientId) throws InsufficientSeatsException {
        for (Cube cube : cubes) {
            try {
                return cube.assignClientIdToSeat(clientId);
            } catch (NoAssignableSeatsException ignore) {
            }
        }
        throw new InsufficientSeatsException();
    }

    public void reserveSeats(int seatCount, GroupName groupName, ReservationStrategy reservationStrategy) throws InsufficientSeatsException {
        List<Seat> foundSeats = getNAvailableSeats(seatCount, reservationStrategy);
        for (Seat seat : foundSeats) {
            seat.setState(SeatState.Reserved);
            seat.setGroupName(groupName);
        }
    }

    private List<Seat> getNAvailableSeats(int seatCount, ReservationStrategy reservationStrategy) throws InsufficientSeatsException {
        ArrayList<Seat> foundSeats = new ArrayList<>();
        for (Cube cube : cubes) {
            foundSeats.addAll(cube.getAvailableSeats(seatCount - foundSeats.size(), reservationStrategy));
            if (foundSeats.size() >= seatCount) return foundSeats;
        }
        throw new InsufficientSeatsException();
    }

    public int getMaxCapacity() {
        int totalSeatCount = 0;
        for (Cube cube : cubes) {
            totalSeatCount += cube.getCubeSize();
        }
        return totalSeatCount;
    }

    public void resetAllSeats() {
        for (Cube cube : cubes) {
            cube.resetAllSeats();
        }
    }

    public void disbandGroupIfEmpty(GroupName groupName) {
        List<Seat> groupSeats = cubes.stream().flatMap(cube -> cube.getSeats().stream())
                .filter(seat -> seat.getGroupName().equals(groupName)).toList();

        if (groupSeats.stream().allMatch(groupSeat -> groupSeat.getState() == SeatState.Reserved)) {
            groupSeats.forEach(Seat::reset);
        }
    }

    public void resetSeatByClientId(ClientId clientId) throws InvalidClientIdException {
        Seat clientSeat = cubes.stream().flatMap(cube -> cube.getSeats().stream())
                .filter(seat -> seat.getClientId().equals(clientId))
                .findFirst().orElseThrow(InvalidClientIdException::new);

        clientSeat.reset();
    }
}
