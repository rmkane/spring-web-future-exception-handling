package org.example.integration.request;

import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.entity.ContentType;
import org.example.integration.util.MapUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/** Simple builder for HTTP headers backed by a {@link MultiValueMap}. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestHeadersBuilder {
  @NonNull private final LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

  public static RequestHeadersBuilder create() {
    return new RequestHeadersBuilder();
  }

  public RequestHeadersBuilder addHeader(String name, String value) {
    MapUtils.addIf(headers, name, value);
    return this;
  }

  public RequestHeadersBuilder addHeaderContentType(String mediaType) {
    return addHeader(HttpHeaders.CONTENT_TYPE, mediaType);
  }

  public RequestHeadersBuilder addHeaderContentType(MediaType mediaType) {
    return addHeaderContentType(mediaType.toString());
  }

  public RequestHeadersBuilder addHeaderContentType(ContentType contentType) {
    return addHeaderContentType(contentType.toString());
  }

  public RequestHeadersBuilder addHeaderContentTypeXml() {
    return addHeaderContentType(MediaType.APPLICATION_XML_VALUE);
  }

  public RequestHeadersBuilder addHeaderContentTypeJson() {
    return addHeaderContentType(MediaType.APPLICATION_JSON_VALUE);
  }

  public RequestHeadersBuilder addAll(String name, Collection<String> values) {
    MapUtils.addAllIf(headers, name, values);
    return this;
  }

  public RequestHeadersBuilder put(String name, List<String> values) {
    MapUtils.putIf(headers, name, values);
    return this;
  }

  /** Return a copy of the built headers map. */
  @NonNull
  public HttpHeaders build() {
    return HttpHeaders.readOnlyHttpHeaders(headers);
  }
}
