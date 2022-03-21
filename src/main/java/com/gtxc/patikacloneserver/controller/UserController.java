package com.gtxc.patikacloneserver.controller;

/*
    Created by gt at 2:05 PM on Wednesday, March 02, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import com.gtxc.patikacloneserver.model.User;
import com.gtxc.patikacloneserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public @ResponseBody List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/api/users/{id}")
    public @ResponseBody User getUserById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping("/api/users")
    public @ResponseBody List<User> addNewUser(@RequestBody User user) {
        userService.addNew(user);
        return getAllUsers();
    }

    @DeleteMapping("/api/users/{id}")
    public @ResponseBody List<User> removeUser(@PathVariable("id") Long id) {
        userService.removeById(id);
        return getAllUsers();
    }

    @DeleteMapping("/api/users/remove-all")
    public @ResponseBody List<User> removeUsers() {
        userService.removeAll();
        return getAllUsers();
    }

    @PutMapping("/api/users/{id}")
    public @ResponseBody User updateUser(@RequestBody User user, @PathVariable("id") Long id) {
        return userService.update(user, id);
    }
}
