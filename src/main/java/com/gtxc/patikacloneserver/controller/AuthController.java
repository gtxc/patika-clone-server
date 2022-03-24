package com.gtxc.patikacloneserver.controller;

/*
    Created by gt at 12:29 PM on Monday, March 14, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import com.gtxc.patikacloneserver.payload.request.LoginRequest;
import com.gtxc.patikacloneserver.payload.request.SignupRequest;
import com.gtxc.patikacloneserver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.signin(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }
}
