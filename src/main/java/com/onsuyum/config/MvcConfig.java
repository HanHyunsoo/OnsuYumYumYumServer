package com.onsuyum.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}")
    String[] corsOrigins;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                                                               .serializersByType(
                                                                       Map.of(
                                                                               LocalDateTime.class,
                                                                               new LocalDateTimeSerializer(
                                                                                       dateTimeFormatter),
                                                                               LocalDate.class,
                                                                               new LocalDateSerializer(
                                                                                       dateFormatter),
                                                                               Date.class,
                                                                               new DateSerializer(
                                                                                       false,
                                                                                       new SimpleDateFormat(
                                                                                               "yyyy-MM-dd HH:mm:ss"))
                                                                       )
                                                               )
                                                               .deserializerByType(LocalDate.class,
                                                                       new LocalDateDeserializer(
                                                                               dateFormatter))
                                                               .build();
        converters.add(0, new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}