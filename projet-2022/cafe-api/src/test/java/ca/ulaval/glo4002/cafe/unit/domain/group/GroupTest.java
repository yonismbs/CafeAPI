package ca.ulaval.glo4002.cafe.unit.domain.group;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.group.Group;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;

class GroupTest {

    private static final int GROUP_SIZE = 1;
    private static final int GROUP_SIZE_NO_MORE_PLACE = 0;
    private static final GroupName VALID_GROUP_NAME = new GroupName("Bulletproof Boy Scouts");
    private Client client;

    @BeforeEach
    public void setup() {
        client = mock(Client.class);
    }

    @Test
    void givenAFullGroup_whenAddingAClientToGroup_thenANoGroupSeatsExceptionIsThrown() {
        Group group = new Group(GROUP_SIZE_NO_MORE_PLACE, VALID_GROUP_NAME);

        assertThrows(NoGroupSeatsException.class, () -> group.add(client));
    }

    @Test
    void givenANonFullGroup_whenAddingAClientToGroup_thenNoExceptionIsThrown() {
        Group group = new Group(GROUP_SIZE, VALID_GROUP_NAME);
        assertDoesNotThrow(() -> group.add(client));
    }


}