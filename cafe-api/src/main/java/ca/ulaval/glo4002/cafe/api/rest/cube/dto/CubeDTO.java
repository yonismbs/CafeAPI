package ca.ulaval.glo4002.cafe.api.rest.cube.dto;

import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.seat.dto.SeatDTO;

public record CubeDTO(String name, List<SeatDTO> seats) {
}
