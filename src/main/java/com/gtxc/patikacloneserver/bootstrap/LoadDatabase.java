package com.gtxc.patikacloneserver.bootstrap;

/*
    Created by gt at 12:47 PM on Thursday, March 03, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.bootstrap.
*/

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {

        };
    }
}
