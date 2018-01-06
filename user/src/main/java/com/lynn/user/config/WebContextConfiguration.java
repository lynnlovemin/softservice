package com.lynn.user.config;

import com.lynn.user.params.SnakeToCamelModelAttributeMethodProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebContextConfiguration extends WebMvcConfigurationSupport {
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(processor());
    }

    @Bean
    protected SnakeToCamelModelAttributeMethodProcessor processor() {
        return new SnakeToCamelModelAttributeMethodProcessor(true);
    }
}
