package com.example.web.api.v1.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.example.integration.IntegrationTestSuite;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Tag("integration")
final class FileControllerTest extends IntegrationTestSuite {
  @Test
  void testUploadFile() throws IOException {
    var fileName = "test.txt";
    var request =
        post("/api/v1/files").file("file", loadResource("/files/" + fileName), fileName).build();
    var response = fetch(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("File uploaded successfully", response.getBody());
  }

  @Test
  void testUploadMusicFile() throws IOException {
    var fileName = "jazz-background-music-426859.mp3";
    var request =
        post("/api/v1/files").file("file", loadResource("/files/" + fileName), fileName).build();
    var response = fetch(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("MP3 file uploaded successfully", response.getBody());
  }
}
