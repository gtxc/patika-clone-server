package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 12:58 PM on Thursday, March 03, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import java.io.Serializable;

public enum RoleType implements Serializable {
    USER,
    INSTRUCTOR,
    MODERATOR,
    STUDENT,
    DEV,
}
