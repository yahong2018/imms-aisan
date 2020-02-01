package com.zhxh.utils.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LocalDateConverter extends BaseDateConverter<LocalDate> {
    @Override
    protected LocalDate doConvert(SimpleDateFormat dateFormat, String source) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat.toPattern());
        return LocalDate.parse(source,formatter);
    }
}
