package com.zhxh.imms.utils.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.zhxh.imms.utils.ZhxhUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class JacksonDateDeserializer extends JsonDeserializer {
    private Class type;

    public JacksonDateDeserializer(Class type) {
        this.type = type;
    }

    private static String[] pattern =
            new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S",
                    "yyyy.MM.dd", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.S",
                    "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.S"};

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String originDate = p.getText();
        if (StringUtils.isNotEmpty(originDate)) {
            if (type == LocalDateTime.class || type == LocalDate.class) {
                try {
                    long longDate = Long.parseLong(originDate.trim());
                    if (type == LocalDateTime.class) {
                        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longDate), ZoneId.systemDefault());
                    } else if (type == LocalDate.class) {
                        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longDate), ZoneId.systemDefault()).toLocalDate();
                    }
                } catch (NumberFormatException e) {
                    try {
                        LocalDateTime localDateTime = ZhxhUtils.date2LocalDateTime(DateUtils.parseDate(originDate, JacksonDateDeserializer.pattern));
                        if (type == LocalDate.class) {
                            return localDateTime.toLocalDate();
                        }
                        return localDateTime;

                    } catch (ParseException pe) {
                        throw new IOException(String.format(
                                "'%s' can not convert to type 'java.util.Date',just support timestamp(type of long) and following date format(%s)",
                                originDate,
                                StringUtils.join(pattern, ",")));
                    }
                }
            } else {
                try {
                    return LocalTime.parse(originDate, DateTimeFormatter.ofPattern("HH:mm:ss"));
                } catch (DateTimeParseException e) {
                    return LocalTime.parse(originDate, DateTimeFormatter.ofPattern("HH:mm"));
                }
            }

        }
        return null;
    }

    @Override
    public Class handledType() {
        return type;
    }
}

