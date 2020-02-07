package com.zhxh.imms.si.kocheer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.*;
import com.zhxh.imms.utils.ZhxhUtils;

import java.time.LocalDateTime;
import java.util.Date;

public class GsonCreator {
    public static Gson getGson(FieldNamingPolicy policy) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> {
            String str = json.getAsString().replace("/Date(", "").replace(")/", "");
            Date date = new Date(Long.parseLong(str));
            return ZhxhUtils.date2LocalDateTime(date);
        });
        builder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> {
            Long result = ZhxhUtils.localDateTime2Stamp(src);
            return new JsonPrimitive("/Date(" + result.toString() + ")/");
        });

        Gson gson = builder.setFieldNamingPolicy(policy)
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        if (f.getAnnotation(JsonIgnore.class) != null) {
                            return true;
                        }
                        return false;
                    }
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();
        return gson;
    }

    public static Gson getUpperCamelGson(){
        return getGson(FieldNamingPolicy.UPPER_CAMEL_CASE);
    }
}
