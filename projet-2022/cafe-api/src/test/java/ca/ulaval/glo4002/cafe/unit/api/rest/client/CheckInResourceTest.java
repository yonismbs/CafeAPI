package ca.ulaval.glo4002.cafe.unit.api.rest.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ca.ulaval.glo4002.cafe.api.rest.checkIn.CheckInResource;
import ca.ulaval.glo4002.cafe.api.rest.client.ClientInformation;
import ca.ulaval.glo4002.cafe.application.service.CheckInService;
import ca.ulaval.glo4002.cafe.domain.cafe.exception.InsufficientSeatsException;
import ca.ulaval.glo4002.cafe.domain.client.Client;
import ca.ulaval.glo4002.cafe.domain.client.ClientId;
import ca.ulaval.glo4002.cafe.domain.client.exception.DuplicateClientIdException;
import ca.ulaval.glo4002.cafe.domain.group.GroupName;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupFoundException;
import ca.ulaval.glo4002.cafe.domain.group.exception.NoGroupSeatsException;
import jakarta.ws.rs.core.Response;

class CheckInResourceTest {

    private static final String CUSTOMERS_RESPONSE_HEADER_PATH = "/customers/";
    private static final String LOCATION_HEADER_PATH_PARAMETER = "Location";
    private static final String MISSING_GROUP_NAME = null;
    private static final ClientId CLIENT_ID = new ClientId("11111");
    private static final String CLIENT_NAME = "Jason Momoa";
    private CheckInService checkInService;
    private CheckInResource checkInResource;

    @BeforeEach
    public void setUp() {
        checkInService = mock(CheckInService.class);
        checkInResource = new CheckInResource(checkInService);
    }

    @Test
    void whenCheckInClient_thenShouldReturnCustomerIdInLocationHeader() throws NoGroupFoundException, InsufficientSeatsException, NoGroupSeatsException, DuplicateClientIdException {
        Client client = new Client(CLIENT_ID, CLIENT_NAME);
        ClientInformation clientInformation = new ClientInformation(CLIENT_NAME, CLIENT_ID.id(), MISSING_GROUP_NAME);

        when(checkInService.checkInClient(CLIENT_ID, CLIENT_NAME, GroupName.VOID)).thenReturn(client);

       Response response = checkInResource.checkInClient(clientInformation);
       assertEquals(CUSTOMERS_RESPONSE_HEADER_PATH + CLIENT_ID.id(), response.getHeaderString(LOCATION_HEADER_PATH_PARAMETER));
    }

}
