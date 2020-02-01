package com.zhxh.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.zhxh.admin.domain.SystemUser;
import com.zhxh.admin.logic.SystemUserLogic;
import com.zhxh.utils.GlobalConstants;
import com.zhxh.utils.Logger;
import com.zhxh.web.ApiCallResult;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            LoginAccount loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginAccount.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SystemUser jwtUser = (SystemUser) authResult.getPrincipal();
        Logger.info("用户："+jwtUser.getUserCode()+"已成功登录");
        jwtUser.setLastLoginTime(LocalDateTime.now());
        SystemUserLogic userLogic = GlobalConstants.getBean(SystemUserLogic.class);
        userLogic.update(jwtUser);
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), false);
        // response.setHeader("Authorization", JwtTokenUtils.TOKEN_PREFIX + token);
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        String str = writeLoginResult(jwtUser, token);
        response.getWriter().write(str);
    }

    private String writeLoginResult(SystemUser jwtUser, String token) {
        ApiCallResult apiCallResult = new ApiCallResult();
        AuthToken authToken = new AuthToken();
        authToken.setAccess_token(token);
        authToken.setToken_type(JwtTokenUtils.TOKEN_PREFIX);
        AuthTokenProfile profile = new AuthTokenProfile();
        authToken.setProfile(profile);
        profile.setAuth_time(LocalDateTime.now());
        profile.setExpires_at((JwtTokenUtils.getTokenBody(token).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
        profile.setSid(jwtUser.getUserCode());
        profile.setName(jwtUser.getDisplayName());
        apiCallResult.setSuccess(true);
        apiCallResult.setData(authToken);
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))).setDateFormat("yyyy/MM/dd HH:mm:ss").create();
        return gson.toJson(apiCallResult);
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ApiCallResult apiCallResult = new ApiCallResult();
        apiCallResult.setMessage("authentication failed, reason: " + failed.getMessage());
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write(new Gson().toJson(apiCallResult));
    }
}

