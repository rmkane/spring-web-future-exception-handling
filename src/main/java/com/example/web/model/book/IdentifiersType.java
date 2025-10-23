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
    name = "IdentifiersType",
    propOrder = {"isbn"})
@Schema(name = "Identifiers", description = "Identifier set such as ISBN")
public class IdentifiersType {
  @XmlElement(name = "ISBN", namespace = "http://example.com/book", required = true)
  private String isbn;
}
