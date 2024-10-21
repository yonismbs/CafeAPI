package ca.ulaval.glo4002.cafe.domain.client;

import ca.ulaval.glo4002.cafe.domain.group.GroupName;

public class ClientFactory {
    public Client create(ClientId clientID, String clientName, GroupName groupName) {
        return new Client(clientID, clientName, groupName);
    }
}
