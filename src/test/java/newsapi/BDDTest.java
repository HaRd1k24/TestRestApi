package newsapi;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class BDDTest {

    public static final String API_KEY = "82edc87933e14dc38b65bc307bc86b33";
    public static RequestSpecification spec;

    @BeforeAll
    static void setUp() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .setBaseUri("https://newsapi.org/v2/everything")
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL);

        spec = builder.build();
    }

    //https://newsapi.org/v2/everything?q=tesla&from=2021-04-18&sortBy=publishedAt&apiKey=82edc87933e14dc38b65bc307bc86b33
    @Test
    @DisplayName("Статьи теслы за послдений месяц")
    void simpleTest() {

        given().spec(spec)
                .when().get("?q=tesla&from=2021-04-18&sortBy=publishedAt")
                .then().statusCode(200).log().all();


    }

    @Test
    @DisplayName("Статьи с упоминанием Apple за вчерашний день")
    public void stateApple() {
    given().spec(spec)
                .when().get("?q=apple&from=2021-05-17&to=2021-05-17&sortBy=popularity")
                .then().statusCode(200).log().all();


    }

    @Test
    @DisplayName("Все статьи о биткойнах")
    void articleBitcoin() {

        String str = "https://newsapi.org/v2/everything?q=bitcoin&apiKey=82edc87933e14dc38b65bc307bc86b33";
       given().spec(spec)
               .when().get("?q=bitcoin")
               .then()
               .statusCode(200).log().headers();

        // Устанавливаем базовый адрес запроса
        RestAssured.baseURI = str;

        // Получить объект HTTP-запроса
        RequestSpecification httpRequest = RestAssured.given();

        // Получаем ответное сообщение
        Response response = httpRequest.get(String.valueOf(spec));

        // Получить server из заголовка сообщения, возвращаемого сервером, и проверить его
        String server = response.header("Server");

        Assertions.assertEquals(server, "cloudflare");








    }
}
