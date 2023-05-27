package ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat;

import java.util.LinkedList;
import java.util.List;

public class SeatFactory {

    private int previousSeatNumber;

    public SeatFactory() {
        previousSeatNumber = 0;
    }

    public int getPreviousSeatNumber() {

        return previousSeatNumber;
    }

    public Seat getSeat() {
        return new Seat(++previousSeatNumber);
    }

    public List<Seat> getSeats(int seatCount) {
        List<Seat> generatedSeats = new LinkedList<>();
        for (int seatNumber = 1; seatNumber <= seatCount; seatNumber++) {
            generatedSeats.add(getSeat());
        }
        return generatedSeats;
    }

    public void reset() {
        previousSeatNumber = 0;
    }
}
