package com.zhxh.imms.utils.converter;

import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.utils.ZhxhUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeDeserializer<T> implements Converter<String, T> {
    private static String[] pattern =
            new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.S",
                    "yyyy.MM.dd", "yyyy.MM.dd HH:mm", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm:ss.S",
                    "yyyy/MM/dd", "yyyy/MM/dd HH:mm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss.S"};

    private Class<T> type;
    public Class<T> getType() {
        return type;
    }

    public DateTimeDeserializer(Class<T> type) {
        this.type = type;
    }

    @Override
    public T convert(String originDate) {
        if (StringUtils.isEmpty(originDate)) {
            return null;
        }

        return (T) doInternalConvert(originDate);
    }

    private Object doInternalConvert(String originDate) {
        if (type == LocalTime.class) {
            return getLocalTime(originDate);
        }

        try {
            long longDate = Long.parseLong(originDate.trim());
            Instant instant = Instant.ofEpochMilli(longDate);
            if (type == LocalDateTime.class) {
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            } else if (type == LocalDate.class) {
                return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            } else if (type == Date.class) {
                return Date.from(instant);
            }

            return Timestamp.from(instant);

        } catch (NumberFormatException e) {
            try {
                Date date = DateUtils.parseDate(originDate, pattern);
                if (type == Date.class) {
                    return date;
                }
                if (type == Timestamp.class) {
                    return Timestamp.from(date.toInstant());
                }
                LocalDateTime localDateTime = ZhxhUtils.date2LocalDateTime(date);
                if (type == LocalDate.class) {
                    return localDateTime.toLocalDate();
                }
                return localDateTime;

            } catch (ParseException pe) {
                throw new BusinessException(String.format(
                        "'%s' can not convert to type 'java.util.Date',just support timestamp(type of long) and following date format(%s)",
                        originDate,
                        StringUtils.join(pattern, ",")));
            }
        }
    }

    private Object getLocalTime(String originDate) {
        try {
            return LocalTime.parse(originDate, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            return LocalTime.parse(originDate, DateTimeFormatter.ofPattern("HH:mm"));
        }
    }


}
