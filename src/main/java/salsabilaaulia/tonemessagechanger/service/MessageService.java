package salsabilaaulia.tonemessagechanger.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import salsabilaaulia.tonemessagechanger.dto.MessageRequest;

@Service
public class MessageService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final RestClient restClient;

    public MessageService() {
        this.restClient = RestClient.create();
    }

    public String changeTone(MessageRequest messageRequest) {
        String response = restClient.post()
                .uri(geminiApiUrl + "?key=" + geminiApiKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(createRequestBody(messageRequest))
                .retrieve()
                .body(String.class);
                
        return extractResponseContent(response);
    }

    public Map<String, Object> createRequestBody(MessageRequest messageRequest) {
        return Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", buildPrompt(messageRequest))
                        })
                }
        );
    }

    public String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            return rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();
        }
    }

    private String buildPrompt(MessageRequest messageRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Provide an alternative message with no explanation.\n");
        if (messageRequest.getTone() != null && !messageRequest.getTone().isEmpty()) {
            prompt.append("Use a ").append(messageRequest.getTone()).append(" tone.");
        }
        prompt.append("\nOriginal message: \n").append(messageRequest.getContent());
        return prompt.toString();
    }
}
