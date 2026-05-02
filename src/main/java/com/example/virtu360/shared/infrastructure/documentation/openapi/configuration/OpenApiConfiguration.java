package com.example.virtu360.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

  @Bean
  public OpenAPI salesPlatformOpenAPI() {
    return new OpenAPI()
        .info(apiInfo())
        .externalDocs(externalDocs())
        .addSecurityItem(new SecurityRequirement());
  }

  private Info apiInfo() {
    return new Info()
        .title("VirtuPro360 Platform API")
        .description("VirtuPro360 Platform Application REST API Documentation.")
        .version("v1.0.0")
        .license(new License()
            .name("Apache 2.0")
            .url("https://springdoc.org"));
  }

  private ExternalDocumentation externalDocs() {
    return new ExternalDocumentation()
        .description("VirtuPro360 Platform Documentation")
        .url("https://example.com");
  }
}
