package com.example.integration;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/** Immutable HTTP request for integration tests. */
public final class RestRequest implements HttpRequest {
  private final URI uri;
  private final HttpMethod method;
  private final HttpHeaders headers;
  @Nullable private final byte[] body;

  RestRequest(URI uri, HttpMethod method, HttpHeaders headers, @Nullable byte[] body) {
    this.uri = uri;
    this.method = method;
    this.headers = HttpHeaders.readOnlyHttpHeaders(headers);
    this.body = body;
  }

  @NonNull
  @Override
  public HttpMethod getMethod() {
    return method;
  }

  public String getMethodValue() {
    return method.name();
  }

  @NonNull
  @Override
  public URI getURI() {
    return uri;
  }

  @NonNull
  @Override
  public HttpHeaders getHeaders() {
    return headers;
  }

  @NonNull
  @Override
  public Map<String, Object> getAttributes() {
    return Map.of();
  }

  /** Raw body bytes, or {@code null} if no body. */
  @Nullable
  public byte[] getBody() {
    return body;
  }

  public Optional<String> getBodyAsString(Charset charset) {
    return body == null ? Optional.empty() : Optional.of(new String(body, charset));
  }
}
