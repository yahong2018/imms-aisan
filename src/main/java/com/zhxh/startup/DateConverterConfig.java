package com.zhxh.startup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhxh.imms.utils.converter.JacksonDateTimeDeserializer;
import com.zhxh.imms.utils.converter.JacksonDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Configuration
public class DateConverterConfig {
    @Bean
    public Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean() {
        Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
        jackson2ObjectMapperFactoryBean.setDeserializers(
                new JacksonDateTimeDeserializer<Date>(Date.class){},
                new JacksonDateTimeDeserializer<Timestamp>(Timestamp.class){},
                new JacksonDateTimeDeserializer<LocalDate>(LocalDate.class){},
                new JacksonDateTimeDeserializer<LocalDateTime>(LocalDateTime.class){},
                new JacksonDateTimeDeserializer<LocalTime>(LocalTime.class){});

        jackson2ObjectMapperFactoryBean.setSerializers(
                new JacksonDateTimeSerializer<LocalTime>(LocalTime.class){},
                new JacksonDateTimeSerializer<LocalDate>(LocalDate.class){},
                new JacksonDateTimeSerializer<LocalDateTime>(LocalDateTime.class){},
                new JacksonDateTimeSerializer<Date>(Date.class){},
                new JacksonDateTimeSerializer<Timestamp>(Timestamp.class){}
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
