package com.gtxc.patikacloneserver.payload.request;

/*
    Created by gt at 12:21 PM on Monday, March 14, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.payload.request.
*/

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> role;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
