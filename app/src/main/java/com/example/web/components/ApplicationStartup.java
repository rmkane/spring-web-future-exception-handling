package com.example.web.components;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
  private final WebServerApplicationContext webServerApplicationContext;
  private final Environment environment;

  public ApplicationStartup(
      WebServerApplicationContext webServerApplicationContext, Environment environment) {
    this.webServerApplicationContext = webServerApplicationContext;
    this.environment = environment;
  }

  @Override
  public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
    int port = webServerApplicationContext.getWebServer().getPort();
    boolean sslEnabled = environment.getProperty("server.ssl.enabled", Boolean.class, false);
    String protocol = sslEnabled ? "https" : "http";
    String address = environment.getProperty("server.address", "localhost");
    String host = "0.0.0.0".equals(address) || "::".equals(address) ? "localhost" : address;
    String contextPath = environment.getProperty("server.servlet.context-path", "");
    String baseUrl = protocol + "://" + host + ":" + port + contextPath;
    System.out.printf("%n\t>> Application is running at: %s%n%n", baseUrl);
  }
}
