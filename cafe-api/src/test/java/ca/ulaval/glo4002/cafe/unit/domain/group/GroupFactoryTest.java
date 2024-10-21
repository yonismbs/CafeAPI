package ca.ulaval.glo4002.cafe.unit.domain.group;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ca.ulaval.glo4002.cafe.domain.group.GroupFactory;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.DuplicateGroupNameException;
import ca.ulaval.glo4002.cafe.domain.group.exception.InvalidGroupSizeException;

class GroupFactoryTest {

    private static final GroupName VALID_GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private static final GroupName VALID_USED_NAME = new GroupName("Love Birds");
    private static final int MINIMUM_VALID_SIZE = 2;
    private static final int INVALID_SIZE = MINIMUM_VALID_SIZE - 1;

    private ArrayList<GroupName> usedNames;
    private GroupFactory groupFactory;

    @BeforeEach
    public void setup() {
        usedNames = new ArrayList<>();
        groupFactory = new GroupFactory(usedNames);
    }

    @Test
    void givenAGroupWithLessThanMinimumGroupSize_whenCreatingAGroup_thenAnInvalidGroupSizeExceptionIsThrown() {
        assertThrows(InvalidGroupSizeException.class, () -> groupFactory.getGroup(
                VALID_GROUP_NAME, INVALID_SIZE));
    }

    @Test
    void givenAGroupWithValidGroupSize_whenCreatingAGroup_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> groupFactory.getGroup(
                VALID_GROUP_NAME, MINIMUM_VALID_SIZE));
    }

    @Test
    void givenADuplicateGroupName_whenCreatingAGroup_thenADuplicateGroupNameExceptionIsThrown() {
        usedNames.add(VALID_GROUP_NAME);

        assertThrows(DuplicateGroupNameException.class, () -> groupFactory.getGroup(
                VALID_GROUP_NAME, MINIMUM_VALID_SIZE));
    }

    @Test
    void givenExistingGroupNamesWithNoDuplicate_whenCreatingAGroup_thenNoExceptionIsThrown() {
        usedNames.add(VALID_USED_NAME);

        assertDoesNotThrow(() -> groupFactory.getGroup(
                VALID_GROUP_NAME, MINIMUM_VALID_SIZE));
    }

    @Test
    void givenExactRemainingAvailableSeats_whenCreatingAGroup_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> groupFactory.getGroup(
                VALID_GROUP_NAME, MINIMUM_VALID_SIZE));
    }
}