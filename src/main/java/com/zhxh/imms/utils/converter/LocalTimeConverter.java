package com.zhxh.imms.utils.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeConverter extends BaseDateConverter<LocalTime> {
    @Override
    protected LocalTime doConvert(SimpleDateFormat dateFormat, String source) throws ParseException {
        String pattern = dateFormat.toPattern();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalTime.parse(source,formatter);
    }
}
