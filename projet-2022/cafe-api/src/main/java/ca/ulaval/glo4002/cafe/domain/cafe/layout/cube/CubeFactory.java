package ca.ulaval.glo4002.cafe.domain.cafe.layout.cube;

import java.util.LinkedList;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatFactory;

public class CubeFactory {

    public static final String[] DEFAULT_CUBE_NAMES = {"Bloom", "Merryweather", "Tinker Bell", "Wanda"};

    public SeatFactory seatFactory;

    public CubeFactory() {
        this.seatFactory = new SeatFactory();
    }

    public CubeFactory(SeatFactory seatFactory) {
        this();
        this.seatFactory = seatFactory;
    }

    public Cube getCube(String name, List<Seat> seats) {
        return new Cube(name, seats);
    }

    public List<Cube> getCubes(int cubeSize) {
        LinkedList<Cube> generatedCubes = new LinkedList<>();
        for (String cubeName : DEFAULT_CUBE_NAMES) {
            Cube cube = getCube(cubeName, seatFactory.getSeats(cubeSize));
            generatedCubes.add(cube);
        }
        return generatedCubes;
    }

    public void reset() {
        seatFactory.reset();
    }

}
