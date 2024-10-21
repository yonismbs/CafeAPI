package ca.ulaval.glo4002.cafe.unit.infrastructure.local;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalClientRepository;

class LocalClientRepositoryTest {

    private static final int EXPECTED_ZERO = 0;
    private static final String CLIENT_NAME = "Mike Wazowski";
    private static final ClientId CLIENT_ID = new ClientId("b954c8a7-6d94-4d3d-8b39-250766764710");
    private Client client;
    private LocalClientRepository localClientRepository;

    @BeforeEach
    void setUp() {
        client = new Client(CLIENT_ID, CLIENT_NAME);
        localClientRepository = new LocalClientRepository();
    }

    @Test
    void givenAClient_whenAddingToRepository_thenShouldAddClientToMemory() {
        int clientListBeforeAddingClient = localClientRepository.getClients().size();

        localClientRepository.addClient(client);

        assertEquals(clientListBeforeAddingClient + 1, localClientRepository.getClients().size());
    }

    @Test
    void whenClearingListOfCustomer_thenShouldClearTodayVisitorList() {
        localClientRepository.addClient(client);

        localClientRepository.clearClientList();

        assertEquals(EXPECTED_ZERO, localClientRepository.getClients().size());
    }

    @Test
    void givenAClientId_whenRetrievingClient_thenShouldReturnClientIfExist() throws InvalidClientIdException {
        localClientRepository.addClient(client);

        assertNotNull(localClientRepository.findClient(client.getId()));
    }

    @Test
    void givenAClientID_whenTryingToRetrieveClient_thenShouldThrowExceptionIfClientDoesntExist() {
        assertThrows(InvalidClientIdException.class, () ->
                localClientRepository.findClient(client.getId()));
    }

    @Test
    void givenNoDuplicatedClient_whenIsClientDuplicate_thenShouldReturnFalse() {
        assertFalse(localClientRepository.isClientDuplicate(CLIENT_ID));
    }

    @Test
    void givenDuplicatedClient_whenIsClientDuplicate_thenShouldReturnTrue() {
        localClientRepository.addClient(client);

        assertTrue(localClientRepository.isClientDuplicate(client.getId()));
    }

}
