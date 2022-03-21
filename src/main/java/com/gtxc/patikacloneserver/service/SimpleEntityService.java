package com.gtxc.patikacloneserver.service;

/*
    Created by gt at 10:17 AM on Thursday, March 03, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.service.
*/

import java.util.List;

public interface SimpleEntityService<T, ID> {

    T getById(ID id);

    List<T> getAll();

    T addNew(T t);

    void removeById(ID id);

    void removeAll();

    T update(T t, ID id);
}
