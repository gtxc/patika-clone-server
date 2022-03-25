package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 3:38 PM on Wednesday, March 02, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@PreAuthorize("hasAuthority('USER')")
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}
