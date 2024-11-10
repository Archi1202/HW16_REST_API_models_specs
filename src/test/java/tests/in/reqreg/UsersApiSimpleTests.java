package tests.in.reqreg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@DisplayName("Check API methods related to the Users from website - https://reqres.in for practice purposes")


public class UsersApiSimpleTests extends TestBase {

    @Test
    public void successUpdateOfUserTest(){
        String updateData = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
        given()
                .body(updateData)
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name",is("morpheus"));
    }

    @Test
    public void updateOfUserWithInvalidDataTest(){
        String updateData = "03948390485";
        given()
                .body(updateData)
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(400);
    }

    @Test
    public void getSingleUserTest(){
        given()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.first_name",is("Janet"))
                .body("support.url",is(notNullValue()));
    }

    @Test
    public void getSingleUserIdAndAvatarTest(){
        String expectedAvatarUrl = "https://reqres.in/img/faces/2-image.jpg";
        int userId = 2;
        given()
                .get("/users/"+ userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id",is(userId))
                .body("data.avatar",is(expectedAvatarUrl));
    }

    @Test
    public void getMissingSingleUserTest(){
        int userId = 999;
        given()
                .get("/users/"+userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
