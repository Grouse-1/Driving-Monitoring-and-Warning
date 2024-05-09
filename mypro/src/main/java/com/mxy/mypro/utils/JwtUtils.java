package com.mxy.mypro.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static long expire = 604800;

    private static String secret = "abcdfghiabcdfghiabcdfghiabcdfghi";

    public static String createToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + 1000*expire);
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    public static Claims getClaimsByToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
