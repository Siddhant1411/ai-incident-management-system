package com.incident.aianalysisservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaClient {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, String> analyze(String description) {

        System.out.println("➡️ Calling Ollama...");

        String url = "http://localhost:11434/api/generate";

        Map<String, Object> request = new HashMap<>();
        request.put("model", "llama3:latest");
        request.put("prompt",
                "You are an incident classification system.\n\n" +

                        "Classify into:\n" +
                        "TYPES:\n" +
                        "- DB_ERROR → database issues\n" +
                        "- API_ERROR → API/timeout/network\n" +
                        "- GENERAL → others\n\n" +

                        "PRIORITY:\n" +
                        "- HIGH → critical\n" +
                        "- MEDIUM → moderate\n" +
                        "- LOW → minor\n\n" +

                        "STRICT RULES:\n" +
                        "- Return ONLY JSON\n" +
                        "- No explanation\n\n" +

                        "EXAMPLES:\n" +
                        "Input: database connection error\n" +
                        "Output: {\"type\":\"DB_ERROR\",\"priority\":\"HIGH\"}\n\n" +

                        "Input: api timeout error\n" +
                        "Output: {\"type\":\"API_ERROR\",\"priority\":\"HIGH\"}\n\n" +

                        "Input: file not found\n" +
                        "Output: {\"type\":\"GENERAL\",\"priority\":\"LOW\"}\n\n" +

                        "Input: " + description + "\n" +
                        "Output:"
        );
        request.put("stream", false);

        try {
            Map<String, Object> response =
                    restTemplate.postForObject(url, request, Map.class);

            System.out.println("⬅️ Ollama raw response: " + response);

            // 🔥 Extract actual AI JSON string
            String json = response.get("response").toString();

            // 🔥 Convert JSON string → Map
            return objectMapper.readValue(json, Map.class);

        } catch (Exception e) {
            e.printStackTrace();

            // fallback (important for Kafka flow)
            Map<String, String> fallback = new HashMap<>();
            fallback.put("type", "GENERAL");
            fallback.put("priority", "MEDIUM");
            return fallback;
        }
    }

}