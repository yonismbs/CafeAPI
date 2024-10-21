package ca.ulaval.glo4002.cafe.api.rest.layout;

import ca.ulaval.glo4002.cafe.api.rest.cube.CubeDTOAssembler;
import ca.ulaval.glo4002.cafe.api.rest.seat.SeatDTOAssembler;

public class LayoutAssemblerFactory {
    public static LayoutAssembler getFranchiseAssembler() {
        return new LayoutAssembler(new CubeDTOAssembler(new SeatDTOAssembler()));
    }
}
