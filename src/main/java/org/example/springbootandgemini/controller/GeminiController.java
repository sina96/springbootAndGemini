package org.example.springbootandgemini.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.springbootandgemini.service.GeminiService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/gemini")
@Tag(name = "Gemini AI", description = "Endpoints powered by Google Gemini API")
public class GeminiController {

   private final GeminiService geminiService;

   public GeminiController(GeminiService geminiService) {
      this.geminiService = geminiService;
   }

   @Operation(summary = "Basic prompt")
   @GetMapping("/ask")
   public ResponseEntity<?> askGemini(@RequestParam String prompt) {
      try {
         return ResponseEntity.ok(geminiService.generateResponse(prompt));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
      }
   }

   @Operation(summary = "Summarize text")
   @PostMapping(value = "/summarize", consumes = MediaType.TEXT_PLAIN_VALUE)
   public ResponseEntity<?> summarize(@RequestBody String input) {
      try {
         return ResponseEntity.ok(geminiService.summarizeText(input));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
      }
   }

   @Operation(summary = "Rewrite text more clearly or professionally")
   @PostMapping(value = "/rewrite", consumes = MediaType.TEXT_PLAIN_VALUE)
   public ResponseEntity<?> rewrite(@RequestBody String input) {
      try {
         return ResponseEntity.ok(geminiService.rewriteText(input));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
      }
   }

   @Operation(summary = "Explain code snippet")
   @PostMapping(value = "/explain-code", consumes = MediaType.TEXT_PLAIN_VALUE)
   public ResponseEntity<?> explainCode(@RequestBody String code) {
      try {
         return ResponseEntity.ok(geminiService.explainCode(code));
      } catch (Exception e) {
         return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
      }
   }
}

