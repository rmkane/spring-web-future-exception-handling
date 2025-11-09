package com.example.web.api.v1.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.integration.RestFetcher;
import com.example.integration.RestRequest;
import com.example.integration.RestRequestBuilder;
import com.example.web.api.BaseControllerTest;
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
    RestFetcher fetcher = new RestFetcher();
    RestRequest request =
        RestRequestBuilder.create(BASE_URL, "/api/v1/files")
            .method(HttpMethod.POST)
            .file("test.txt", loadResource("/files/test.txt"))
            .build();
    ResponseEntity<String> response = fetcher.exchange(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("File uploaded successfully", response.getBody());
  }

  @Test
  void testUploadMusicFile() throws IOException {
    String fileName = "jazz-background-music-426859.mp3";
    RestFetcher fetcher = new RestFetcher();
    RestRequest request =
        RestRequestBuilder.create(BASE_URL, "/api/v1/files")
            .method(HttpMethod.POST)
            .file(fileName, loadResource("/files/" + fileName))
            .build();
    ResponseEntity<String> response = fetcher.exchange(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("MP3 file uploaded successfully", response.getBody());
  }
}
