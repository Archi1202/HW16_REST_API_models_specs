package tests.in.reqreg;

import models.CreateUserRequestModel;
import models.CreateUserSuccessResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.CreateSpecs.createRequestSpec;
import static specs.CreateSpecs.createResponseSpec201;

@DisplayName("Test aimed to verify the Create flow for API of users")
public class CreateUserTests extends TestBase {


    @Test
    @DisplayName("Verify the successful flow of user creation via API")
    public void createUserSuccessTest() {
        CreateUserRequestModel request = new CreateUserRequestModel();
        request.setJob("eve.holt@reqres.in");
        request.setName("cityslicka");

        CreateUserSuccessResponseModel response = step("Send user create request with valid data", () ->
                given(createRequestSpec)
                        .body(request)
                        .contentType(JSON)
                .when()
                        .post("/users/2")
                .then()
                        .spec(createResponseSpec201)
                        .extract().as(CreateUserSuccessResponseModel.class));

        step("Verify that response ID is not null, and Name and Job match the request", () -> {
            assertThat("User ID should not be null", response.getId(), is(notNullValue()));
            assertThat("Name should match request", response.getName(), equalTo("cityslicka"));
            assertThat("Job should match request body", response.getJob(), equalTo("eve.holt@reqres.in"));
        });
    }
}

