package com.vit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI bookManagementAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GST Billing API")
                        .description("API documentation for GST Billing & Filing System")
                        .version("1.0.0"));
    }
}