package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 5:05 PM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@PreAuthorize("hasAuthority('USER')")
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsById(Long id);
    boolean existsByName(String name);
    Optional<Course> findByName(String name);
}
