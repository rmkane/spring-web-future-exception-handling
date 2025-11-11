package com.example.web.api.v1.book;

import com.example.web.model.book.Book;
import com.example.web.model.book.SimpleBook;
import com.example.web.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/books")
@Tag(name = "Books", description = "Endpoints for submitting and managing books")
@Slf4j
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @PostMapping(
      consumes = MediaType.APPLICATION_XML_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Submit a Book as XML",
      description =
          "Accepts a JAXB-annotated Book XML and returns the parsed representation as JSON.",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = MediaType.APPLICATION_XML_VALUE,
                      schema = @Schema(implementation = Book.class))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Book accepted",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Book.class)))
      })
  public ResponseEntity<Book> create(@RequestBody Book book) {
    return ResponseEntity.ok(book);
  }

  @org.springframework.web.bind.annotation.GetMapping(
      path = "/example",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Get example simple book",
      description = "Loads example book JSON from classpath and returns a SimpleBook view.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content =
                  @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = SimpleBook.class))))
  public ResponseEntity<SimpleBook> example() {
    return ResponseEntity.ok(bookService.getExampleSimpleBook());
  }
}
