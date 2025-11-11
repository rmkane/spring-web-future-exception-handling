package com.example.integration.request;

import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
    if (name != null && value != null) {
      headers.add(name, value);
    }
    return this;
  }

  public HeadersBuilder addAll(String name, Collection<String> values) {
    if (name != null && values != null) {
      for (String value : values) {
        if (value != null) {
          headers.add(name, value);
        }
      }
    }
    return this;
  }

  public HeadersBuilder put(String name, List<String> values) {
    if (name != null && values != null) {
      headers.put(name, List.copyOf(values));
    }
    return this;
  }

  /** Return a copy of the built headers map. */
  @NonNull
  public MultiValueMap<String, String> build() {
    return new LinkedMultiValueMap<>(headers);
  }
}
