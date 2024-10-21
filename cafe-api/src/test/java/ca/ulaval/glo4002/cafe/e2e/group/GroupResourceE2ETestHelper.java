package ca.ulaval.glo4002.cafe.e2e.group;

import static ca.ulaval.glo4002.cafe.e2e.E2ETestHelper.CONTENT_TYPE;

import ca.ulaval.glo4002.cafe.api.rest.group.dto.GroupDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GroupResourceE2ETestHelper {
    private final static String GROUPS_ENDPOINT = "/reservations";

    public static Response getGroups() {
        return RestAssured.given()
                .when()
                .get(GROUPS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

    public static Response addGroup(GroupDTO groupDTO) {
        return RestAssured.given()
                .contentType(CONTENT_TYPE)
                .body(groupDTO)
                .when()
                .post(GROUPS_ENDPOINT)
                .then()
                .extract()
                .response();
    }

}
