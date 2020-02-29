package com.zhxh.imms.utils;

import java.time.*;
import java.util.Date;

public class ZhxhUtils {
    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Long localDateTime2Stamp(Object dateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt;
        if (dateTime instanceof LocalDateTime) {
            zdt = ((LocalDateTime) dateTime).atZone(zoneId);
        } else {
            zdt = ((LocalDate) dateTime).atStartOfDay(zoneId);
        }
        return zdt.toInstant().toEpochMilli();
    }
}
