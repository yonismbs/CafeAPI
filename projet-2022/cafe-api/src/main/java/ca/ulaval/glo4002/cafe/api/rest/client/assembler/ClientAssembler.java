package ca.ulaval.glo4002.cafe.api.rest.client.assembler;

import ca.ulaval.glo4002.cafe.api.rest.client.dto.ClientDTO;
import ca.ulaval.glo4002.cafe.domain.client.Client;

public class ClientAssembler {
    public ClientDTO toClientDTO(Client client) {
        return new ClientDTO(client.getName(), client.getSeatNumber(), client.getGroupName().stringRepresentation());
    }
}
