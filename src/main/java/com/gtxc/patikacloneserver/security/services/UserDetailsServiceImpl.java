package com.gtxc.patikacloneserver.security.services;

/*
    Created by gt at 3:40 AM on Monday, March 14, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.security.services.
*/

import com.gtxc.patikacloneserver.model.User;
import com.gtxc.patikacloneserver.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User Not Found with username: " + username
                ));
        return UserDetailsImpl.build(user);
    }
}
