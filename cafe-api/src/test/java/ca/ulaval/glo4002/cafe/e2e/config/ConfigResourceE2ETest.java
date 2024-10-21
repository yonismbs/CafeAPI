package ca.ulaval.glo4002.cafe.e2e.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.PORT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ca.ulaval.glo4002.cafe.CafeServer;
import ca.ulaval.glo4002.cafe.api.rest.config.dto.ConfigDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Response.Status;

class ConfigResourceE2ETest {
    private static final String ERROR_RESPONSE_PARAMETER = "error";
    private static final String DESCRIPTION_RESPONSE_PARAMETER = "description";
    private static final String EXPECTED_RESERVATION_NAME_ERROR = "INVALID_GROUP_RESERVATION_METHOD";
    private static final String EXPECTED_RESERVATION_NAME_ERROR_DESCRIPTION = "The group reservation method is not supported.";
    private static final String EXPECTED_COUNTRY_ERROR = "INVALID_COUNTRY";
    private static final String EXPECTED_COUNTRY_ERROR_DESCRIPTION = "The specified country is invalid.";
    private static final String EXPECTED_STATE_OR_PROVINCE_ERROR = "INVALID_STATE_OR_PROVINCE";
    private static final String EXPECTED_STATE_OR_PROVINCE_ERROR_DESCRIPTION = "The state or province you provided doesn't exist.";
    private static final String EXPECTED_GROUP_TIP_RATE_ERROR = "INVALID_GROUP_TIP_RATE";
    private static final String EXPECTED_GROUP_TIP_RATE_ERROR_DESCRIPTION = "The group tip rate must be set to a value between 0 to 100.";
    private static final String GROUP_RESERVATION_METHOD = "Full Cubes";
    private static final String INVALID_GROUP_RESERVATION_METHOD = "Full Full";
    private static final String ORGANIZATION_NAME = "4-Fées";
    private static final int CUBE_SIZE = 7;
    private static final String COUNTRY = "CA";
    private static final String INVALID_COUNTRY = "invalid";
    private static final String PROVINCE = "QC";
    private static final String INVALID_PROVINCE = "TEXAS";
    private static final String STATE = "A";
    private static final float GROUP_TIP_RATE = 15;
    private static final float INVALID_GROUP_TIP_RATE = 150;

    private final static ConfigDTO CANADIAN_QUEBEC_BASIC_CONFIG = new ConfigDTO(GROUP_RESERVATION_METHOD, ORGANIZATION_NAME, CUBE_SIZE,
            COUNTRY, PROVINCE, STATE, GROUP_TIP_RATE);
    private final static ConfigDTO INVALID_GROUP_RESERVATION_NAME_CONFIG = new ConfigDTO(INVALID_GROUP_RESERVATION_METHOD, ORGANIZATION_NAME, CUBE_SIZE,
            COUNTRY, PROVINCE, STATE, GROUP_TIP_RATE);
    private final static ConfigDTO INVALID_COUNTRY_CONFIG = new ConfigDTO(GROUP_RESERVATION_METHOD, ORGANIZATION_NAME, CUBE_SIZE,
            INVALID_COUNTRY, PROVINCE, STATE, GROUP_TIP_RATE);
    private final static ConfigDTO INVALID_STATE_OR_PROVINCE_CONFIG = new ConfigDTO(GROUP_RESERVATION_METHOD, ORGANIZATION_NAME, CUBE_SIZE,
            COUNTRY, INVALID_PROVINCE, STATE, GROUP_TIP_RATE);
    private final static ConfigDTO INVALID_GROUP_TIP_RATE_CONFIG = new ConfigDTO(GROUP_RESERVATION_METHOD, ORGANIZATION_NAME, CUBE_SIZE,
            COUNTRY, PROVINCE, STATE, INVALID_GROUP_TIP_RATE);
    
    private CafeServer cafeServer;
    private Response response;

    @BeforeEach
    public void setUp() {
        RestAssured.port = PORT;
        cafeServer = new CafeServer();
        cafeServer.run();
    }

    @AfterEach
    public void tearDown() {
        cafeServer.stop();
    }

