package com.cmw.kd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WorkLogApplication extends SpringBootServletInitializer {
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
    return springApplicationBuilder.sources(WorkLogApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(WorkLogApplication.class, args);
  }
}
