package io.github.lucaspicinini.bjj_scraper.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FighterRequest {
    private static final String URL = "https://www.bjjheroes.com";
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();

    public static String getResponseBody(String fighterHref) {
        var fighterUrl = URL + fighterHref;
        HttpResponse<String> response = null;

        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(fighterUrl)).build();
            response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Erro na requisição do atleta: " + e.getMessage());
        }

        return response != null ? response.body() : "Erro na requisição do atleta.";
    }
}
