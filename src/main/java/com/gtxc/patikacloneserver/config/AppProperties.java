package com.gtxc.patikacloneserver.config;

/*
    Created by gt at 2:12 PM on Thursday, March 24, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.config.
*/

public interface AppProperties {
    String SIGN_UP_URL = "/users/record";
    String SECRET_KEY = "SgVkYp2s5v8y/B?E(H+MbQeThWmZq4t6w9z$C&F)J@NcRfUjXn2r5u8x!A%D*G-K";
    String TOKEN_TYPE = "Bearer";
    String HEADER_AUTH = "Authorization";
    Long EXPIRATION_MS = 1000L*60*30;
}
