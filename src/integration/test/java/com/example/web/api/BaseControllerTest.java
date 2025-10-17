package com.example.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

public abstract class BaseControllerTest {
  private static final Path OUTPUT_DIR = Path.of("target/integration");

  protected final ObjectMapper objectMapper = new ObjectMapper();

  protected MultiValueMap<String, String> getDefaultHeaders() {
    MultiValueMap<String, String> headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    return headers;
  }

  protected void writeJsonResponse(String response, String fileName) throws IOException {
    writeResponse(formatJson(response), fileName);
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
