package ca.ulaval.glo4002.cafe.e2e;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.PORT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import ca.ulaval.glo4002.cafe.CafeServer;
import ca.ulaval.glo4002.cafe.api.rest.CloseResource;
import ca.ulaval.glo4002.cafe.application.service.CheckInService;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Response.Status;

public class CloseResourceTest {

    private static final String CLOSE_ENDPOINT = "close";
    private CafeServer cafeServer;
    private CheckInService checkInService;


    @BeforeEach
    public void setup() {
        RestAssured.port = PORT;
        cafeServer = new CafeServer();
        cafeServer.run();

        checkInService = mock(CheckInService.class);
    }

    @AfterEach
    public void tearDown() {
        cafeServer.stop();
    }

    @Test
    void whenPostCloseCafe_thenReturnA200() {
        RestAssured.when().post(CLOSE_ENDPOINT).then().statusCode(Status.OK.getStatusCode());
    }

    @Test
    void whenClosing_thenCheckOutAllIsCalled() {
        CloseResource endpoint = new CloseResource(checkInService);

        endpoint.closeCafe();

        verify(checkInService).close();
    }

}
