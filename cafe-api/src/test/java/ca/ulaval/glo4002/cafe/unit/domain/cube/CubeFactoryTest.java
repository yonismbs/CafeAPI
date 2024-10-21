package ca.ulaval.glo4002.cafe.unit.domain.cube;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.CubeFactory;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatFactory;

class CubeFactoryTest {

    private static final int CUBE_SIZE = 1;
    private static final String DEFAULT_CUBE_NAME = "Tinker Bell";
    private SeatFactory seatFactory;
    private CubeFactory cubeFactory;

    @BeforeEach
    public void setup() {
        seatFactory = new SeatFactory();
        cubeFactory = new CubeFactory(seatFactory);
    }

    @Test
    void whenCreatingCube_thenNewCubeIsNotNull() {
        List<Seat> seats = Collections.emptyList();

        assertNotNull(cubeFactory.getCube(DEFAULT_CUBE_NAME, seats));
    }

    @Test
    void whenReset_thenSeatFactoryResetIsCalled() {
        SeatFactory seatFactory = mock(SeatFactory.class);
        CubeFactory cubeFactory = new CubeFactory(seatFactory);

        cubeFactory.reset();

        verify(seatFactory).reset();
    }

    @Test
    void givenCubeSize_whenGetCubes_thenAllCubesHaveCubeSize() {
        List<Cube> cubes = cubeFactory.getCubes(CUBE_SIZE);

        assertTrue(cubes.stream().allMatch(cube -> cube.getCubeSize() == CUBE_SIZE));
    }
}