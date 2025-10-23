package com.example.web.mapper;

import com.example.web.model.book.Book;
import com.example.web.model.book.SimpleBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SimpleBookMapper {
  SimpleBookMapper INSTANCE = Mappers.getMapper(SimpleBookMapper.class);

  @Mapping(target = "authorName", source = "author.name")
  @Mapping(target = "publisherName", source = "publisher.name")
  SimpleBook toSimple(Book book);
}
