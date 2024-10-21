package ca.ulaval.glo4002.cafe.unit.service;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.ulaval.glo4002.cafe.application.service.CafeService;
import ca.ulaval.glo4002.cafe.application.service.GroupService;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.group.Group;
import ca.ulaval.glo4002.cafe.domain.group.GroupFactory;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.GroupRepository;
import ca.ulaval.glo4002.cafe.domain.group.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.group.exception.GroupException;
import ca.ulaval.glo4002.cafe.domain.group.exception.InvalidGroupSizeException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;

public class GroupServiceTest {

    public static final GroupName VALID_GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    public static final int VALID_GROUP_SIZE = 2;
    GroupService groupService;
    GroupRepository groupRepository;
    GroupFactory groupFactory;
    CafeService cafeService;

    @BeforeEach
    void setup() {
        groupRepository = mock(GroupRepository.class);
        groupFactory = mock(GroupFactory.class);
        cafeService = mock(CafeService.class);

        groupService = new GroupService(cafeService, groupRepository, groupFactory);
    }

    @Test
    void givenGroupsInRepo_whenGetGroups_thenRepoGroupsRepoAreReturned() {
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group(VALID_GROUP_SIZE, VALID_GROUP_NAME));

        when(groupRepository.findAll()).thenReturn(groups);

        assertEquals(groupService.getGroups(), groups);
    }

    @Test
    void givenNotEnoughSeats_whenCreateGroup_thenInsufficientSeatsExceptionIsThrown() throws InsufficientSeatsException {

        doThrow(InsufficientSeatsException.class).when(cafeService).reserveSeats(VALID_GROUP_SIZE, VALID_GROUP_NAME);
        assertThrows(InsufficientSeatsException.class,
                () -> groupService.createGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE));
        verify(groupRepository, never()).saveByGroupName(any(GroupName.class), any(Group.class));
        verify(groupFactory, never()).confirmGroupName(VALID_GROUP_NAME);
    }

    @Test
    void whenCreateGroup_thenSeatsAreReserved() throws InsufficientSeatsException, DuplicateGroupNameException, InvalidGroupSizeException {
        when(groupFactory.getGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE)).thenReturn(mock(Group.class));

        assertDoesNotThrow(() -> groupService.createGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE));

        verify(cafeService).reserveSeats(VALID_GROUP_SIZE, VALID_GROUP_NAME);
    }

    @Test
    void whenCreatingInvalidGroup_thenSeatsAreNotReserved()
            throws InsufficientSeatsException, DuplicateGroupNameException, InvalidGroupSizeException {

        when(groupFactory.getGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE)).thenThrow(DuplicateGroupNameException.class);

        assertThrows(GroupException.class, () -> groupService.createGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE));

        verify(cafeService, never()).reserveSeats(VALID_GROUP_SIZE, VALID_GROUP_NAME);
    }

    @Test
    void whenCreatingInvalidGroup_thenGroupNameIsNotConfirmed()
            throws DuplicateGroupNameException, InvalidGroupSizeException {

        when(groupFactory.getGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE)).thenThrow(DuplicateGroupNameException.class);

        assertThrows(GroupException.class, () -> groupService.createGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE));

        verify(groupFactory, never()).confirmGroupName(any());
    }

    @Test
    void givenValidGroup_whenCreatingGroup_thenGroupNameIsConfirmed()
            throws DuplicateGroupNameException, InvalidGroupSizeException, InsufficientSeatsException {
        Group group = mock(Group.class);
        when(group.getName()).thenReturn(VALID_GROUP_NAME);
        when(groupFactory.getGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE)).thenReturn(group);

        groupService.createGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE);

        verify(groupFactory).confirmGroupName(VALID_GROUP_NAME);
    }

    @Test
    void givenEnoughSeats_whenCreateGroup_thenGroupIsSavedToRepo() throws DuplicateGroupNameException, InvalidGroupSizeException {

        when(groupFactory.getGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE)).thenReturn(mock(Group.class));

        assertDoesNotThrow(() -> groupService.createGroup(VALID_GROUP_NAME, VALID_GROUP_SIZE));
        verify(groupRepository).saveByGroupName(any(GroupName.class), any(Group.class));
    }

    @Test
    void whenAddingClientToGroup_thenGroupAddIsCalled() throws NoGroupSeatsException, NoGroupFoundException {
        Group group = mock(Group.class);
        when(groupRepository.find(VALID_GROUP_NAME)).thenReturn(group);

        Client client = mock(Client.class);

        groupService.addClientToGroup(VALID_GROUP_NAME, client);

        verify(group).add(client);
    }

    @Test
    void givenNonExistingGroupName_whenAddingClientToGroup_thenNoGroupFoundExceptionIsThrown()
            throws NoGroupFoundException {
        when(groupRepository.find(VALID_GROUP_NAME)).thenThrow(new NoGroupFoundException());

        Client client = mock(Client.class);

        assertThrows(NoGroupFoundException.class, () -> groupService.addClientToGroup(VALID_GROUP_NAME, client));
    }

    @Test
    void whenClearingGroups_thenAllGroupsAreRemovedFromRepo() {
        groupService.resetGroups();

        verify(groupRepository).clear();
    }

    @Test
    void whenClearingGroups_thenAllGroupsAreRemovedFromFactory() {
        groupService.resetGroups();

        verify(groupFactory).clearGroupNames();
    }
}
