package com.ChatBot.Chatbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OllamaService {

    public Map<String, String> getResponse(String userMessage) throws Exception {

        String url = "http://localhost:11434/api/generate";

        String prompt = "You are a helpful assistant. Give answer in 150 words.\nUser: "
                + userMessage + "\nAssistant:";

        // ✅ Use ObjectMapper (safe JSON)
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("model", "llama3.2:3b");
        requestMap.put("prompt", prompt);
        requestMap.put("stream", false);

        String body = mapper.writeValueAsString(requestMap);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, entity, String.class);

        // ✅ Parse response
        JsonNode jsonNode = mapper.readTree(response.getBody());

        String replyText = jsonNode.get("response").asText();

        // ✅ Return JSON format
        Map<String, String> result = new HashMap<>();
        result.put("reply", replyText);

        return result;
    }
}