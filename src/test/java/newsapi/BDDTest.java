package newsapi;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class BDDTest {

    public static final String API_KEY = "82edc87933e14dc38b65bc307bc86b33";
    public static RequestSpecification spec;

    @BeforeAll
    static void setUp() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .setBaseUri("https://newsapi.org/")
                .setBasePath("v2/everything")
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL);

        spec = builder.build();
    }


    @Test
    @DisplayName("Статьи теслы за послдений месяц")
    void stateTeslaOnMonth() {
        String str = "https://newsapi.org/v2/everything?q=tesla&from=2021-04-19&sortBy=publishedAt&apiKey=82edc87933e14dc38b65bc307bc86b33";

        Response response = RestAssured.given().spec(spec)
                .queryParam("sortBy", "publishedAt")
                .when().get("?q=tesla");

        JsonPath jsonPath = response.jsonPath();

        List<String> list = new ArrayList<>(jsonPath.get("articles"));
        System.out.println(list);
        // Assertions.assertTrue(list.contains("a"));

    }


    @Test
    @DisplayName("Статьи с упоминанием Apple за вчерашний день")
    public void stateApple() {
        Response response = RestAssured.given().spec(spec)
                .queryParam("sortBy", "popularity")
                .when().get("?q=apple");

        JsonPath jsonPath = response.jsonPath();
        System.out.println(jsonPath.get("status").toString());
        String str = jsonPath.get("status");
        Assertions.assertTrue(str.contains("ok"));


    }

    @Test
    @DisplayName("Все статьи о биткойнах")
    void articleBitcoin() {

        String str = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=82edc87933e14dc38b65bc307bc86b33";
        given().spec(spec)
                .when().get("?q=bitcoin")
                .then().log().all()
                .header("Server", Matchers.equalTo("cloudflare"));


    }
}
