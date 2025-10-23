package com.example.web.model;

import static org.junit.jupiter.api.Assertions.*;

import com.example.web.model.book.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class BookParsingTest {

  @Test
  void unmarshals_book_xml_to_pojo() throws Exception {
    try (InputStream in = getClass().getResourceAsStream("/books/don-quixote.xml")) {
      assertNotNull(in, "XML resource not found");

      JAXBContext context = JAXBContext.newInstance("com.example.web.model.book");
      Unmarshaller unmarshaller = context.createUnmarshaller();
      Book book = (Book) unmarshaller.unmarshal(in);

      assertNotNull(book);
      assertEquals("Don Quixote", book.getTitle());
      assertNotNull(book.getAuthor());
      assertEquals("Miguel de Cervantes Saavedra", book.getAuthor().getName());
      assertEquals("English", book.getLanguage());
      assertEquals("bk-don-quixote", book.getId());
      assertEquals("1.0", book.getVersion());
      assertNotNull(book.getChapters());
      assertNotNull(book.getChapters().getChapter());
      assertFalse(book.getChapters().getChapter().isEmpty());
    }
  }

  @Test
  void parsed_book_matches_expected_json() throws Exception {
    try (InputStream xml = getClass().getResourceAsStream("/books/don-quixote.xml");
        InputStream json = getClass().getResourceAsStream("/books/don-quixote.json")) {
      assertNotNull(xml, "XML resource not found");
      assertNotNull(json, "JSON resource not found");

      JAXBContext context = JAXBContext.newInstance("com.example.web.model.book");
      Unmarshaller unmarshaller = context.createUnmarshaller();
      Book book = (Book) unmarshaller.unmarshal(xml);

      ObjectMapper mapper = new ObjectMapper();
      Book expected = mapper.readValue(json, Book.class);

      assertEquals(expected, book);
    }
  }
}
