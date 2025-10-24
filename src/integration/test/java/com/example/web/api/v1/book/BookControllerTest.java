package com.example.web.api.v1.book;

import com.example.integration.HeadersBuilder;
import com.example.integration.RestFetcher;
import com.example.integration.RestRequest;
import com.example.integration.RestRequestBuilder;
import com.example.web.api.BaseControllerTest;
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
    ResponseEntity<Book> response = fetcher.exchange(request, Book.class);

    writeJsonResponse(response.getBody(), "book_create.json");
  }
}
