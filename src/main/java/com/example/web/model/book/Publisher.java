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
    name = "Publisher",
    propOrder = {"name", "location", "year"})
@Schema(name = "Publisher", description = "Publisher details")
public class Publisher {
  @XmlElement(name = "Name", namespace = "http://example.com/book", required = true)
  private String name;

  @XmlElement(name = "Location", namespace = "http://example.com/book")
  private String location;

  @XmlElement(name = "Year", namespace = "http://example.com/book")
  private Integer year;
}
