package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 4:18 PM on Wednesday, March 02, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import java.util.Optional;

public interface SimpleCrudRepository<T, ID> {

    Optional<T> findById(ID id);

    Iterable<T> findAll();

    Optional<T> save(T t);

    Long count();

    void deleteAll();

    void deleteById(ID id);

    boolean existsById(ID id);
}
