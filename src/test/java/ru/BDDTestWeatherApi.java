package ru;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static io.restassured.RestAssured.given;

public class BDDTestWeatherApi {
    public static final String API_KEY = "4d85be9d306a41e099161606211805";
    public static RequestSpecification spec;

    @BeforeAll
    static void setUp() {
        //https://api.weatherapi.com/v1/current.json?key=4d85be9d306a41e099161606211805&q=London&aqi=no
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .setBaseUri("https://api.weatherapi.com/")
                .setBasePath("v1/")
                .addQueryParam("key", API_KEY)
                .log(LogDetail.ALL);

        spec = builder.build();
    }

    @Test
    @DisplayName("Погода в лондоне на 1 день")

        //https://api.weatherapi.com/v1/forecast.json?key=4d85be9d306a41e099161606211805&q=London&days=1&aqi=no&alerts=no
    void London() {
        Response response = RestAssured.given().spec(spec).log().all()
                .when().get("forecast.json?q=London&days=1aqi=noalerts=no");

        JsonPath jsonPath = response.jsonPath();


        List<String> list = new ArrayList<>(jsonPath.get("location"));
        System.out.println(list);


    }

    @Test
    @DisplayName("Виды спорта которые проходили в России")
    void sportInRussia() {
        Response response = RestAssured.given().spec(spec).
                queryParams("q", "Russia").log().all()
                .when().get("sports.json");


        //  https://api.weatherapi.com/v1/sports.json?key=4d85be9d306a41e099161606211805&q=Russia

        JsonPath jsonPath = response.jsonPath();
        List<String> list = new ArrayList<String>(jsonPath.get("football"));

        System.out.println(list);
        // Assertions.assertTrue(list.contains("stadium"));

    }

    @Test
    @DisplayName("Прогноз погода на 1 день в России")
    void weatherForecast() {
        String Str = "https://api.weatherapi.com/v1/forecast.json?key=4d85be9d306a41e099161606211805&q=Russia&days=1&aqi=no&alerts=no";
        given().spec(spec).when()
                .get("forecast.json?q=Russia&days=1aqi=noalerts=no")
                .then()
                .header("Content-Type", Matchers.equalTo("application/json"));


    }
}
