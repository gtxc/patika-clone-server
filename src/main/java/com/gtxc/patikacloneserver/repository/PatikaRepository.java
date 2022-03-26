package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 2:46 PM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.model.Patika;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//@PreAuthorize("hasAuthority('USER')")
public interface PatikaRepository extends JpaRepository<Patika, Long> {
    Boolean existsByName(String name);
    Optional<Patika> findByName(String name);
}
