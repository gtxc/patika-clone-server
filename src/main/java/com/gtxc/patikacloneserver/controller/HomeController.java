package com.gtxc.patikacloneserver.controller;

/*
    Created by gt at 10:50 AM on Monday, March 07, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import com.gtxc.patikacloneserver.repository.RoleRepository;
import com.gtxc.patikacloneserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/roles")
public class HomeController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public HomeController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<?> getRoleUsers(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userRepository.findAllById(roleRepository.findRoleUsers(id)));
    }
}




// POST	/api/auth/signup	signup new account
// POST	/api/auth/signin	login an account
// GET	/api/test/all	    retrieve public content
// GET	/api/test/user	    access User’s content
// GET	/api/test/mod	    access Moderator’s content
// GET	/api/test/admin	    access Admin’s content
