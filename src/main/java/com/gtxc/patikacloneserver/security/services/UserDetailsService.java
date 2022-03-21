package com.gtxc.patikacloneserver.security.services;

/*
    Created by gt at 3:05 AM on Monday, March 14, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.security.services.
*/

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
