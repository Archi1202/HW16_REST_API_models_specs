package tests.in.reqreg;

import models.RegisterUserRequestModel;
import models.RegisterUserSuccessResponseModel;
import models.RegisterUserUnsuccessfulResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.RegisterUserSpecs.*;

@DisplayName("Verification of Register flow for API")
public class RegisterUserTests extends TestBase {

    @Tag("API")
    @Test
    @DisplayName("Verify the success flow for register functionality")
    void registerUserSuccessTest() {
        RegisterUserRequestModel request = new RegisterUserRequestModel();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword("pistol");

        RegisterUserSuccessResponseModel response = step("Register user with valid data", () ->
                given(registerRequestSpec)
                        .body(request)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registerResponseSpec200)
                        .extract().as(RegisterUserSuccessResponseModel.class));

        step("Verify that in response ", () -> {
            assertThat("User token should not be null", response.getToken(), is(notNullValue()));
            assertThat("User token should be alphanumeric", response.getToken().matches("^[a-zA-Z0-9]+$"), is(true));
            assertThat("User token length should be at least 17 characters", response.getToken().length(), greaterThanOrEqualTo(17));
            assertThat("User ID should not be null", response.getId(), is(notNullValue()));
            assertThat("User ID should be numeric", response.getId().matches("\\d+"), is(true));

        });
    }

    @Tag("API")
    @Test
    @DisplayName("Verify the flow for register functionality when there is invalid data in both email and password fields")
    void registerUserWithInvalidDataTest() {
        RegisterUserRequestModel request = new RegisterUserRequestModel();
        request.setEmail("eve.holt");
        request.setPassword("pisto");

        RegisterUserUnsuccessfulResponseModel response = step("Register user with incorrect data", () ->
                given(registerRequestSpec)
                        .body(request)
                        .when()
                        .post("/register")
                        .then()
                        .spec(invalidRegisterResponseSpec400)
                        .extract().as(RegisterUserUnsuccessfulResponseModel.class));

        step("Verify the error response data", () -> {
            assertThat("Error should be presented", response.getError(), is(notNullValue()));
            assertThat("Error should have specific text", response.getError(), equalTo("Note: Only defined users succeed registration"));
        });
    }

    @Tag("API")
    @Test
    @DisplayName("Verify the flow for register functionality when there is NULL in password field")
    void registerUserWithMissingPasswordTest() {
        RegisterUserRequestModel request = new RegisterUserRequestModel();
        request.setEmail("eve.holt@reqres.in");
        request.setPassword(null);

        RegisterUserUnsuccessfulResponseModel response = step("Register user with incomplete data", () ->
                given(registerRequestSpec)
                        .body(request)
                        .when()
                        .post("/register")
                        .then()
                        .spec(invalidRegisterResponseSpec400)
                        .extract().as(RegisterUserUnsuccessfulResponseModel.class));

        step("Verify that response ", () -> {
            assertThat("Error should be presented", response.getError(), is(notNullValue()));
            assertThat("Error should have specific text", response.getError(), equalTo("Missing password"));
        });
    }
}
