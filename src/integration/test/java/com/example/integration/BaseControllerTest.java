package com.example.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.integration.request.HeadersBuilder;
import com.example.integration.request.RestFetcher;
import com.example.integration.request.RestRequestBuilder;
import com.example.integration.util.ResourceLoader;
import com.example.integration.util.ResponseWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class BaseControllerTest {
  private static final int DEFAULT_PORT = 8080;
  private static final String PROTOCOL_HTTP = "http";
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
    return String.format("%s://localhost:%d", getProtocol(), getPort());
  }

  /** Returns the protocol (http or https). Can be overridden by subclasses. */
  protected String getProtocol() {
    return PROTOCOL_HTTP;
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

  /** Convenience method for GET requests. */
  protected RestRequestBuilder get(String endpoint) {
    return request(endpoint).method(HttpMethod.GET);
  }

  /** Convenience method for POST requests. */
  protected RestRequestBuilder post(String endpoint) {
    return request(endpoint).method(HttpMethod.POST);
  }

  /** Convenience method for PUT requests. */
  protected RestRequestBuilder put(String endpoint) {
    return request(endpoint).method(HttpMethod.PUT);
  }

  /** Convenience method for DELETE requests. */
  protected RestRequestBuilder delete(String endpoint) {
    return request(endpoint).method(HttpMethod.DELETE);
  }

  /**
   * Parses a JSON response body into a Map. Useful for testing JSON responses.
   *
   * @param response The ResponseEntity containing the JSON response
   * @return A Map representation of the JSON body
   * @throws IOException if the JSON cannot be parsed
   */
  protected Map<String, Object> parseJsonResponse(ResponseEntity<String> response)
      throws IOException {
    return objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
  }

  /**
   * Converts an object to a JSON string. Useful for creating request bodies.
   *
   * @param object The object to convert to JSON
   * @return A JSON string representation of the object
   * @throws JsonProcessingException if the object cannot be serialized
   */
  protected String toJson(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  /**
   * Asserts that the response has the expected status code.
   *
   * @param response The response to check
   * @param expectedStatus The expected HTTP status
   * @throws AssertionError if the status codes don't match
   */
  protected void assertStatus(ResponseEntity<?> response, HttpStatus expectedStatus) {
    assertEquals(expectedStatus, response.getStatusCode(), "Response status code mismatch");
  }

  /**
   * Asserts that the response has status 200 OK.
   *
   * @param response The response to check
   * @throws AssertionError if the status is not OK
   */
  protected void assertOk(ResponseEntity<?> response) {
    assertStatus(response, HttpStatus.OK);
  }

  /**
   * Asserts that a JSON response contains a specific value at a given path.
   *
   * @param response The JSON response
   * @param jsonPath The JSON path (e.g., "query.term")
   * @param expectedValue The expected value
   * @throws IOException if the JSON cannot be parsed
   * @throws AssertionError if the value doesn't match
   */
  protected void assertJsonPath(
      ResponseEntity<String> response, String jsonPath, Object expectedValue) throws IOException {
    Map<String, Object> json = parseJsonResponse(response);
    Object actualValue = getJsonPathValue(json, jsonPath);
    org.junit.jupiter.api.Assertions.assertEquals(
        expectedValue, actualValue, "JSON path '" + jsonPath + "' mismatch");
  }

  private Object getJsonPathValue(Map<String, Object> json, String jsonPath) {
    String[] parts = jsonPath.split("\\.");
    Object current = json;
    for (String part : parts) {
      if (current instanceof Map) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) current;
        current = map.get(part);
      } else {
        return null;
      }
    }
    return current;
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
      try {
        return Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
            "Invalid port value in environment variable '" + variableName + "': " + value, e);
      }
    }
    return defaultValue;
  }

  private static int getSystemProperty(String propertyName, int defaultValue) {
    String value = System.getProperty(propertyName);
    if (value != null && !value.isEmpty()) {
      try {
        return Integer.parseInt(value);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
            "Invalid port value in system property '" + propertyName + "': " + value, e);
      }
    }
    return defaultValue;
  }
}
