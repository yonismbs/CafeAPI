package ca.ulaval.glo4002.cafe.e2e.layout;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.PORT;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import ca.ulaval.glo4002.cafe.CafeServer;
import ca.ulaval.glo4002.cafe.api.rest.cube.dto.CubeDTO;
import ca.ulaval.glo4002.cafe.api.rest.seat.dto.SeatDTO;
import ca.ulaval.glo4002.cafe.domain.cafe.layout.cube.seat.SeatState;
import io.restassured.RestAssured;

class LayoutResourceE2ETest {

    private static final  String LAYOUT_ENDPOINT = "/layout";
    private static final String CORRECT_NAME = "Les 4-Fées";
    private static final String CUBES_RESPONSE = "cubes";
    private static final String NAME_RESPONSE = "name";
    private static final String DEFAULT_CUBE_1_NAME = "Bloom";
    private static final String DEFAULT_CUBE_2_NAME = "Merryweather";
    private static final String DEFAULT_CUBE_3_NAME = "Tinker Bell";
    private static final String DEFAULT_CUBE_4_NAME = "Wanda";
    private CafeServer cafeServer;


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
    void whenGetRequestOnLayout_thenEndpointReturnsStatus200() {
        RestAssured.when().get(LAYOUT_ENDPOINT).then().statusCode(200);
    }

    @Test
    void givenLayoutWasJustGenerated_whenGetRequestOnLayout_thenLayoutFirstSeatIsCorrect() {
        List<CubeDTO> cubeDTOS = RestAssured.when().get(LAYOUT_ENDPOINT)
                .then().extract().body().jsonPath().getList(CUBES_RESPONSE, CubeDTO.class);
        SeatDTO seatDto = cubeDTOS.get(0).seats().get(0);

        assertEquals(1, seatDto.number());
        assertEquals(SeatState.Available.name(), seatDto.status());
        assertNull(seatDto.customer_id());
        assertNull(seatDto.group_name());
    }

    @Test
    void whenGetRequestOnLayout_thenLayoutNameIsCorrect() {
        RestAssured.when().get(LAYOUT_ENDPOINT).then().body(NAME_RESPONSE, equalTo(CORRECT_NAME));
    }

    @Test
    void whenGetRequestOnLayout_thenLayoutCubeNamesAreCorrect() {
        RestAssured.when().get(LAYOUT_ENDPOINT).then().body(CUBES_RESPONSE,
                hasItems(
                        hasEntry(NAME_RESPONSE, DEFAULT_CUBE_1_NAME),
                        hasEntry(NAME_RESPONSE, DEFAULT_CUBE_2_NAME),
                        hasEntry(NAME_RESPONSE, DEFAULT_CUBE_3_NAME),
                        hasEntry(NAME_RESPONSE, DEFAULT_CUBE_4_NAME)));
    }

}