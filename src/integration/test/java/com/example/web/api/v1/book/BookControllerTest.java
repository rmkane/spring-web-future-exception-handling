package com.example.web.api.v1.book;

import com.example.integration.BaseControllerTest;
import com.example.integration.request.HeadersBuilder;
import com.example.web.model.book.Book;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@Tag("integration")
final class BookControllerTest extends BaseControllerTest {
  @Test
  void testCreateBook() throws Exception {
    var request =
        request("/api/v1/books")
            .method(HttpMethod.POST)
            .headers(
                HeadersBuilder.create()
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .build())
            .body(loadResource("/books/don-quixote.xml"))
            .build();
    var response = restFetcher.fetch(request, Book.class);

    writeJsonResponse(response.getBody(), "book_create.json");
  }
}
