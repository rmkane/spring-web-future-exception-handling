package com.example.web.api.v1.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.integration.BaseControllerTest;
import com.example.integration.request.RestRequest;
import java.io.IOException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("integration")
final class FileControllerTest extends BaseControllerTest {
  @Test
  void testUploadFile() throws IOException {
    String fileName = "test.txt";
    RestRequest request =
        request("/api/v1/files")
            .method(HttpMethod.POST)
            .file("file", loadResource("/files/" + fileName), fileName)
            .build();
    ResponseEntity<String> response = restFetcher.fetch(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("File uploaded successfully", response.getBody());
  }

  @Test
  void testUploadMusicFile() throws IOException {
    var fileName = "jazz-background-music-426859.mp3";
    var request =
        request("/api/v1/files")
            .method(HttpMethod.POST)
            .file("file", loadResource("/files/" + fileName), fileName)
            .build();
    var response = restFetcher.fetch(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("MP3 file uploaded successfully", response.getBody());
  }
}
