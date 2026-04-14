package org.mp2.translator.llm;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeminiLlmClient implements LlmClient {
    private static final String MODEL = "gemini-1.5-flash";
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String apiKey;

    public GeminiLlmClient(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String translate(String text, String sourceLanguage, String targetLanguage) {
        String prompt = "Translate the following text from " + sourceLanguage + " to Moroccan Arabic dialect (Darija). "
                + "Return only the translated sentence with no explanation: " + text;

        try {
            String payload = objectMapper.createObjectNode()
                    .putArray("contents")
                    .addObject()
                    .putArray("parts")
                    .addObject()
                    .put("text", prompt)
                    .toString();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(ENDPOINT, MODEL, apiKey)))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                return "Translation failed (Gemini API error): " + response.body();
            }

            JsonNode root = objectMapper.readTree(response.body());
            return root.path("candidates").path(0).path("content").path("parts").path(0).path("text").asText();
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Translation failed: " + e.getMessage();
        }
    }

    @Override
    public String modelName() {
        return MODEL;
    }
}
