package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 1:48 AM on Tuesday, March 08, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    @NotNull
    private RoleType roleType;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<User> users = new HashSet<>();

    public Role(RoleType roleType) {
        this.roleType = roleType;
    }
}
