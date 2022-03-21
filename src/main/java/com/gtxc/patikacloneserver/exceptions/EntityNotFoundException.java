package com.gtxc.patikacloneserver.exceptions;

/*
    Created by gt at 11:31 AM on Thursday, March 03, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.exceptions.
*/

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String s) {
        super(s);
    }
}
