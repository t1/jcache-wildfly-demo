package test;

import com.github.t1.testcontainers.jee.JeeContainer;
import com.github.t1.testcontainers.jee.WildflyContainer;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import static org.assertj.core.api.BDDAssertions.then;

@Testcontainers
class HelloIT {
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    @Container @SuppressWarnings("resource")
    static JeeContainer CONTAINER = new WildflyContainer("rdohna/wildfly", "26.1-jdk17")
        .withDeployment("target/ROOT.war");

    @Test
    void shouldGetCachedValue() throws Exception {
        var firstResponse = get();
        then(firstResponse).startsWith("hi, it's ");
        Thread.sleep(10);

        var secondResponse = get();

        then(secondResponse).isEqualTo(firstResponse);
    }

    private static String get() throws Exception {
        var get = HttpRequest.newBuilder()
            .uri(CONTAINER.baseUri().resolve("hi"))
            .header("Accept", "application/json")
            .GET()
            .build();
        var response = HTTP.send(get, BodyHandlers.ofString());

        then(response.statusCode()).isEqualTo(200);
        return response.body();
    }
}
