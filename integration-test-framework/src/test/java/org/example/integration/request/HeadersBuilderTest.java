package org.example.integration.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

class HeadersBuilderTest {

  @Test
  void testCreate() {
    HeadersBuilder builder = HeadersBuilder.create();
    assertNotNull(builder);
  }

  @Test
  void testAddHeader() {
    MultiValueMap<String, String> headers =
        HeadersBuilder.create().addHeader("X-Custom-Header", "value").build();

    assertEquals("value", headers.getFirst("X-Custom-Header"));
  }

  @Test
  void testAddHeaderContentTypeJson() {
    MultiValueMap<String, String> headers = HeadersBuilder.create().addHeaderContentTypeJson().build();

    assertEquals(MediaType.APPLICATION_JSON_VALUE, headers.getFirst(HttpHeaders.CONTENT_TYPE));
  }

  @Test
  void testAddHeaderContentTypeXml() {
    MultiValueMap<String, String> headers = HeadersBuilder.create().addHeaderContentTypeXml().build();

    assertEquals(MediaType.APPLICATION_XML_VALUE, headers.getFirst(HttpHeaders.CONTENT_TYPE));
  }

  @Test
  void testAddAll() {
    List<String> values = Arrays.asList("value1", "value2", "value3");
    MultiValueMap<String, String> headers =
        HeadersBuilder.create().addAll("X-Multi-Header", values).build();

    assertEquals(values, headers.get("X-Multi-Header"));
  }

  @Test
  void testPut() {
    List<String> values = Arrays.asList("value1", "value2");
    MultiValueMap<String, String> headers = HeadersBuilder.create().put("X-Header", values).build();

    assertEquals(values, headers.get("X-Header"));
  }

  @Test
  void testMultipleHeaders() {
    MultiValueMap<String, String> headers =
        HeadersBuilder.create()
            .addHeader("Header1", "value1")
            .addHeader("Header2", "value2")
            .addHeader("Header3", "value3")
            .build();

    assertEquals("value1", headers.getFirst("Header1"));
    assertEquals("value2", headers.getFirst("Header2"));
    assertEquals("value3", headers.getFirst("Header3"));
  }

  @Test
  void testNullValuesIgnored() {
    MultiValueMap<String, String> headers =
        HeadersBuilder.create().addHeader("Valid", "value").build();

    // Null key/value should be ignored, so only valid header should exist
    assertEquals("value", headers.getFirst("Valid"));
    assertEquals(1, headers.size());
  }

  @Test
  void testBuildReturnsCopy() {
    HeadersBuilder builder = HeadersBuilder.create().addHeader("Header1", "value1");
    MultiValueMap<String, String> headers1 = builder.build();
    MultiValueMap<String, String> headers2 = builder.build();

    // Should be different instances
    assertNotNull(headers1);
    assertNotNull(headers2);
    // But same content
    assertEquals("value1", headers1.getFirst("Header1"));
    assertEquals("value1", headers2.getFirst("Header1"));
  }

  @Test
  void testChaining() {
    MultiValueMap<String, String> headers =
        HeadersBuilder.create()
            .addHeader("Header1", "value1")
            .addHeaderContentTypeJson()
            .addHeader("Header2", "value2")
            .build();

    assertEquals("value1", headers.getFirst("Header1"));
    assertEquals(MediaType.APPLICATION_JSON_VALUE, headers.getFirst(HttpHeaders.CONTENT_TYPE));
    assertEquals("value2", headers.getFirst("Header2"));
  }
}

