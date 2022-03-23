package com.gtxc.patikacloneserver.config;

/*
    Created by gt at 1:25 PM on Wednesday, March 23, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.config.
*/

public interface SecurityConstants {
    String SECRET = "SECRET_KEY";
    long EXPIRATION_TIME = 900_000; // 15 mins
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    String SIGN_UP_URL = "/api/services/controller/user";
}