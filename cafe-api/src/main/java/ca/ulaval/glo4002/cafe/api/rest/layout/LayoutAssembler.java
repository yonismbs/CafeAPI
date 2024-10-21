package ca.ulaval.glo4002.cafe.api.rest.layout;

import ca.ulaval.glo4002.cafe.api.rest.cube.CubeDTOAssembler;
import ca.ulaval.glo4002.cafe.api.rest.layout.dto.LayoutDTO;
import ca.ulaval.glo4002.cafe.domain.cafe.Cafe;

public class LayoutAssembler {

    private final CubeDTOAssembler cubeAssembler;

    public LayoutAssembler(CubeDTOAssembler cubeAssembler) {
        this.cubeAssembler = cubeAssembler;
    }

    public LayoutDTO toLayoutDTO(Cafe cafe) {
        return new LayoutDTO(cafe.getName(), cubeAssembler.toCubeDTO(cafe.getLayout().getCubes()));
    }

}
