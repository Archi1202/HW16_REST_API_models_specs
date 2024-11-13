package tests.in.reqreg;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.CreateSpecs.createRequestSpec;
import static specs.CreateSpecs.invalidCreateResponseSpec400;
import static specs.UpdateUserSpecs.updateResponseSpec200;

@DisplayName("Test aimed to verify the Update flow for API of users")
public class UpdateUserTests extends TestBase {


    @Test
    @DisplayName("Verify the successful flow of user creation via API")
    public void createUserSuccessTest() {
        UpdateUserRequestModel request = new UpdateUserRequestModel();
        request.setName("12333");
        request.setJob("test");

        UpdateUserSuccessResponseModel response = step("Send user create request with valid data", () ->
                given(createRequestSpec)
                        .body(request)
                        .contentType(JSON)
                .when()
                        .put("/users/2")
                .then()
                        .spec(updateResponseSpec200)
                        .extract().as(UpdateUserSuccessResponseModel.class));

        step("Verify that response ID is not null, and Name and Job match the request", () -> {
            assertThat("User ID should not be null", response.getId(), is(notNullValue()));
            assertThat("Name should match request", response.getName(), equalTo("12333"));
            assertThat("Job should match request body", response.getJob(), equalTo("test"));
        });
    }

    @Test
    @DisplayName("Verify the invalid flow of user creation via API")
    void createInvalidUserTest() {
        CreateUserRequestModel request = new CreateUserRequestModel();
        request.setJob("");
        request.setName("");

        UpdateUserUnsuccessfulResponseModel response = step("Send user create request with incorrect data", () ->
                given(createRequestSpec)
                        .body(request)
                        .contentType(JSON)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(invalidCreateResponseSpec400)
                        .extract().as(UpdateUserUnsuccessfulResponseModel.class));

        step("Verify that response ID is not null, and Name and Job match the request", () -> {
            assertThat("Validation of error response", response.getError(),equalTo("Invalid data"));
        });
    }
}

