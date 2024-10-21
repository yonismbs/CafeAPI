package ca.ulaval.glo4002.cafe.application.service;

import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.Group;
import ca.ulaval.glo4002.cafe.domain.group.GroupFactory;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.GroupRepository;
import ca.ulaval.glo4002.cafe.domain.group.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.group.exception.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupWithSuchClientException;

public class GroupService {

    private final CafeService cafeService;
    private final GroupRepository groupRepository;
    private final GroupFactory groupFactory;

    public GroupService(CafeService cafeService, GroupRepository groupRepository,
                        GroupFactory groupFactory) {
        this.cafeService = cafeService;
        this.groupRepository = groupRepository;
        this.groupFactory = groupFactory;
    }

    public GroupService(CafeService cafeService, GroupRepository groupRepository) {
        this(cafeService, groupRepository, new GroupFactory(new ArrayList<>()));
    }

    public void createGroup(GroupName groupName, int size) throws InsufficientSeatsException, InvalidGroupSizeException, DuplicateGroupNameException {
        Group group = groupFactory.getGroup(groupName, size);
        cafeService.reserveSeats(size, groupName);
        groupFactory.confirmGroupName(groupName);
        this.groupRepository.saveByGroupName(groupName, group);
    }

    public void addClientToGroup(GroupName groupName, Client client) throws NoGroupSeatsException, NoGroupFoundException {
        groupRepository.find(groupName).add(client);
    }

    public void resetGroups() {
        groupRepository.clear();
        groupFactory.clearGroupNames();
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public GroupName findGroupIdFromClientId(ClientId clientId) throws NoGroupWithSuchClientException {
        return groupRepository.findGroupNameByClientId(clientId);
    }
}