    @Test
    void givenValidRequest_whenPostOnConfigEndpoint_thenReturnsExpectedStatusCode() {
        response = ConfigResourceE2ETestHelper.changeConfig(CANADIAN_QUEBEC_BASIC_CONFIG);

        assertEquals(Status.OK.getStatusCode(), response.getStatusCode());
    }

    @Test
    void giveInvalidGroupReservationMethod_whenPostOnConfigEndpoint_thenReturnsExpectedStatusCode() {
        response = ConfigResourceE2ETestHelper.changeConfig(INVALID_GROUP_RESERVATION_NAME_CONFIG);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void giveInvalidGroupReservationMethod_whenPostOnConfigEndpoint_thenReturnsExpectedErrorMessage() {
        response = ConfigResourceE2ETestHelper.changeConfig(INVALID_GROUP_RESERVATION_NAME_CONFIG);

        String errorMsgCode = response.getBody().jsonPath().getString(ERROR_RESPONSE_PARAMETER);
        String errorMsgDescription = response.getBody().jsonPath().getString(DESCRIPTION_RESPONSE_PARAMETER);

        assertEquals(EXPECTED_RESERVATION_NAME_ERROR, errorMsgCode);
        assertEquals(EXPECTED_RESERVATION_NAME_ERROR_DESCRIPTION, errorMsgDescription);
    }

    @Test
    void giveInvalidCountry_whenPostOnConfigEndpoint_thenReturnsExpectedStatusCode() {
        response = ConfigResourceE2ETestHelper.changeConfig(INVALID_COUNTRY_CONFIG);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void giveInvalidCountry_whenPostOnConfigEndpoint_thenReturnsExpectedErrorMessage() {
        response = ConfigResourceE2ETestHelper.changeConfig(INVALID_COUNTRY_CONFIG);

        String errorMsgCode = response.getBody().jsonPath().getString(ERROR_RESPONSE_PARAMETER);
        String errorMsgDescription = response.getBody().jsonPath().getString(DESCRIPTION_RESPONSE_PARAMETER);

        assertEquals(EXPECTED_COUNTRY_ERROR, errorMsgCode);
        assertEquals(EXPECTED_COUNTRY_ERROR_DESCRIPTION, errorMsgDescription);
    }

    @Test
    void giveInvalidStateOrProvince_whenPostOnConfigEndpoint_thenReturnsExpectedStatusCode() {
        response = ConfigResourceE2ETestHelper.changeConfig(INVALID_STATE_OR_PROVINCE_CONFIG);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void giveInvalidStateOrProvince_whenPostOnConfigEndpoint_thenReturnsExpectedErrorMessage() {
        response = ConfigResourceE2ETestHelper.changeConfig(INVALID_STATE_OR_PROVINCE_CONFIG);

        String errorMsgCode = response.getBody().jsonPath().getString(ERROR_RESPONSE_PARAMETER);
        String errorMsgDescription = response.getBody().jsonPath().getString(DESCRIPTION_RESPONSE_PARAMETER);

        assertEquals(EXPECTED_STATE_OR_PROVINCE_ERROR, errorMsgCode);
        assertEquals(EXPECTED_STATE_OR_PROVINCE_ERROR_DESCRIPTION, errorMsgDescription);
    }

    @Test
    void giveInvalidGroupTipRate_whenPostOnConfigEndpoint_thenReturnsExpectedStatusCode() {
        response = ConfigResourceE2ETestHelper.changeConfig(INVALID_GROUP_TIP_RATE_CONFIG);

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void giveInvalidGroupTipRate_whenPostOnConfigEndpoint_thenReturnsExpectedErrorMessage() {
        response = ConfigResourceE2ETestHelper.changeConfig(INVALID_GROUP_TIP_RATE_CONFIG);

        String errorMsgCode = response.getBody().jsonPath().getString(ERROR_RESPONSE_PARAMETER);
        String errorMsgDescription = response.getBody().jsonPath().getString(DESCRIPTION_RESPONSE_PARAMETER);

        assertEquals(EXPECTED_GROUP_TIP_RATE_ERROR, errorMsgCode);
        assertEquals(EXPECTED_GROUP_TIP_RATE_ERROR_DESCRIPTION, errorMsgDescription);
    }

}