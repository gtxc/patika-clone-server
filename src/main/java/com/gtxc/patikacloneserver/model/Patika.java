package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 3:11 PM on Friday, March 04, 2022.
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
public class Patika implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "PATIKA_COURSES",
            joinColumns = @JoinColumn(name = "PATIKA_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
    private Set<Course> courses = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "USER_PATIKAS",
                joinColumns = @JoinColumn(name = "USER_ID"),
                inverseJoinColumns = @JoinColumn(name = "PATIKA_ID"))
    private Set<User> users = new HashSet<>();
}
