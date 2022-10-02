package test;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import static org.assertj.core.api.BDDAssertions.then;

class HelloIT {
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    @Test
    void shouldGetCachedValue() throws Exception {
        var firstResponse = get();
        then(firstResponse).startsWith("hi, it's ");

        var secondResponse = get();

        then(secondResponse).isEqualTo(firstResponse);
    }

    private static String get() throws Exception {
        var get = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/hi"))
            .header("Accept", "application/json")
            .GET()
            .build();
        var response = HTTP.send(get, BodyHandlers.ofString());

        then(response.statusCode()).isEqualTo(200);
        return response.body();
    }
}
