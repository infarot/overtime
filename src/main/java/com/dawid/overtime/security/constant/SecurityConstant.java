package com.dawid.overtime.security.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstant {

    public static String SECRET;

    public static long EXPIRATION_TIME;

    public static String TOKEN_PREFIX;

    public static String HEADER_STRING;

    @Value("${jwt.secret}")
    public void setSECRET(String SECRET) {
        SecurityConstant.SECRET = SECRET;
    }

    @Value("${jwt.expiration_time}")
    public void setExpirationTime(long expirationTime) {
        EXPIRATION_TIME = expirationTime;
    }

    @Value("${jwt.token_prefix}")
    public void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }

    @Value("${jwt.header_string}")
    public void setHeaderString(String headerString) {
        HEADER_STRING = headerString;
    }
}
