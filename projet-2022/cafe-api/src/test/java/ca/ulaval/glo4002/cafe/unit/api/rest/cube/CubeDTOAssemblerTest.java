package ca.ulaval.glo4002.cafe.unit.api.rest.cube;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.api.rest.cube.CubeDTOAssembler;
import ca.ulaval.glo4002.cafe.api.rest.cube.dto.CubeDTO;
import ca.ulaval.glo4002.cafe.api.rest.seat.SeatDTOAssembler;
import ca.ulaval.glo4002.cafe.api.rest.seat.dto.SeatDTO;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.Cube;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;

class CubeDTOAssemblerTest {

    private static final String STATUS = "Available";
    private static final int SEAT_NUMBER = 1;
    private static final String MISSING_GROUP_NAME = null;
    private SeatDTOAssembler seatDtoAssembler;

    @BeforeEach
    void setup() {
        seatDtoAssembler = mock(SeatDTOAssembler.class);
    }

    @Test
    void givenNCubes_thenShouldAssembleNCubes() {
        CubeDTOAssembler cubeDtoAssembler = new CubeDTOAssembler(seatDtoAssembler);
        SeatDTO seat = new SeatDTO(SEAT_NUMBER, STATUS, UUID.randomUUID().toString(), MISSING_GROUP_NAME);
        Cube cubeA = mock(Cube.class);
        Cube cubeB = mock(Cube.class);
        List<Cube> cubes = Arrays.asList(cubeA, cubeB);
        when(seatDtoAssembler.toSeatDTO(any(Seat.class))).thenReturn(seat);

        List<CubeDTO> cubeDTOS = cubeDtoAssembler.toCubeDTO(cubes);

        assertEquals(cubes.size(), cubeDTOS.size());
    }
}