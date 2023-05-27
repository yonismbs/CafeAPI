package ca.ulaval.glo4002.cafe.infrastructure.local;

import java.util.HashMap;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.ClientRepository;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;

public class LocalClientRepository implements ClientRepository {

    private final HashMap<ClientId, Client> listOfClients = new HashMap<>();

    @Override
    public void addClient(Client client) {
        listOfClients.put(client.getId(), client);
    }

    @Override
    public void clearClientList() {
        listOfClients.clear();
    }

    public HashMap<ClientId, Client> getClients() {
        return listOfClients;
    }

    @Override
    public boolean isClientDuplicate(ClientId clientId) {
        return listOfClients.containsKey(clientId);
    }

    @Override
    public Client findClient(ClientId ID) throws InvalidClientIdException {
        if(!listOfClients.containsKey(ID)) throw new InvalidClientIdException();
        return listOfClients.get(ID);
    }

}
