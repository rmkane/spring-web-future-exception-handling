package com.example.web.api;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;

final class BookXmlValidationTest {

  private static Schema schema;

  @BeforeAll
  static void loadSchema() throws Exception {
    // book.xsd must be in src/test/resources
    URL xsdUrl = resource("/schemas/book.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    // Allow local file imports (e.g., xml.xsd) while keeping secure processing
    sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file");
    schema = sf.newSchema(xsdUrl);
  }

  @Test
  void xml_is_valid() {
    assertDoesNotThrow(() -> validate("/books/don-quixote.xml"));
  }

  @Test
  void xml_with_duplicate_chapter_numbers_is_invalid() {
    SAXParseException ex =
        assertThrows(SAXParseException.class, () -> validate("/books/don-quixote-dup-chapter.xml"));
    // Optional: assert message or location to be sure we hit the uniqueness rule
    assertTrue(ex.getMessage().toLowerCase().contains("unique"), ex.getMessage());
  }

  private static void validate(String xmlClasspathPath) throws Exception {
    URL xmlUrl = resource(xmlClasspathPath);
    Validator v = schema.newValidator();

    // Use StreamSource with systemId so XSD includes/imports & error locations work properly
    try (var in = xmlUrl.openStream()) {
      StreamSource src = new StreamSource(in);
      src.setSystemId(xmlUrl.toString());
      v.validate(src);
    }
  }

  private static URL resource(String classpathPath) {
    URL url = BookXmlValidationTest.class.getResource(classpathPath);
    if (url == null) throw new IllegalArgumentException("Missing resource: " + classpathPath);
    return url;
  }
}
