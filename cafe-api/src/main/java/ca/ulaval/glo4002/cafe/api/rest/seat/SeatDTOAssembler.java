package ca.ulaval.glo4002.cafe.api.rest.seat;

import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.seat.dto.SeatDTO;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.Seat;

public class SeatDTOAssembler {

    public SeatDTO toSeatDTO(Seat seat) {
        return new SeatDTO(seat.getNumber(), seat.getState().name(),
                seat.getClientId().id(),
                seat.getGroupName().stringRepresentation());
    }

    public List<SeatDTO> toSeatDTO(List<Seat> seats) {
        return seats.stream().map(this::toSeatDTO).toList();
    }


}
