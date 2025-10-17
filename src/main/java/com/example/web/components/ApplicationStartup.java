package com.example.web.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

  @Value("${server.port:8080}")
  private int port;

  @Value("${server.address:}")
  private String serverAddress;

  @Value("${server.ssl.enabled:false}")
  private boolean sslEnabled;

  /**
   * This event is executed as late as conceivably possible to indicate that the application is
   * ready to service requests.
   */
  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    String url = getApplicationUrl(serverAddress, port, sslEnabled);
    System.out.printf("%n%nApplication running on: %s%n%n", url);
  }

  private String getApplicationUrl(String serverAddress, int port, boolean sslEnabled) {
    String addressForUrl = getAddressForUrl(serverAddress);
    String protocol = sslEnabled ? "https" : "http";
    return String.format("%s://%s:%d", protocol, addressForUrl, port);
  }

  private String getAddressForUrl(String serverAddress) {
    if (!StringUtils.hasText(serverAddress)
        || "0.0.0.0".equals(serverAddress)
        || "::".equals(serverAddress)) {
      return "localhost";
    }
    return serverAddress;
  }
}
