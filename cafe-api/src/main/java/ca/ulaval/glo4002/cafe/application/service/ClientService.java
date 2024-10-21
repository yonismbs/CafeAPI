package ca.ulaval.glo4002.cafe.application.service;

import ca.ulaval.glo4002.cafe.domain.billing.Bill;
import ca.ulaval.glo4002.cafe.domain.billing.BillRepository;
import ca.ulaval.glo4002.cafe.domain.billing.exception.NoBillException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientFactory;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.ClientRepository;
import ca.ulaval.glo4002.cafe.domain.client.exception.DuplicateClientIdException;
import ca.ulaval.glo4002.cafe.domain.client.exception.InvalidClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;

public class ClientService {

    private final ClientFactory clientFactory = new ClientFactory();
    private final ClientRepository clientRepository;
    private final BillRepository billRepository;

    public ClientService(ClientRepository clientRepository, BillRepository billRepository) {
        this.clientRepository = clientRepository;
        this.billRepository = billRepository;
    }

    public Client createClient(ClientId clientId, String clientName, GroupName groupName)
            throws DuplicateClientIdException {
        if(clientRepository.isClientDuplicate(clientId)) throw new DuplicateClientIdException();
        return clientFactory.create(clientId, clientName, groupName);
    }

    public void saveClient(Client newClient) {
        clientRepository.addClient(newClient);
    }

    public Client findClient(ClientId clientId) throws InvalidClientIdException {
        return clientRepository.findClient(clientId);
    }

    public void resetCustomers() {
        clientRepository.clearClientList();
    }

    public Bill getClientBill(ClientId clientId) throws InvalidClientIdException, NoBillException {
        clientRepository.findClient(clientId);
        return billRepository.findBillByClientId(clientId);
    }

}
