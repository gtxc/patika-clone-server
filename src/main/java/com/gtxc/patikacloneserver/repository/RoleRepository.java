package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 12:07 PM on Tuesday, March 08, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.model.Role;
import com.gtxc.patikacloneserver.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
//@PreAuthorize("hasAuthority('USER')")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Boolean existsByRoleType(RoleType roleType);
    Optional<Role> findByRoleType(RoleType roleType);
    @Query(value =
            "SELECT USER.ID\n" +
            "FROM USER\n" +
            "INNER JOIN USER_ROLES\n" +
            "ON USER.ID = USER_ROLES.USER_ID\n" +
            "INNER JOIN ROLE\n" +
            "ON USER_ROLES.ROLE_ID = ROLE.ID\n" +
            "WHERE ROLE.ID = :id",
            nativeQuery = true)
    List<Long> findRoleUsers(@Param("id") Long id);
}
