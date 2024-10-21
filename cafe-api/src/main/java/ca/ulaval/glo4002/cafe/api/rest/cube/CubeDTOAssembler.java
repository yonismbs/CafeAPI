package ca.ulaval.glo4002.cafe.api.rest.cube;

import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.cube.dto.CubeDTO;
import ca.ulaval.glo4002.cafe.api.rest.seat.SeatDTOAssembler;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.Cube;

public class CubeDTOAssembler {

    private SeatDTOAssembler seatAssembler;

    public CubeDTOAssembler() {
        this.seatAssembler = new SeatDTOAssembler();
    }

    public CubeDTOAssembler(SeatDTOAssembler seatAssembler) {
        this();
        this.seatAssembler = seatAssembler;
    }

    public CubeDTO toCubeDTO(Cube cube) {
        return new CubeDTO(cube.getName(), seatAssembler.toSeatDTO(cube.getSeats()));
    }

    public List<CubeDTO> toCubeDTO(List<Cube> cubes) {
        return cubes.stream().map(this::toCubeDTO).toList();
    }
}
