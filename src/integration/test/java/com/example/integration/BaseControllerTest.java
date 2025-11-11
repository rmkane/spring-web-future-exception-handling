package com.example.integration;

import com.example.integration.request.HeadersBuilder;
import com.example.integration.request.RestFetcher;
import com.example.integration.request.RestRequestBuilder;
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
  private static final int DEFAULT_PORT = 8080;
  private static final String PORT_PROPERTY = "test.server.port";
  private static final String PORT_ENV_VAR = "TEST_SERVER_PORT";

  private static final int PORT = resolvePort();

  protected static ObjectMapper objectMapper;
  protected static RestFetcher restFetcher;

  @BeforeAll
  static void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    // Reuse a single RestTemplate instance across all tests (thread-safe)
    restFetcher = new RestFetcher(new RestTemplate());
  }

  /** Returns the base URL for the test server. Can be overridden by subclasses. */
  protected String getBaseUrl() {
    return String.format("http://localhost:%d", getPort());
  }

  /** Returns the configured port. Can be overridden by subclasses. */
  protected int getPort() {
    return PORT;
  }

  /**
   * Convenience method to create a RestRequestBuilder with the base URL and endpoint.
   *
   * @param endpoint The API endpoint (e.g., "/api/v1/greeting")
   * @return A RestRequestBuilder configured with the base URL and endpoint
   */
  protected RestRequestBuilder request(String endpoint) {
    return RestRequestBuilder.create(getBaseUrl(), endpoint);
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

  private static int resolvePort() {
    // Check system property first
    int portProperty = getSystemProperty(PORT_PROPERTY, DEFAULT_PORT);
    if (portProperty != DEFAULT_PORT) {
      return portProperty;
    }

    // Check environment variable
    int portEnv = getEnvironmentVariable(PORT_ENV_VAR, DEFAULT_PORT);
    if (portEnv != DEFAULT_PORT) {
      return portEnv;
    }

    return DEFAULT_PORT;
  }

  private static int getEnvironmentVariable(String variableName, int defaultValue) {
    String value = System.getenv(variableName);
    if (value != null && !value.isEmpty()) {
      return Integer.parseInt(value);
    }
    return defaultValue;
  }

  private static int getSystemProperty(String propertyName, int defaultValue) {
    String value = System.getProperty(propertyName);
    if (value != null && !value.isEmpty()) {
      return Integer.parseInt(value);
    }
    return defaultValue;
  }
}
