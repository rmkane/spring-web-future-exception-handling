package com.example.web.model.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "AuthorType",
    propOrder = {"name", "birthYear", "deathYear", "nationality"})
@Schema(name = "Author", description = "Author biographical metadata")
public class AuthorType {
  @XmlElement(name = "Name", namespace = "http://example.com/book", required = true)
  private String name;

  @XmlElement(name = "BirthYear", namespace = "http://example.com/book")
  private Integer birthYear;

  @XmlElement(name = "DeathYear", namespace = "http://example.com/book")
  private Integer deathYear;

  @XmlElement(name = "Nationality", namespace = "http://example.com/book")
  private String nationality;
}
