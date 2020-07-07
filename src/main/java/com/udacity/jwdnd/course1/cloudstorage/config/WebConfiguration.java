package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/notFound").setViewName("forward:/home");
        registry.addViewController("/internalError").setViewName("forward:/login");
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerNotFoundCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage[]{new ErrorPage(HttpStatus.NOT_FOUND, "/notFound")});
        };
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerInternalErrorCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage[]{new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/internalError")});
        };
    }

}