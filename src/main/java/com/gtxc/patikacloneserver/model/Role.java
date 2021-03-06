package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 1:48 AM on Tuesday, March 08, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Role implements Serializable {

    private Long id;
    private RoleType roleType;

    private Set<User> users = new HashSet<>();

    public Role() {}

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }

    public Role(Long id, RoleType roleType) {
        this.id = id;
        this.roleType = roleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleType=" + roleType +
                ", users=" + users +
                '}';
    }
}
