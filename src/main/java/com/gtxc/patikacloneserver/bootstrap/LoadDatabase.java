package com.gtxc.patikacloneserver.bootstrap;

/*
    Created by gt at 12:47 PM on Thursday, March 03, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.bootstrap.
*/

import com.gtxc.patikacloneserver.model.*;
import com.gtxc.patikacloneserver.service.SimpleEntityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(SimpleEntityService<User,Long> userService,
                                   SimpleEntityService<Patika, Long> patikaService,
                                   SimpleEntityService<Course, Long> courseService,
                                   SimpleEntityService<Role, Long> roleService) {
        return args -> {
            try {userService.addNew(new User("gtxc", "gtxc@mail.com", "1234qwer1234"));} catch (Exception ignored){}
            User gt = new User("gtx", "gt@mail.com", "asdfasdf");
            Set<Role> roles = new HashSet<>();
            roles.add(new Role(RoleType.DEV));
            roles.add(new Role(RoleType.USER));
            gt.setRoles(roles);
            try {userService.addNew(gt);} catch (Exception ignored){}
            try {userService.addNew(new User(17L, "elf", "elf@mail.com", "asdfasdfasdf"));} catch (Exception ignored){}
            try {userService.addNew(new User("orc", "orc@mail.com", "orcorcorc"));} catch (Exception ignored){}

            try {patikaService.addNew(new Patika("Backend"));} catch (Exception ignored){}
            try {patikaService.addNew(new Patika("Frontend"));} catch (Exception ignored){}
            try {patikaService.addNew(new Patika("Devops"));} catch (Exception ignored){}
            try {patikaService.addNew(new Patika("CS50"));} catch (Exception ignored){}

            try {courseService.addNew(new Course("Java Basic", "Java"));} catch (Exception ignored){}
            try {courseService.addNew(new Course("C Programming"));} catch (Exception ignored){}
            try {courseService.addNew(new Course("Go Development", "Golang"));} catch (Exception ignored){}
            try {courseService.addNew(new Course("Game Development", "C++"));} catch (Exception ignored){}
            try {courseService.addNew(new Course("AWS", "SHELL, Python, Pearl"));} catch (Exception ignored){}

            try {roleService.addNew(new Role(RoleType.USER));} catch (Exception ignored){}
            try {roleService.addNew(new Role(RoleType.STUDENT));} catch (Exception ignored){}
            try {roleService.addNew(new Role(RoleType.INSTRUCTOR));} catch (Exception ignored){}
            try {roleService.addNew(new Role(RoleType.DEV));} catch (Exception ignored){}
            try {roleService.addNew(new Role(RoleType.MODERATOR));} catch (Exception ignored){}

        };
    }
}
