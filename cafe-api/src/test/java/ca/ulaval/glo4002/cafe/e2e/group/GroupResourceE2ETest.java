package ca.ulaval.glo4002.cafe.e2e.group;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.CafeServer;
import ca.ulaval.glo4002.cafe.api.rest.group.dto.GroupDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Response.Status;


class GroupResourceE2ETest {
    private static final String GROUP_NAME_A_STRING = "Bulletproof Boy Scouts";
    private static final int GROUP_MAX_SIZE_A = 2;
    private static final int GROUP_MINIMUM_SIZE = 2;
    private static final int ONE_GROUP_RESERVED_SIZE = 1;
    private static final String ERROR_RESPONSE_PARAMETER = "error";
    private static final String DESCRIPTION_RESPONSE_PARAMETER = "description";
    private static final String EXPECTED_GROUP_SIZE_ERROR = "INVALID_GROUP_SIZE";
    private static final String EXPECTED_GROUP_SIZE_ERROR_DESCRIPTION = "Groups must reserve at least two seats.";
    private static final String EXPECTED_DUPLICATE_GROUP_ERROR = "DUPLICATE_GROUP_NAME";
    private static final String EXPECTED_DUPLICATE_GROUP_ERROR_DESCRIPTION = "The specified group already made a reservation today.";
    private static final String EXPECTED_INSUFFICIENT_SEATS_ERROR = "INSUFFICIENT_SEATS";
    private static final String EXPECTED_INSUFFICIENT_SEATS_ERROR_DESCRIPTION = "There are currently no available seats. Please come back later.";
    private static final GroupDTO validGroupDto = new GroupDTO(GROUP_NAME_A_STRING, GROUP_MAX_SIZE_A);
    private static final GroupDTO invalidGroupDto = new GroupDTO(GROUP_NAME_A_STRING, GROUP_MINIMUM_SIZE - 1);
    private static final GroupDTO insufficientSeatsGroupDto = new GroupDTO(GROUP_NAME_A_STRING, Integer.MAX_VALUE);
    private CafeServer cafeServer;
    private Response response;


    @BeforeEach
    public void setup() {
        RestAssured.port = PORT;
        cafeServer = new CafeServer();
        cafeServer.run();
    }

    @AfterEach
    public void tearDown() {
        cafeServer.stop();
    }

    @Test
    void whenGetRequestOnReservations_thenEndpointReturnsStatus200() {
        response = GroupResourceE2ETestHelper.getGroups();

        assertEquals(Status.OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    void givenAddedValidGroup_whenGettingReservations_thenReturnsExpectedNumberOfGroups() {
        GroupResourceE2ETestHelper.addGroup(validGroupDto);
        response = GroupResourceE2ETestHelper.getGroups();

        assertEquals(ONE_GROUP_RESERVED_SIZE, response.getBody().as(List.class).size());
    }

    @Test
    void givenAValidGroup_whenPostRequestOnReservations_thenEndpointReturnsStatus200() {
        response = GroupResourceE2ETestHelper.addGroup(validGroupDto);

        assertEquals(Status.OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    void givenInvalidGroupSize_whenPostRequestOnReservations_thenEndpointReturnsStatus400() {
        response = GroupResourceE2ETestHelper.addGroup(invalidGroupDto);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void givenInvalidGroupSize_whenPostRequestOnReservations_thenEndpointReturnsMatchingErrorBody() {
        response = GroupResourceE2ETestHelper.addGroup(invalidGroupDto);

        String errorMsgCode = response.getBody().jsonPath().getString(ERROR_RESPONSE_PARAMETER);
        String errorMsgDescription = response.getBody().jsonPath().getString(DESCRIPTION_RESPONSE_PARAMETER);

        assertEquals(EXPECTED_GROUP_SIZE_ERROR, errorMsgCode);
        assertEquals(EXPECTED_GROUP_SIZE_ERROR_DESCRIPTION, errorMsgDescription);
    }

    @Test
    void givenDuplicateGroupName_whenPostRequestOnReservations_thenEndpointReturnsStatus400() {
        GroupResourceE2ETestHelper.addGroup(validGroupDto);
        response = GroupResourceE2ETestHelper.addGroup(validGroupDto);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void givenDuplicateGroupName_whenPostRequestOnReservations_thenEndpointReturnsMatchingErrorBody() {
        GroupResourceE2ETestHelper.addGroup(validGroupDto);
        response = GroupResourceE2ETestHelper.addGroup(validGroupDto);

        String errorMsgCode = response.getBody().jsonPath().getString(ERROR_RESPONSE_PARAMETER);
        String errorMsgDescription = response.getBody().jsonPath().getString(DESCRIPTION_RESPONSE_PARAMETER);

        assertEquals(EXPECTED_DUPLICATE_GROUP_ERROR, errorMsgCode);
        assertEquals(EXPECTED_DUPLICATE_GROUP_ERROR_DESCRIPTION, errorMsgDescription);
    }

    @Test
    void givenInsufficientSeats_whenPostRequestOnReservations_thenEndpointReturnsStatus400() {
        response = GroupResourceE2ETestHelper.addGroup(insufficientSeatsGroupDto);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void givenInsufficientSeats_whenPostRequestOnReservations_thenEndpointReturnsMatchingErrorBody() {
        response = GroupResourceE2ETestHelper.addGroup(insufficientSeatsGroupDto);

        String errorMsgCode = response.getBody().jsonPath().getString(ERROR_RESPONSE_PARAMETER);
        String errorMsgDescription = response.getBody().jsonPath().getString(DESCRIPTION_RESPONSE_PARAMETER);

        assertEquals(EXPECTED_INSUFFICIENT_SEATS_ERROR, errorMsgCode);
        assertEquals(EXPECTED_INSUFFICIENT_SEATS_ERROR_DESCRIPTION, errorMsgDescription);
    }

}