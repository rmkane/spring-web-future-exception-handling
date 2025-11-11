package com.example.integration;

import com.example.integration.request.HeadersBuilder;
import com.example.integration.request.RestFetcher;
import com.example.integration.util.ResourceLoader;
import com.example.integration.util.ResponseWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class BaseControllerTest {
  protected static final String BASE_URL = "http://localhost:8080";

  protected static ObjectMapper objectMapper;
  protected static RestFetcher restFetcher;

  @BeforeAll
  static void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    // Reuse a single RestTemplate instance across all tests (thread-safe)
    restFetcher = new RestFetcher(new RestTemplate());
  }

  protected MultiValueMap<String, String> getDefaultHeaders() {
    return HeadersBuilder.create()
        .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }

  protected byte[] loadResource(String fileName) throws IOException {
    return ResourceLoader.loadBytes(getClass(), fileName);
  }

  protected String loadResourceAsString(String fileName) throws IOException {
    return ResourceLoader.loadString(getClass(), fileName);
  }

  protected void writeJsonResponse(String response, String fileName) throws IOException {
    ResponseWriter.writeJson(objectMapper, response, fileName);
  }

  protected void writeJsonResponse(Object response, String fileName) throws IOException {
    ResponseWriter.writeJson(objectMapper, response, fileName);
  }

  protected void writeResponse(String response, String fileName) throws IOException {
    ResponseWriter.write(response, fileName);
  }
}
