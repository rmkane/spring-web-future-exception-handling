package com.example.integration;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/** Builder for {@link RestRequest} instances. */
public final class RestRequestBuilder {
  private String baseUrl;
  private String endpoint;
  private HttpMethod method = HttpMethod.GET;
  private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
  private final Map<String, String> queryParams = new LinkedHashMap<>();
  private final Map<String, Object> pathVariables = new LinkedHashMap<>();
  @Nullable private byte[] body;

  public static RestRequestBuilder create(String baseUrl) {
    RestRequestBuilder b = new RestRequestBuilder();
    b.baseUrl = Objects.requireNonNull(baseUrl, "baseUrl");
    return b;
  }

  public static RestRequestBuilder create(String baseUrl, String endpoint) {
    RestRequestBuilder b = create(baseUrl);
    b.endpoint(endpoint);
    return b;
  }

  public RestRequestBuilder method(HttpMethod method) {
    this.method = Objects.requireNonNull(method, "method");
    return this;
  }

  public RestRequestBuilder headers(MultiValueMap<String, String> headers) {
    if (headers != null) {
      this.headers.addAll(headers);
    }
    return this;
  }

  public RestRequestBuilder addHeader(String name, String value) {
    if (name != null && value != null) {
      this.headers.add(name, value);
    }
    return this;
  }

  public RestRequestBuilder endpoint(String endpoint) {
    this.endpoint = Objects.requireNonNull(endpoint, "endpoint");
    return this;
  }

  public RestRequestBuilder pathVar(String name, Object value) {
    if (name != null && value != null) {
      this.pathVariables.put(name, value);
    }
    return this;
  }

  public RestRequestBuilder pathVars(Map<String, ?> variables) {
    if (variables != null) {
      variables.forEach(
          (k, v) -> {
            if (k != null && v != null) {
              this.pathVariables.put(k, v);
            }
          });
    }
    return this;
  }

  public RestRequestBuilder queryParam(String name, String value) {
    if (name != null && value != null) {
      this.queryParams.put(name, value);
    }
    return this;
  }

  public RestRequestBuilder queryParams(Map<String, String> params) {
    if (params != null) {
      this.queryParams.putAll(params);
    }
    return this;
  }

  public RestRequestBuilder body(byte[] body) {
    this.body = body;
    return this;
  }

  public RestRequestBuilder body(String body) {
    this.body = body == null ? null : body.getBytes(StandardCharsets.UTF_8);
    return this;
  }

  public RestRequest build() {
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
    if (endpoint != null && !endpoint.isEmpty()) {
      uriBuilder.path(endpoint);
    }
    queryParams.forEach(uriBuilder::queryParam);

    URI uri =
        pathVariables.isEmpty()
            ? uriBuilder.build().encode().toUri()
            : uriBuilder.buildAndExpand(pathVariables).encode().toUri();
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.addAll(this.headers);
    return new RestRequest(uri, method, httpHeaders, body);
  }
}
