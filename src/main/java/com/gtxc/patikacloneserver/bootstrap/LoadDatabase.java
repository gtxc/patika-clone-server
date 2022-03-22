package com.gtxc.patikacloneserver.bootstrap;

/*
    Created by gt at 12:47 PM on Thursday, March 03, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.bootstrap.
*/

import com.gtxc.patikacloneserver.model.Role;
import com.gtxc.patikacloneserver.model.RoleType;
import com.gtxc.patikacloneserver.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository) {
        return args -> {
            try {
                if (!roleRepository.existsByRoleType(RoleType.USER)) {
                    roleRepository.save(new Role(RoleType.USER));
                }
                if (!roleRepository.existsByRoleType(RoleType.INSTRUCTOR)) {
                    roleRepository.save(new Role(RoleType.INSTRUCTOR));
                }
                if (!roleRepository.existsByRoleType(RoleType.MODERATOR)) {
                    roleRepository.save(new Role(RoleType.MODERATOR));
                }
                if (!roleRepository.existsByRoleType(RoleType.STUDENT)) {
                    roleRepository.save(new Role(RoleType.STUDENT));
                }
                if (!roleRepository.existsByRoleType(RoleType.DEV)) {
                    roleRepository.save(new Role(RoleType.DEV));
                }
            } catch (Exception e) {
                System.out.println("exception : " + e.getMessage());
            }
        };
    }
}
