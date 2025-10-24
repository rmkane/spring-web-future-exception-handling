package com.example.web.model;

import static org.junit.jupiter.api.Assertions.*;

import com.example.web.model.book.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BookParsingTest {
  private static ObjectMapper mapper;

  @BeforeAll
  static void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
  }

  @Test
  void unmarshals_book_xml_to_pojo() throws Exception {
    try (InputStream in = getClass().getResourceAsStream("/books/don-quixote.xml")) {
      assertNotNull(in, "XML resource not found");

      Book book = unmarshal(in, Book.class);

      assertNotNull(book);
      assertEquals("Don Quixote", book.getTitle());
      assertNotNull(book.getAuthor());
      assertEquals("Miguel de Cervantes Saavedra", book.getAuthor().getName());
      assertEquals("English", book.getLanguage());
      assertEquals("bk-don-quixote", book.getId());
      assertEquals("1.0", book.getVersion());
      assertNotNull(book.getChapters());
      assertEquals(4, book.getChapters().size());
    }
  }

  @Test
  void parsed_book_matches_expected_json() throws Exception {
    try (InputStream xml = getClass().getResourceAsStream("/books/don-quixote.xml");
        InputStream json = getClass().getResourceAsStream("/books/don-quixote.json")) {
      assertNotNull(xml, "XML resource not found");
      assertNotNull(json, "JSON resource not found");

      Book book = unmarshal(xml, Book.class);
      Book expected = mapper.readValue(json, Book.class);

      assertEquals(expected, book);
    }
  }

  @SuppressWarnings("unchecked")
  private static <E> E unmarshal(InputStream in, Class<E> clazz) throws Exception {
    JAXBContext context = JAXBContext.newInstance(clazz);
    Unmarshaller unmarshaller = context.createUnmarshaller();
    return (E) unmarshaller.unmarshal(in);
  }
}
