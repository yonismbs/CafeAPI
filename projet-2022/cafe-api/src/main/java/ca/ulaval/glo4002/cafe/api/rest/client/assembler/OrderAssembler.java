package ca.ulaval.glo4002.cafe.api.rest.client.assembler;

import java.util.List;
import ca.ulaval.glo4002.cafe.api.rest.client.dto.OrderRequestDTO;

public class OrderAssembler {
    public OrderRequestDTO toOrderListDto(List<String> allOrders) {
        return new OrderRequestDTO(allOrders);
    }
}
