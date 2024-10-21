package ca.ulaval.glo4002.cafe.unit.infrastructure.local;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.domain.group.Group;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.infrastructure.local.LocalGroupRepository;

public class LocalGroupRepositoryTest {

    private static final int EMPTY_REPO_SIZE = 0;
    private static final GroupName VALID_GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private static final GroupName SECOND_VALID_GROUP_NAME = new GroupName("secondGroup");
    private static final GroupName MISSING_GROUP = new GroupName("missingGroup");
    private Group group;
    private LocalGroupRepository localGroupRepository;

    @BeforeEach
    void setup() {
        localGroupRepository = new LocalGroupRepository();
        group = mock(Group.class);
        when(group.getName()).thenReturn(VALID_GROUP_NAME);

    }

    @Test
    void whenSavingGroup_thenNameIsReturned() {

        assertEquals(VALID_GROUP_NAME, localGroupRepository.saveByGroupName(VALID_GROUP_NAME, group));
    }

    @Test
    void givenSavedGroup_whenFindingGroup_thenReturnSavedGroup() throws NoGroupFoundException {
        localGroupRepository.saveByGroupName(VALID_GROUP_NAME, group);

        assertEquals(group, localGroupRepository.find(VALID_GROUP_NAME));
    }

    @Test
    void givenSavedGroups_whenFindingAllGroups_thenReturnAllGroupsOrderless() {
        Group groupB = mock(Group.class);
        when(groupB.getName()).thenReturn(SECOND_VALID_GROUP_NAME);
        localGroupRepository.saveByGroupName(group.getName(), group);
        localGroupRepository.saveByGroupName(groupB.getName(), groupB);

        List<Group> foundGroups = localGroupRepository.findAll();

        assertTrue(foundGroups.contains(group));
        assertTrue(foundGroups.contains(groupB));
    }

    @Test
    void whenFindingMissingGroup_thenNoGroupFoundExceptionIsThrown() {
        assertThrows(NoGroupFoundException.class, () -> localGroupRepository.find(MISSING_GROUP));
    }

    @Test
    void givenGroupRepositoryWithOneGroup_whenClearingGroups_thenInternalRepoIsCleared() {
        localGroupRepository.saveByGroupName(group.getName(), group);
        localGroupRepository.clear();

        assertEquals(EMPTY_REPO_SIZE, localGroupRepository.findAll().size());
    }
}
