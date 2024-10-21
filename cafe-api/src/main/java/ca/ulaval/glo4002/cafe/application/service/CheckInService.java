package ca.ulaval.glo4002.cafe.application.service;

import ca.ulaval.glo4002.cafe.application.service.order.billing.BillService;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.DuplicateClientIdException;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupWithSuchClientException;

public class CheckInService {

    private final GroupService groupService;
    private final ClientService clientService;
    private final CafeService cafeService;
    private final BillService billService;

    public CheckInService(CafeService cafeService, ClientService clientService, GroupService groupService,
                          BillService billService) {
        this.cafeService = cafeService;
        this.clientService = clientService;
        this.groupService = groupService;
        this.billService = billService;
    }

    public Client checkInClient(ClientId clientID, String clientName, GroupName groupName)
            throws InsufficientSeatsException, NoGroupSeatsException, NoGroupFoundException, DuplicateClientIdException {

        Client newClient = clientService.createClient(clientID, clientName, groupName);
        if (!groupName.isAbsent()) {
            groupService.addClientToGroup(groupName, newClient);
        }

        int seatNumber = cafeService.assignClientIdToSeat(newClient.getId(), groupName);
        newClient.setSeatNumber(seatNumber);
        clientService.saveClient(newClient);
        return newClient;
    }

    public void close() {
        cafeService.close();
        billService.archive();
        clientService.resetCustomers();
        groupService.resetGroups();
    }

    public void checkOutClient(ClientId clientId) throws InvalidClientIdException {
        billService.createReceiptForClient(clientId);
        cafeService.resetSeatByClientId(clientId);
        try {
            GroupName groupName = groupService.findGroupIdFromClientId(clientId);
            cafeService.disbandGroupIfEmpty(groupName);
        } catch (NoGroupWithSuchClientException ignored) { }

    }
}
