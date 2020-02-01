package com.zhxh.utils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhxh.admin.domain.SystemUser;
import com.zhxh.admin.logic.SystemUserLogic;
import com.zhxh.backSservice.ServiceManager;
import com.zhxh.data.FieldsMapInitiator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Modifier;

@Component
public class GlobalConstants implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static String shellExecuteName;

    public static SystemUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        String userCode = authentication.getPrincipal().toString();
        SystemUserLogic systemUserLogic = applicationContext.getBean(SystemUserLogic.class);
        return systemUserLogic.getByCode(userCode);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static String getShellExecuteName() {
        return shellExecuteName;
    }

    public static void setShellExecuteName(String shellExecuteName) {
        GlobalConstants.shellExecuteName = shellExecuteName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GlobalConstants.applicationContext = applicationContext;
        shellExecuteName = GlobalConstants.applicationContext.getEnvironment().getProperty("com.zhxh.shellExecuteName");

        FieldsMapInitiator.initFieldsMap();

//        ServiceManager serviceManager = applicationContext.getBean(ServiceManager.class);
//        serviceManager.start();
    }

    public static Gson getGson(String... ignoredFields) {
        return getGson(true, true, ignoredFields);
    }

    public static Gson getGson(boolean ignoreJsonManagedReference, boolean ignoreJsonBackReference, String... ignoredFields) {
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        for (String field : ignoredFields) {
                            if (field.equalsIgnoreCase(f.getName())) {
                                return true;
                            }
                        }

                        if (ignoreJsonManagedReference && f.getAnnotation(JsonManagedReference.class) != null) {
                            return true;
                        }
                        if (ignoreJsonBackReference && f.getAnnotation(JsonBackReference.class) != null) {
                            return true;
                        }

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

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }
}
