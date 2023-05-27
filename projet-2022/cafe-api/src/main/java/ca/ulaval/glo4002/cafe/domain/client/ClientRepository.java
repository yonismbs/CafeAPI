package ca.ulaval.glo4002.cafe.domain.client;

import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;

public interface ClientRepository {

    void addClient(Client client);

    void clearClientList();

    Client findClient(ClientId clientId) throws InvalidClientIdException;

    boolean isClientDuplicate(ClientId clientId);
}
