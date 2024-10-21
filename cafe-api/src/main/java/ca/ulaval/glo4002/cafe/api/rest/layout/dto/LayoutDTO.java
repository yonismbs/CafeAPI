package ca.ulaval.glo4002.cafe.api.rest.layout.dto;

import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.cube.dto.CubeDTO;

public record LayoutDTO(String name, List<CubeDTO> cubes) {
}
