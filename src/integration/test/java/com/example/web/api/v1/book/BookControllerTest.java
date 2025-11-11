package com.example.web.api.v1.book;

import com.example.integration.BaseControllerTest;
import com.example.integration.request.HeadersBuilder;
import com.example.integration.request.RestFetcher;
import com.example.integration.request.RestRequest;
import com.example.integration.request.RestRequestBuilder;
import com.example.web.model.book.Book;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag("integration")
final class BookControllerTest extends BaseControllerTest {
  @Test
  void testCreateBook() throws Exception {
    RestFetcher fetcher = new RestFetcher();
    RestRequest request =
        RestRequestBuilder.create(BASE_URL, "/api/v1/books")
            .method(HttpMethod.POST)
            .headers(
                HeadersBuilder.create()
                    .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                    .build())
            .body(loadResource("/books/don-quixote.xml"))
            .build();
    ResponseEntity<Book> response = fetcher.fetch(request, Book.class);

    writeJsonResponse(response.getBody(), "book_create.json");
  }
}
