package com.api.stock_management.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourceHandler = "/uploads/**";
        String resourceLocation = "file:./uploads/";

        registry.addResourceHandler(resourceHandler)
                .addResourceLocations(resourceLocation);
    }
}
