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
                .queryParam("from", "2021-04-19")
                .queryParam("sortBy", "publishedAt")
                .when().get("?q=tesla");

        JsonPath jsonPath = response.jsonPath();
        List<Object> title = jsonPath.getList("articles.title");
        title.forEach(System.out::println);
        Assertions.assertFalse(title.isEmpty());
    }

    @Test
    @DisplayName("Статьи с упоминанием Apple за вчерашний день")
    public void stateApple() {
        Response response = RestAssured.given().spec(spec)
                .queryParam("from", "2021-05-18")
                .queryParam("to", "2021-05-18")
                .queryParam("sortBy", "popularity")
                .when().get("?q=apple");
        //https://newsapi.org/v2/everything?q=apple&from=2021-05-18&to=2021-05-18&
        // sortBy=popularity&apiKey=82edc87933e14dc38b65bc307bc86b33

        JsonPath jsonPath = response.jsonPath();

        List<Object> list = jsonPath.getList("articles.author");
        list.forEach(System.out::println);
    }

    @Test
    @DisplayName("Все статьи о биткойнах")
    void articleBitcoin() {
        //  String str = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=82edc87933e14dc38b65bc307bc86b33";
        given().spec(spec)
                .when().get("q=bitcoin")
                .then().log().all()
                .header("Server", Matchers.equalTo("cloudflare"));
    }
}
