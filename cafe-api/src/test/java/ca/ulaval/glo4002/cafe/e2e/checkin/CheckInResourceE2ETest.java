package ca.ulaval.glo4002.cafe.e2e.checkin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.PORT;
import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.cafe.CafeServer;
import io.restassured.RestAssured;
import io.restassured.response.Response;

class CheckInResourceE2ETest {

    private static final int EXPECTED_CREATED_STATUS_CODE = 201;
    private static final String CLIENT_ID = "11111";
    private static final String BILL_PATH = "/bill";
    private static final String LOCATION_HEADER = "Location";
    private CafeServer cafeServer;
    private Response response;

    @BeforeEach
    public void setUp() {
        RestAssured.port = PORT;
        cafeServer = new CafeServer();
        cafeServer.run();

        response = CheckInResourceE2ETestHelper.checkInCustomer();
    }

    @AfterEach
    public void tearDown() {
        cafeServer.stop();
    }

    @Test
    void givenValidCustomer_whenCheckingInClient_thenReturnsExpectedStatusCode() {
        assertEquals(EXPECTED_CREATED_STATUS_CODE, response.statusCode());
    }

    @Test
    void givenValidRequest_whenCheckingInClient_thenLocationHeaderIsValid(){
        assertNotNull(response.getHeader(LOCATION_HEADER));
        assertTrue(response.getHeader(LOCATION_HEADER).contains(CLIENT_ID));
    }

    @Test
    void givenValidCustomer_whenCheckingOutClient_thenReturnsExpectedStatusCode(){
        response = CheckInResourceE2ETestHelper.checkOutCustomer();

        assertEquals(EXPECTED_CREATED_STATUS_CODE, response.statusCode());
    }

    @Test
    void givenValidRequest_whenCheckingOutClient_thenLocationHeaderIsValid(){
        response = CheckInResourceE2ETestHelper.checkOutCustomer();

        assertNotNull(response.getHeader(LOCATION_HEADER));
        assertTrue(response.getHeader(LOCATION_HEADER).contains(CLIENT_ID));
        assertTrue(response.getHeader(LOCATION_HEADER).contains(BILL_PATH));
    }

}
