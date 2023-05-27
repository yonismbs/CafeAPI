package ca.ulaval.glo4002.cafe.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.application.service.CheckInService;
import ca.ulaval.glo4002.cafe.application.service.ClientService;
import ca.ulaval.glo4002.cafe.application.service.GroupService;
import ca.ulaval.glo4002.cafe.application.service.order.billing.BillService;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.DuplicateClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;

public class CheckInServiceTest {

    private static final GroupName GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private static final String CLIENT_NAME = "Mario Bross";
    private CheckInService checkInService;
    private GroupService groupService;
    private ClientService clientService;
    private CafeService cafeService;
    private BillService billService;
    private Client client;
    private Client clientWithNoGroup;
    private ClientId clientId;

    @BeforeEach
    void setup() throws DuplicateClientIdException {
        cafeService = mock(CafeService.class);
        clientService = mock(ClientService.class);
        groupService = mock(GroupService.class);
        billService = mock(BillService.class);
        clientId = mock(ClientId.class);

        client = mock(Client.class);
        when(client.getId()).thenReturn(clientId);
        when(client.getName()).thenReturn(CLIENT_NAME);
        when(client.getGroupName()).thenReturn(GROUP_NAME);

        clientWithNoGroup = mock(Client.class);
        when(clientWithNoGroup.getId()).thenReturn(clientId);
        when(clientWithNoGroup.getName()).thenReturn(CLIENT_NAME);
        when(clientWithNoGroup.getGroupName()).thenReturn(GroupName.VOID);

        when(clientService.createClient(clientId, CLIENT_NAME, GROUP_NAME)).thenReturn(client);
        when(clientService.createClient(clientId, CLIENT_NAME, GroupName.VOID)).thenReturn(clientWithNoGroup);

        checkInService = new CheckInService(cafeService, clientService, groupService, billService);
    }


    @Test
    void givenValidClientInformation_whenCheckingInClient_thenReturnsClient() throws InsufficientSeatsException, NoGroupSeatsException, NoGroupFoundException, DuplicateClientIdException {
        assertThat(checkInService.checkInClient(clientId, CLIENT_NAME, GroupName.VOID), instanceOf(Client.class));
    }


    @Test
    void givenFaultyGroupName_whenCheckInClient_thenNoClientIsSaved() throws NoGroupSeatsException, NoGroupFoundException {
        doThrow(NoGroupSeatsException.class).when(groupService).addClientToGroup(GROUP_NAME, client);
        assertThrows(NoGroupSeatsException.class, () -> checkInService.checkInClient(clientId, CLIENT_NAME, GROUP_NAME));
        verify(clientService, never()).saveClient(any(Client.class));
    }

    @Test
    void givenFaultyGroupName_whenCheckInClient_thenLayoutServiceIsNeverCalled() throws NoGroupSeatsException, InsufficientSeatsException, NoGroupFoundException {
        doThrow(NoGroupSeatsException.class).when(groupService).addClientToGroup(GROUP_NAME, client);
        assertThrows(NoGroupSeatsException.class, () -> checkInService.checkInClient(clientId, CLIENT_NAME, GROUP_NAME));

        verify(cafeService, never()).assignClientIdToSeat(any(), any());
    }

    @Test
    void givenGroupName_whenCheckInClient_thenClientIsAddedToGroup() throws NoGroupSeatsException, NoGroupFoundException {
        assertDoesNotThrow(() -> checkInService.checkInClient(clientId, CLIENT_NAME, GROUP_NAME));
        verify(groupService).addClientToGroup(eq(GROUP_NAME), any(Client.class));
    }

    @Test
    void givenNoGroupName_whenCreatingClient_thenGroupServiceIsNeverCalled() throws NoGroupSeatsException, NoGroupFoundException {
        assertDoesNotThrow(() -> checkInService.checkInClient(clientId, CLIENT_NAME, GroupName.VOID));
        verify(groupService, never()).addClientToGroup(any(), any(Client.class));
    }

    @Test
    void whenCheckOutAll_thenLayoutIsReset() {
        checkInService.close();

        verify(cafeService).close();
    }

    @Test
    void whenCheckOutAll_thenGroupsAreReset() {
        checkInService.close();

        verify(groupService).resetGroups();
    }

    @Test
    void whenCheckOutAll_thenClientsAreReset() {
        checkInService.close();

        verify(clientService).resetCustomers();
    }
}
