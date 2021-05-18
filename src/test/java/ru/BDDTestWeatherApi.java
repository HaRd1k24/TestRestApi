package ru;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class BDDTestWeatherApi {
    public static final String API_KEY = "4d85be9d306a41e099161606211805";
    public static RequestSpecification spec;

    @BeforeAll
    static void setUp() {
        //https://api.weatherapi.com/v1/current.json?key=4d85be9d306a41e099161606211805&q=London&aqi=no
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .setBaseUri("https://api.weatherapi.com/v1/")
                .addQueryParam("key", API_KEY)
                .log(LogDetail.ALL);

        spec = builder.build();
    }

    @Test
    @DisplayName("Погода в лондоне")
        //https://api.weatherapi.com/v1/current.json?key=4d85be9d306a41e099161606211805&q=London&aqi=no
    void London() {
        given().spec(spec)
                .when().get("current.json?q=London&aqi=no")
                .then()
                .statusCode(200).log().all();

    }

    @Test
    @DisplayName("Виды спорта которые проходили в России")
    void sportInRussia() {
        given().spec(spec).when().
                get("sports.json?q=Russia")
                .then().
                statusCode(200).log().all();

        //  https://api.weatherapi.com/v1/sports.json?key= 4d85be9d306a41e099161606211805&q=Russia
    }

    @Test
    @DisplayName("Прогноз погода на 1 день в России")
    void weatherForecast() {
        String Str = "https://api.weatherapi.com/v1/forecast.json?key=4d85be9d306a41e099161606211805&q=Russia&days=1&aqi=no&alerts=no";
        given().spec(spec).when()
                .get("forecast.json?q=Russia&days=1aqi=noalerts=no")
                .then()
                .statusCode(200).log().headers();
        // Устанавливаем базовый адрес запроса
        RestAssured.baseURI = Str;

        // Получить объект HTTP-запроса
        RequestSpecification httpRequest = RestAssured.given();

        // Получаем ответное сообщение
        Response response = httpRequest.get(String.valueOf(spec));

        // Получить Content-Type из заголовка сообщения, возвращаемого сервером, и проверить его
        String contentType = response.header("Content-Type");

        Assertions.assertEquals(contentType, "application/json");



    }
}
