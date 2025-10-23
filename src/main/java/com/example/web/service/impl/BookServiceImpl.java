package com.example.web.service.impl;

import com.example.web.mapper.SimpleBookMapper;
import com.example.web.model.book.Book;
import com.example.web.model.book.SimpleBook;
import com.example.web.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final SimpleBookMapper simpleBookMapper;
  private final ObjectMapper objectMapper;

  @Override
  public SimpleBook getExampleSimpleBook() {
    try (InputStream in = new ClassPathResource("books/don-quixote.json").getInputStream()) {
      Book book = objectMapper.readValue(in, Book.class);
      return simpleBookMapper.toSimple(book);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read example book JSON", e);
    }
  }
}
