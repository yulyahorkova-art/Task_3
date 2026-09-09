package api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String BASE_URL = "https://qa-stellarburgers.education-services.ru/api";
    private static final String AUTH_REGISTER = "/auth/register";
    private static final String AUTH_LOGIN = "/auth/login";
    private static final String AUTH_USER = "/auth/user";

    public static Response register(UserGenerator user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL + AUTH_REGISTER)
                .then()
                .extract()
                .response();
    }

    public static Response login(UserGenerator user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(BASE_URL + AUTH_LOGIN)
                .then()
                .extract()
                .response();
    }

    public static Response deleteUser(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            return null;
        }
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(BASE_URL + AUTH_USER)
                .then()
                .extract()
                .response();
    }

    public static String getAccessToken(UserGenerator user) {
        try {
            Response response = login(user);
            if (response.statusCode() == 200) {
                return response.then().extract().path("accessToken");
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}