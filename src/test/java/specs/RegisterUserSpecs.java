package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class RegisterUserSpecs {
    public static RequestSpecification registerRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(JSON);

    public static ResponseSpecification registerResponseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(STATUS)
            .log(ALL)
            .build();

    public static ResponseSpecification invalidRegisterResponseSpec400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(STATUS)
            .log(ALL)
            .build();
}