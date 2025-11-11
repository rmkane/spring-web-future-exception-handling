package com.example.web.api.v1.book;

import com.example.web.model.book.Book;
import org.example.integration.BaseControllerTest;
import org.example.integration.request.HeadersBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("integration")
final class BookControllerTest extends BaseControllerTest {
  @Test
  void testCreateBook() throws Exception {
    var request =
        post("/api/v1/books")
            .headers(HeadersBuilder.create().addHeaderContentTypeXml().build())
            .body(loadResource("/books/don-quixote.xml"))
            .build();
    var response = restFetcher.fetch(request, Book.class);

    writeJsonResponse(response.getBody(), "book_create.json");
  }
}
