package com.example.camunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import java.util.Collections;

@EnableProcessApplication
@SpringBootApplication
public class Application {

  @Autowired
  private RuntimeService runtimeService;

  @EventListener
  private void processPostDeploy(PostDeployEvent event) {
    runtimeService.startProcessInstanceByKey("getting-started-process");
  }

  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(final String[] args) throws Exception {
    String port = System.getenv("PORT");
    if (port == null) {
      port = "8080";
      logger.warn("$PORT environment variable not set, defaulting to 8080");
    }
    SpringApplication app = new SpringApplication(Application.class);
    app.setDefaultProperties(Collections.singletonMap("server.port", port));

    // Start the Spring Boot application.
    app.run(args);
    logger.info(
            "Hello from Cloud Run! The container started successfully and is listening for HTTP requests on " + port);
  }
}