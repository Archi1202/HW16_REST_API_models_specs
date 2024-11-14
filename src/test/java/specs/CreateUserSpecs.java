package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class CreateUserSpecs {
    public static RequestSpecification createRequestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .contentType(JSON);


    public static ResponseSpecification createResponseSpec201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(ALL)
            .build();
}