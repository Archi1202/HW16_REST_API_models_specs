package tests.in.reqreg;

import models.CreateUserRequestModel;
import models.CreateUserSuccessResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.CreateUserSpecs.createRequestSpec;
import static specs.CreateUserSpecs.createResponseSpec201;

@DisplayName("Test aimed to verify the Create flow for API of users")
public class CreateUserTests extends TestBase {


    @Tag("API")
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

        step("Validate response details", () -> {
            step("Verify User ID is not null", () ->
                    assertThat(response.getId(), is(notNullValue())));

            step("Verify Name matches request", () ->
                    assertThat(response.getName(), equalTo("cityslicka")));

            step("Verify Job matches request body", () ->
                    assertThat(response.getJob(), equalTo("eve.holt@reqres.in")));
        });
    }
}

