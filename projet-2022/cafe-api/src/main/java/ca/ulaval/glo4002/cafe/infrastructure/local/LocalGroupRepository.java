package ca.ulaval.glo4002.cafe.infrastructure.local;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.Group;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.GroupRepository;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupWithSuchClientException;

public class LocalGroupRepository implements GroupRepository {

    private final HashMap<GroupName, Group> groupRepo = new HashMap<>();

    @Override
    public GroupName saveByGroupName(GroupName groupName, Group group) {
        groupRepo.put(groupName, group);
        return groupName;
    }

    @Override
    public Group find(GroupName groupName) throws NoGroupFoundException {
        if (!groupRepo.containsKey(groupName)) throw new NoGroupFoundException();
        return groupRepo.get(groupName);
    }

    @Override
    public void clear() {
        groupRepo.clear();
    }

    @Override
    public List<Group> findAll() {
        return new ArrayList<>(groupRepo.values());
    }

    @Override
    public GroupName findGroupNameByClientId(ClientId clientId) throws NoGroupWithSuchClientException {
        return groupRepo.values().stream()
            .filter(group -> group.getVisitedClients().stream()
                .anyMatch(client -> client.getId().equals(clientId)))
            .findFirst().orElseThrow(NoGroupWithSuchClientException::new).getName();
    }
}
