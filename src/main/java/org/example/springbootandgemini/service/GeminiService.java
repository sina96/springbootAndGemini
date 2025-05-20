package org.example.springbootandgemini.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class GeminiService {

   private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);

   @Value("${gemini.api.key}")
   private String apiKey;

   @Value("${gemini.model}")
   private String model;

   private final ObjectMapper objectMapper = new ObjectMapper();

   public Map<String, Object> generateResponse(String prompt) throws Exception {
      String url = String.format(
            "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s",
            model, apiKey
      );

      int promptTokens = estimateTokens(prompt);
      logger.info("Sending prompt to Gemini: {}", prompt);
      logger.info("Estimated prompt tokens: {}", promptTokens);

      try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
         HttpPost post = new HttpPost(url);
         post.setHeader("Content-Type", "application/json");

         String requestBody = """
            {
              "contents": [{
                "parts": [{ "text": "%s" }]
              }]
            }
            """.formatted(prompt);

         post.setEntity(new StringEntity(requestBody));

         var response = httpClient.execute(post);

         if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Gemini API error: " + response.getStatusLine());
         }

         var json = objectMapper.readTree(response.getEntity().getContent());

         JsonNode textNode = json
               .path("candidates")
               .get(0)
               .path("content")
               .path("parts")
               .get(0)
               .path("text");

         int responseTokens = estimateTokens(textNode.asText());

         logger.info("Gemini response: {}", textNode.asText());
         logger.info("Estimated response tokens: {}", responseTokens);

         Map<String, Object> output = new LinkedHashMap<>();
         output.put("answer", textNode.asText());
         output.put("promptTokens", promptTokens);
         output.put("responseTokens", responseTokens);
         output.put("totalEstimatedTokens", promptTokens + responseTokens);
         return output;
      }
   }



   public Map<String, Object> summarizeText(String input) throws Exception {
      String prompt = "Summarize the following text in clear, concise bullet points:\n\n" + input;
      return generateResponse(prompt);
   }

   public Map<String, Object> rewriteText(String input) throws Exception {
      String prompt = "Rewrite the following text to be clearer and more professional:\n\n" + input;
      return generateResponse(prompt);
   }

   public Map<String, Object> explainCode(String code) throws Exception {
      String prompt = "Explain what the following Java code does, in simple terms:\n\n" + code;
      return generateResponse(prompt);
   }

   private int estimateTokens(String text) {
      int wordCount = text.trim().split("\\s+").length;
      return (int) Math.round(wordCount * 1.3);  // ~1.3 tokens per word
   }
}