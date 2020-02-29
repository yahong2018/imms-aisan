package com.zhxh.imms.web;

import com.google.common.net.HttpHeaders;
import com.zhxh.imms.data.BusinessException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExtJsResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!"POST".equals(request.getMethod().toString().toUpperCase())) {
            return body;
        }
        if (!selectedContentType.equals(MediaType.APPLICATION_JSON) /*&& !MediaType.APPLICATION_JSON_UTF8.equals(selectedContentType)*/) {
            return body;
        }
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "text/html");

        Map<String, Object> result = new HashMap<>();
        if (body instanceof BusinessException) {
            result.put("success", false);
            result.put("data", ((BusinessException) body).getMessage());
        } else {
            result.put("success", true);
            result.put("data", body);
        }

        return result;
    }
}
