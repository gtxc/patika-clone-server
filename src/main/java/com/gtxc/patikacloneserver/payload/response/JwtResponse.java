package com.gtxc.patikacloneserver.payload.response;

/*
    Created by gt at 12:23 PM on Monday, March 14, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.payload.response.
*/

import com.gtxc.patikacloneserver.config.AppProperties;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = AppProperties.TOKEN_TYPE;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
