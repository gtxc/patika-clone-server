package com.gtxc.patikacloneserver.controller;

/*
    Created by gt at 10:51 AM on Monday, March 07, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import com.gtxc.patikacloneserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/api/login")
//    public @ResponseBody TestLoginToken sendToken() {
//        return new TestLoginToken("test123");
//    }

//    @PostMapping("/api/login")
//    public @ResponseBody TestLoginToken authenticateUser(@RequestBody User user) {
//        System.out.println(userService.getByUname(user.getUname()));
//        return sendToken();
//    }
}
