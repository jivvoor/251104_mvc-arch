package org.example.mvc.model.repository;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.mvc.model.entity.Pet;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class SupabasePetRepository implements PetRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String apiURL;
    private final String apiKey;

    public SupabasePetRepository() {
        Dotenv dotenv = Dotenv
                .configure().ignoreIfMissing() // dotenv 없으면 환경변수 불러오겠다
                .load();
        apiURL = dotenv.get("SUPABASE_API_URL");
        apiKey = dotenv.get("SUPABASE_API_KEY");
    }

    @Override
    public List<Pet> findAll() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/rest/v1/pet?select=*".formatted(apiURL)))
                .GET()
                .headers("Authorization", "Bearer %s".formatted(apiKey), "apikey", apiKey)
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//            return objectMapper.readValue(response.body(), new TypeReference<List<Pet>>(){});
            return objectMapper.readValue(response.body(), new TypeReference<>(){});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Pet pet) {
        String payload = objectMapper.writeValueAsString(pet);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/rest/v1/pet".formatted(apiURL)))
                .POST(HttpRequest.BodyPublishers.ofString(
                        payload
                ))
                .headers("Authorization", "Bearer %s".formatted(apiKey),
                        "apikey", apiKey,
                        "Content-Type", "application/json",
                        "Prefer", "return=minimal")
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            if (response.body().isBlank()) {
                System.out.println(response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}