package com.zhxh.imms.utils.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter extends BaseDateConverter<LocalDateTime> {
    @Override
    protected LocalDateTime doConvert(SimpleDateFormat dateFormat, String source) throws ParseException {
        String pattern = dateFormat.toPattern();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(source,formatter);
    }
}
