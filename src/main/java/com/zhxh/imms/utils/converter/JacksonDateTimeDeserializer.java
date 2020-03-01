package com.zhxh.imms.utils.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class JacksonDateTimeDeserializer<T> extends JsonDeserializer<T> {
    private DateTimeDeserializer<T> innerDeserializer;

    public JacksonDateTimeDeserializer(Class<T> type) {
        this.innerDeserializer = new DateTimeDeserializer<T>(type){};
    }

    @Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return innerDeserializer.convert(p.getText());
    }

    @Override
    public Class<T> handledType() {
        return innerDeserializer.getType();
    }
}

