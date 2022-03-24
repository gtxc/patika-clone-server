package com.gtxc.patikacloneserver.controller;

/*
    Created by gt at 10:50 AM on Monday, March 07, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/home")
public class HomeController {

}




// POST	/api/auth/signup	signup new account
// POST	/api/auth/signin	login an account
// GET	/api/test/all	    retrieve public content
// GET	/api/test/user	    access User’s content
// GET	/api/test/mod	    access Moderator’s content
// GET	/api/test/admin	    access Admin’s content
