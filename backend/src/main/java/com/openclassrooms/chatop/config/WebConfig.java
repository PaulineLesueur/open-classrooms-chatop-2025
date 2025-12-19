package com.openclassrooms.chatop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Registers a resource handler for serving uploaded files.
     *
     * <p>
     * Maps all requests starting with {@code /uploads/} to the local
     * {@code uploads/} directory located at the root of the backend project.
     * </p>
     *
     * <p>
     * Example:
     * </p>
     * <ul>
     *   <li>Request: {@code /uploads/image.jpg}</li>
     *   <li>File served from: {@code uploads/image.jpg}</li>
     * </ul>
     *
     * @param registry the resource handler registry used to configure
     *                 static resource mappings
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}