package com.example.web.api;

import com.example.integration.HeadersBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

public abstract class BaseControllerTest {
  protected static final String BASE_URL = "http://localhost:8080";

  private static final Path OUTPUT_DIR = Path.of("target/integration");

  protected static ObjectMapper objectMapper;

  @BeforeAll
  static void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
  }

  protected MultiValueMap<String, String> getDefaultHeaders() {
    return HeadersBuilder.create()
        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  protected byte[] loadResource(String fileName) throws IOException {
    return getClass().getResourceAsStream(fileName).readAllBytes();
  }

  protected String loadResourceAsString(String fileName) throws IOException {
    return new String(loadResource(fileName), StandardCharsets.UTF_8);
  }

  protected void writeJsonResponse(String response, String fileName) throws IOException {
    writeResponse(formatJson(response), fileName);
  }

  protected void writeJsonResponse(Object response, String fileName) throws IOException {
    writeResponse(formatJson(objectMapper.writeValueAsString(response)), fileName);
  }

  protected void writeResponse(String response, String fileName) throws IOException {
    Files.createDirectories(OUTPUT_DIR);
    Files.writeString(OUTPUT_DIR.resolve(fileName), response, StandardCharsets.UTF_8);
  }

  private String formatJson(String json) throws JsonProcessingException {
    return objectMapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(objectMapper.readValue(json, Object.class));
  }
}
