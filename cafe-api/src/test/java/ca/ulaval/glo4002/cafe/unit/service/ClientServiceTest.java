package ca.ulaval.glo4002.cafe.unit.service;

import ca.ulaval.glo4002.cafe.domain.billing.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.application.service.ClientService;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientFactory;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.DuplicateClientIdException;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalClientRepository;

class ClientServiceTest {

    private static final GroupName GROUP_NAME = null;
    private static final String CLIENT_NAME = "Mario Bross";
    private static final GroupName GROUP_NANE = new GroupName("groupe");
    private LocalClientRepository clientRepositoryImpl;
    private BillRepository billRepository;
    private ClientFactory clientFactory;
    private ClientId clientId;
    private Client client;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientId = mock(ClientId.class);
        clientFactory = mock(ClientFactory.class);
        clientRepositoryImpl = mock(LocalClientRepository.class);
        billRepository = mock(BillRepository.class);
        clientService = new ClientService(clientRepositoryImpl, billRepository);
        client = new Client(clientId, CLIENT_NAME);
    }

    @Test
    void whenCreatingNewClient_thenShouldReturnNewClient() throws DuplicateClientIdException {
        when(clientFactory.create(clientId, CLIENT_NAME, GROUP_NANE)).thenReturn(client);
        when(clientRepositoryImpl.isClientDuplicate(clientId)).thenReturn(false);
        when(clientFactory.create(clientId, CLIENT_NAME, GROUP_NAME)).thenReturn(client);

        assertThat(clientService.createClient(clientId, CLIENT_NAME, GROUP_NANE), instanceOf(Client.class));
    }

    @Test
    void givenExistingClient_whenCreatingClientWithSameID_thenShouldThrowException() {
        when(clientRepositoryImpl.isClientDuplicate(clientId)).thenReturn(true);

        assertThrows(DuplicateClientIdException.class, () ->
                clientService.createClient(clientId, CLIENT_NAME, GROUP_NANE));
    }

    @Test
    void whenFindingClient_thenClientRepositoryIsCalledToFindClient() throws InvalidClientIdException {
        when(clientRepositoryImpl.findClient(clientId)).thenReturn(client);
        clientService.findClient(clientId);

        verify(clientRepositoryImpl).findClient(clientId);
    }

    @Test
    void whenClosingCafe_thenClientRepositoryIsCalledToResetCustomers() {
        clientService.resetCustomers();

        verify(clientRepositoryImpl).clearClientList();
    }

    @Test
    void whenAddingClient_thenClientRepositoryIsCalledToSaveClient() {
        clientService.saveClient(client);

        verify(clientRepositoryImpl).addClient(client);
    }

}
