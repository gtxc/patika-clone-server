package com.gtxc.patikacloneserver.controller;

/*
    Created by gt at 1:00 PM on Monday, March 14, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestAuthorizationController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('STUDENT') or hasAuthority('MODERATOR') or hasAuthority('DEV') or hasAuthority('INSTRUCTOR')")
    public String userAccess() {
        return "User Content";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasAuthority('MODERATOR') or hasAuthority('DEV')")
    public String moderatorAccess() {
        return "Moderator Content";
    }

    @GetMapping("/dev")
    @PreAuthorize("hasAuthority('DEV')")
    public String devAccess() {
        return "Dev Content";
    }

    @GetMapping("/student")
    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('DEV')")
    public String studentAccess() {
        return "Student Content";
    }

    @GetMapping("/instructor")
    @PreAuthorize("hasAuthority('DEV') or hasAuthority('INSTRUCTOR')")
    public String instructorAccess() {
        return "Instructor Content";
    }
}
