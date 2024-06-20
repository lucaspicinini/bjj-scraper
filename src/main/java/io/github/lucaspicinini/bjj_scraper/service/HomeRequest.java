package io.github.lucaspicinini.bjj_scraper.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HomeRequest {
    private static final String URL = "https://www.bjjheroes.com/a-z-bjj-fighters-list";
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final HttpRequest REQUEST = HttpRequest.newBuilder().uri(URI.create(URL)).build();

    public static String getResponseBody() {
        HttpResponse<String> response = null;

        try {
            response = CLIENT.send(REQUEST, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Erro na requisição da home: " + e.getMessage());
        }

        return response != null ? response.body() : "Erro na requisição da home.";
    }
}
