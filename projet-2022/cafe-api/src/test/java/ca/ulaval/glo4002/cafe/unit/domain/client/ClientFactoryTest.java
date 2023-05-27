package ca.ulaval.glo4002.cafe.unit.domain.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.ulaval.glo4002.cafe.domain.client.ClientFactory;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;

class ClientFactoryTest {

    private static final String CLIENT_NAME = "Harry Potter";
    private static final ClientId CLIENT_ID = new ClientId("b954c8a7-6d94-4d3d-8b39-250766764710");
    private static final GroupName GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private ClientFactory clientFactory;

    @BeforeEach
    public void setUp() {
        clientFactory = new ClientFactory();
    }


    @Test
    void whenCreatingClient_thenClientIsNotNull() {
        assertNotNull(clientFactory.create(CLIENT_ID, CLIENT_NAME, GROUP_NAME));
    }

    @Test
    void whenCreatingClientWithGroup_thenClientGroupIsNotNull() {
        assertNotNull(clientFactory.create(CLIENT_ID, CLIENT_NAME, GROUP_NAME).getGroupName());
    }
}
