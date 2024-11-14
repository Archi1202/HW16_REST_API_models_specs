package tests.in.reqreg;

import models.UpdateUserRequestModel;
import models.UpdateUserSuccessResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.UpdateUserSpecs.updateRequestSpec;
import static specs.UpdateUserSpecs.updateResponseSpec200;

@DisplayName("Test aimed to verify the Update flow for API of users")
public class UpdateUserTests extends TestBase {

    @Tag("API")
    @Test
    @DisplayName("Verify the successful flow of user update via API")
    public void createUserSuccessTest() {
        UpdateUserRequestModel request = new UpdateUserRequestModel();
        request.setName("12333");
        request.setJob("test");

        UpdateUserSuccessResponseModel response = step("Send user update request with new data", () ->
                given(updateRequestSpec)
                        .body(request)
                        .contentType(JSON)
                .when()
                        .put("/users/2")
                .then()
                        .spec(updateResponseSpec200)
                        .extract().as(UpdateUserSuccessResponseModel.class));

        step("Verify response details", () -> {

            step("Name should match from request body", () ->
                    assertThat(response.getName(), equalTo("12333")));
            step("Job should match from request body", () ->
                    assertThat(response.getJob(), equalTo("test")));
            step("there is Updated Date information in response and it's NOT NULL", () ->
                    assertThat(response.getUpdatedAt(), is(notNullValue())));
        });
    }
}

