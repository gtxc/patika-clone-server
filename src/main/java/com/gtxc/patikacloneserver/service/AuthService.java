package com.gtxc.patikacloneserver.service;

/*
    Created by gt at 2:57 PM on Thursday, March 24, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.service.
*/

import com.gtxc.patikacloneserver.model.Role;
import com.gtxc.patikacloneserver.model.RoleType;
import com.gtxc.patikacloneserver.model.User;
import com.gtxc.patikacloneserver.payload.request.LoginRequest;
import com.gtxc.patikacloneserver.payload.request.SignupRequest;
import com.gtxc.patikacloneserver.payload.response.JwtResponse;
import com.gtxc.patikacloneserver.payload.response.MessageResponse;
import com.gtxc.patikacloneserver.repository.RoleRepository;
import com.gtxc.patikacloneserver.repository.UserRepository;
import com.gtxc.patikacloneserver.security.jwt.JwtUtils;
import com.gtxc.patikacloneserver.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    public JwtResponse signin(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        userRepository.findByUsername(userDetails.getUsername()).ifPresent(u -> {
            u.setLastLogin(new Timestamp(new Date().getTime()));
            userRepository.save(u);
        });
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public ResponseEntity<?> signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }

        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleType(RoleType.USER)
                    .orElseThrow(() -> new RuntimeException(
                            "Error: Role is not found"
                    ));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "INSTRUCTOR":
                        Role instructorRole = roleRepository.findByRoleType(RoleType.INSTRUCTOR)
                                .orElseThrow(() -> new RuntimeException(
                                        "Error: Role is not found"
                                ));
                        roles.add(instructorRole);
                        break;
                    case "MODERATOR":
                        Role moderatorRole = roleRepository.findByRoleType(RoleType.MODERATOR)
                                .orElseThrow(() -> new RuntimeException(
                                        "Error: Role is not found"
                                ));
                        roles.add(moderatorRole);
                        break;
                    case "STUDENT":
                        Role studentRole = roleRepository.findByRoleType(RoleType.STUDENT)
                                .orElseThrow(() -> new RuntimeException(
                                        "Error: Role is not found"
                                ));
                        roles.add(studentRole);
                        break;
                    case "DEV":
                        Role devRole = roleRepository.findByRoleType(RoleType.DEV)
                                .orElseThrow(() -> new RuntimeException(
                                        "Error: Role is not found"
                                ));
                        roles.add(devRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleType(RoleType.USER)
                                .orElseThrow(() -> new RuntimeException(
                                        "Error: Role is not found"
                                ));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user.setCreatedOn(new Timestamp(new Date().getTime()));
        userService.addNew(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
