package com.zhxh.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhxh.imms.utils.converter.JacksonDateDeserializer;
import com.zhxh.imms.utils.converter.JacksonDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class DateConverterConfig {
    @Bean
    public Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean() {
        Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        jackson2ObjectMapperFactoryBean.setDeserializers(
                new JacksonDateDeserializer(LocalDate.class),
                new JacksonDateDeserializer(LocalDateTime.class),
                new JacksonDateDeserializer(LocalTime.class));

        jackson2ObjectMapperFactoryBean.setSerializers(
                new JacksonDateTimeSerializer(LocalTime.class),
                new JacksonDateTimeSerializer(LocalDate.class),
                new JacksonDateTimeSerializer(LocalDateTime.class)
        );

        return jackson2ObjectMapperFactoryBean;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(@Autowired ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return mappingJackson2HttpMessageConverter;
    }
}
