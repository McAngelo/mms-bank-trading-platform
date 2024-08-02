package com.mms.market_data_service.helper;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class ExternalApiRequests {

    public String BASE_URL;
    private final Gson gson = new Gson();

    public ExternalApiRequests(String baseUrl){
        this.BASE_URL = "https://"+ baseUrl +".matraining.com/";
    }


    public HttpResponse<String> getRequests(String endpoint) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofSeconds(10))
                .uri(URI.create(BASE_URL + "/" + endpoint))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response;
    }

    public HttpResponse<String> getOneRequest(String endpoint, String Id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .timeout(Duration.ofSeconds(10))
                .uri(URI.create(BASE_URL + "/" + endpoint + "/" + Id))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response;
    }

    public HttpResponse<String> postRequests(String endpoint, String data, String contentType) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .timeout(Duration.ofSeconds(10))
                .uri(URI.create(BASE_URL + "/" + endpoint))
                .header("Content-Type", contentType) // "application/json" or  "text/plain"
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response;
    }

    public HttpResponse<String> putRequests(String endpoint, Object data) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(convertToJson(data)))
                .timeout(Duration.ofSeconds(10))
                .uri(URI.create(BASE_URL + "/" + endpoint))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response;
    }

    public HttpResponse<String> deleteRequests(String endpoint, String Id) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .timeout(Duration.ofSeconds(10))
                .uri(URI.create(BASE_URL + "/" + endpoint + "/" + Id))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
        return response;
    }

    private String convertToJson(Object data) {
        return gson.toJson(data);
    }
}
