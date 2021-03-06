package com.zhxh.imms.authenticate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtils {
//    private final SystemUserLogic systemUserLogic;

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ZHXH_TOKEN_HEADER = "___ZHXH___" + JwtTokenUtils.TOKEN_HEADER;

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "echisan";

    // 过期时间是24小时
    private static final long EXPIRATION = 24 * 3600L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

//    public JwtTokenUtils(SystemUserLogic systemUserLogic) {
//        this.systemUserLogic = systemUserLogic;
//    }

    // 创建token
    public static String createToken(String username, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    // 是否已过期
    public static boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }

    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

//    public SystemUser getCurrentUser(){
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
//        String token = request.getHeader(TOKEN_HEADER).replace(TOKEN_PREFIX,"");
//        String userName = this.getUsername(token);
//        return this.systemUserLogic.getByCode(userName);
//    }
}

