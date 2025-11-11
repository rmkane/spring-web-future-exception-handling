package com.example.web.api.v1.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/v1/files")
@Tag(name = "Files", description = "Endpoints for file operations")
@Slf4j
@RequiredArgsConstructor
public class FileController {
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Operation(summary = "Upload a file", description = "Upload a file to the server")
  public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file)
      throws IOException {
    byte[] fileContent = file.getBytes();
    FileInfo fileInfo = getFileInfo(file);

    log.info("File uploaded successfully: {}", fileInfo);

    // Truncate content logging for MP3 and other binary files
    String contentLog = getContentForLogging(fileContent, fileInfo.fileExtension);
    log.info("File content: {}", contentLog);

    // Return different message for MP3 files
    if ("mp3".equalsIgnoreCase(fileInfo.fileExtension)) {
      return ResponseEntity.ok("MP3 file uploaded successfully");
    }
    return ResponseEntity.ok("File uploaded successfully");
  }

  private FileInfo getFileInfo(MultipartFile file) {
    String fileName = file.getOriginalFilename();
    String fileExtension = "";
    if (fileName != null) {
      int lastDotIndex = fileName.lastIndexOf(".");
      if (lastDotIndex >= 0 && lastDotIndex < fileName.length() - 1) {
        fileExtension = fileName.substring(lastDotIndex + 1);
      }
    }
    long fileSize = file.getSize();
    String fileType = file.getContentType();
    String filePath = fileName;
    return new FileInfo(fileName, fileExtension, fileSize, fileType, filePath);
  }

  private String getContentForLogging(byte[] fileContent, String fileExtension) {
    // Truncate binary files (MP3, images, etc.) to avoid logging large binary data
    if (isBinaryFile(fileExtension)) {
      int maxBytes = 100; // Show first 100 bytes
      if (fileContent.length <= maxBytes) {
        return String.format("[%d bytes - binary content]", fileContent.length);
      }
      return String.format(
          "[%d bytes - binary content, first %d bytes: %s...]",
          fileContent.length, maxBytes, bytesToHex(fileContent, maxBytes));
    }
    // For text files, convert to string but limit length
    String content = new String(fileContent, StandardCharsets.UTF_8);
    int maxLength = 500;
    if (content.length() <= maxLength) {
      return content;
    }
    return content.substring(0, maxLength) + "... [truncated]";
  }

  private boolean isBinaryFile(String fileExtension) {
    if (fileExtension == null || fileExtension.isEmpty()) {
      return false;
    }
    String ext = fileExtension.toLowerCase();
    return ext.equals("mp3")
        || ext.equals("mp4")
        || ext.equals("jpg")
        || ext.equals("jpeg")
        || ext.equals("png")
        || ext.equals("gif")
        || ext.equals("pdf")
        || ext.equals("zip")
        || ext.equals("exe")
        || ext.equals("bin");
  }

  private String bytesToHex(byte[] bytes, int maxBytes) {
    StringBuilder hex = new StringBuilder();
    int length = Math.min(bytes.length, maxBytes);
    for (int i = 0; i < length; i++) {
      hex.append(String.format("%02x", bytes[i]));
      if (i < length - 1) {
        hex.append(" ");
      }
    }
    return hex.toString();
  }

  record FileInfo(
      String fileName, String fileExtension, long fileSize, String fileType, String filePath) {
    public FileInfo(
        String fileName, String fileExtension, long fileSize, String fileType, String filePath) {
      this.fileName = fileName;
      this.fileExtension = fileExtension;
      this.fileSize = fileSize;
      this.fileType = fileType;
      this.filePath = filePath;
    }
  }
}
