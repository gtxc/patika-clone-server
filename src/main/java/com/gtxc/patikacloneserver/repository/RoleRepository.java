package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 12:07 PM on Tuesday, March 08, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.model.Role;
import com.gtxc.patikacloneserver.model.RoleType;
import com.gtxc.patikacloneserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@PreAuthorize("hasAuthority('USER')")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Boolean existsByRoleType(RoleType roleType);
    Optional<Role> findByRoleType(RoleType roleType);
    @Query( "SELECT * " +
            "FROM USER " +
            "INNER JOIN USER_ROLES" +
            "ON USER.ID = USER_ROLES.USER_ID")
    List<User> findRoleUsers(Long id);
}
