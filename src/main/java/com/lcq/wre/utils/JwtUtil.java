package com.lcq.wre.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    private static final String secret = "NP0B264lx8869J&lLR";

    private static final Long expiration = 6 * 60 * 60 * 1000L;


    public static String generateToken(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, secret) // 签发算法，秘钥
                .setClaims(claims) // body数据
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration))// 有效时间
                .compact();

    }

    public static Map<String, Object> checkToken(String token) {
        try {
            Jwt parse = Jwts.parser().setSigningKey(secret).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Long parseJwtToken(String token) {
        return Long.parseLong(
                String.valueOf(
                        Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("userId")));
    }

    public static void main(String[] args) {
        Long id = 123456L;
        String token = JwtUtil.generateToken(id);
        System.out.println("token: " + token);
        System.out.println(JwtUtil.parseJwtToken(token));
    }
}