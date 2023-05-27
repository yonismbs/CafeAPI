package ca.ulaval.glo4002.cafe.domain.group;

import java.util.List;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupWithSuchClientException;

public interface GroupRepository {
    GroupName saveByGroupName(GroupName groupName, Group group);
    Group find(GroupName groupName) throws NoGroupFoundException;
    void clear();
    List<Group> findAll();

    GroupName findGroupNameByClientId(ClientId clientId) throws NoGroupWithSuchClientException;
}
