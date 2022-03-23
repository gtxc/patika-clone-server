package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 11:25 AM on Wednesday, March 02, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @NotNull
    @Size(max = 20)
    @Column(unique = true)
    private String username;
    @NotNull
    @Size(max = 50)
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    @Size(max = 120)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "USER_ROLES",
                joinColumns = @JoinColumn(name = "USER_ID"),
                inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "USER_COURSES",
                joinColumns = @JoinColumn(name = "USER_ID"),
                inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
    private Set<Course> courses = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "USER_PATIKAS",
                joinColumns = @JoinColumn(name = "USER_ID"),
                inverseJoinColumns = @JoinColumn(name = "PATIKA_ID"))
    private Set<Patika> patikas = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
