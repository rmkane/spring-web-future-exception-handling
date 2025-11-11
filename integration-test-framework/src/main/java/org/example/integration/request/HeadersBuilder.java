package org.example.integration.request;

import org.example.integration.util.MapUtils;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/** Simple builder for HTTP headers backed by a {@link MultiValueMap}. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HeadersBuilder {
  @NonNull private final LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

  public static HeadersBuilder create() {
    return new HeadersBuilder();
  }

  public HeadersBuilder addHeader(String name, String value) {
    MapUtils.addIf(headers, name, value);
    return this;
  }

  public HeadersBuilder addHeaderContentTypeXml() {
    return addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
  }

  public HeadersBuilder addHeaderContentTypeJson() {
    return addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
  }

  public HeadersBuilder addAll(String name, Collection<String> values) {
    MapUtils.addAllIf(headers, name, values);
    return this;
  }

  public HeadersBuilder put(String name, List<String> values) {
    MapUtils.putIf(headers, name, values);
    return this;
  }

  /** Return a copy of the built headers map. */
  @NonNull
  public MultiValueMap<String, String> build() {
    return new LinkedMultiValueMap<>(headers);
  }
}
