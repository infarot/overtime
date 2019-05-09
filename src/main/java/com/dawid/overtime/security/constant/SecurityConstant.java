package com.dawid.overtime.security.constant;

import org.springframework.beans.factory.annotation.Value;

public class SecurityConstant {

    @Value("${jwt.secret}")
    public static String SECRET;
    @Value("${jwt.expiration_time}")
    public static long EXPIRATION_TIME;
    @Value("${jwt.token_prefix}")
    public static String TOKEN_PREFIX;
    @Value("${jwt.header_string}")
    public static String HEADER_STRING;
}
