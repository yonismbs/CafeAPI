package ca.ulaval.glo4002.cafe.unit.api.rest.client.group;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.api.rest.group.GroupResource;
import ca.ulaval.glo4002.cafe.api.rest.group.dto.GroupDTO;
import ca.ulaval.glo4002.cafe.application.service.GroupService;
import ca.ulaval.glo4002.cafe.domain.group.Group;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;

class GroupResourceTest {

    private static final String GROUP_NAME_A_STRING = "Bulletproof Boy Scouts";
    private static final GroupName GROUP_NAME_A = new GroupName("Bulletproof Boy Scouts");
    private static final int GROUP_MAX_SIZE_A = 2;
    private static final GroupName GROUP_NAME_B = new GroupName("b");
    private static final int GROUP_MAX_SIZE_B = 3;
    private GroupService groupService;
    private GroupResource groupResource;

    @BeforeEach
    public void setup() {
        groupService = mock(GroupService.class);

        groupResource = new GroupResource(groupService);
    }

    List<Group> getMockedGroupsFromService() {
        ArrayList<Group> groups = new ArrayList<>();
        when(groupService.getGroups()).thenReturn(groups);

        return groups;
    }

    @Test
    void givenMultipleGroupsAreReserved_whenGettingAllReservations_thenAllGroupsAreReturned() {
        List<Group> groups = getMockedGroupsFromService();
        groups.add(new Group(GROUP_MAX_SIZE_A, GROUP_NAME_A));
        groups.add(new Group(GROUP_MAX_SIZE_B, GROUP_NAME_B));

        List<GroupDTO> groupsDTO = groupResource.getAllGroups();

        assertThat(groupsDTO, hasSize(2));
    }

    @Test
    void givenAGroupIsReserved_whenGettingAllReservations_thenReturnedGroupMatchesByValue() {
        List<Group> groups = getMockedGroupsFromService();
        groups.add(new Group(GROUP_MAX_SIZE_A, GROUP_NAME_A));

        GroupDTO group0 = groupResource.getAllGroups().get(0);

        assertEquals(GROUP_NAME_A_STRING, group0.group_name());
        assertEquals(GROUP_MAX_SIZE_A, group0.group_size());
    }

}