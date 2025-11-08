package com.api.stock_management.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Lê as propriedades do seu application.properties
    @Value("${file.upload-dir.produtos}")
    private String produtosUploadDir;

    @Value("${file.upload-dir.sellers}")
    private String sellersUploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapeia a URL pública para a pasta física dos produtos
        String produtosPath = Paths.get(produtosUploadDir).toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/files/produtos/**")
                .addResourceLocations(produtosPath);

        // Mapeia a URL pública para a pasta física dos sellers
        String sellersPath = Paths.get(sellersUploadDir).toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/files/sellers/**")
                .addResourceLocations(sellersPath);
    }
}