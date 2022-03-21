package com.gtxc.patikacloneserver.service;

/*
    Created by gt at 10:18 AM on Thursday, March 03, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.security.services.
*/

import com.gtxc.patikacloneserver.exceptions.EntityNotFoundException;
import com.gtxc.patikacloneserver.model.User;
import com.gtxc.patikacloneserver.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements SimpleEntityService<User, Long> {

    public static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Could not found user " + id
                ));
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Could not found any user");
        }
        return users;
    }

    @Override
    public User addNew(User user) {
        if (user.getId() != null && userRepository.existsById(user.getId())) {
            String unavailable = "User id '" + user.getId() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        } else if (user.getUsername() != null && userRepository.existsByUsername(user.getUsername())) {
            String unavailable = "Username '" + user.getUsername() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        } else if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            String unavailable = "Email '" + user.getEmail() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        }
        Optional<User> newUser = userRepository.save(user);
        if (newUser.isPresent()) {
            return newUser.get();
        } else {
            String retrieveError = "Error while getting added user : " + user;
            log.warn(retrieveError);
            throw new EntityNotFoundException(retrieveError);
        }
    }

    @Override
    public void removeById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public void removeAll() {
        if (!userRepository.findAll().isEmpty()) {
            userRepository.deleteAll();
        }
    }

    @Override
    public User update(User user, Long id) {
        User oldUser = getById(id);
        oldUser.setId(id);
        oldUser.setFirstName(user.getFirstName() != null && !user.getFirstName().isEmpty() ? user.getFirstName() : oldUser.getFirstName());
        oldUser.setLastName(user.getLastName() != null && !user.getLastName().isEmpty() ? user.getLastName() : oldUser.getLastName());
        oldUser.setUsername(user.getUsername() != null && !user.getUsername().isEmpty() ? user.getUsername() : oldUser.getUsername());
        oldUser.setEmail(user.getEmail() != null && !user.getEmail().isEmpty() ? user.getEmail() : oldUser.getEmail());
        oldUser.setPassword(user.getPassword() != null && !user.getPassword().isEmpty() ? user.getPassword() : oldUser.getPassword());
        removeById(id);
        return addNew(oldUser);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
