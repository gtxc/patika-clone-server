package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 12:07 PM on Tuesday, March 08, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.model.Role;
import com.gtxc.patikacloneserver.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@PreAuthorize("hasAuthority('USER')")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Boolean existsByRoleType(RoleType roleType);
    Optional<Role> findByRoleType(RoleType roleType);

}
