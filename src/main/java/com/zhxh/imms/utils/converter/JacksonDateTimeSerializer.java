package com.zhxh.imms.utils.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.zhxh.imms.utils.ZhxhUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JacksonDateTimeSerializer<T> extends JsonSerializer<T> {
    private Class<T> type;

    public JacksonDateTimeSerializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (type == LocalTime.class) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            gen.writeString(((LocalTime) value).format(formatter));
        } else {
            gen.writeNumber(ZhxhUtils.localDateTime2Stamp(value));
        }
    }

    @Override
    public Class<T> handledType() {
        return this.type;
    }
}
