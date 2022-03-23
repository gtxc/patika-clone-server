package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 3:14 PM on Friday, March 04, 2022.
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
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;
    private String info;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "USER_COURSES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
    private Set<User> users = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "PATIKA_COURSES",
            joinColumns = @JoinColumn(name = "PATIKA_ID"),
            inverseJoinColumns = @JoinColumn(name = "COURSE_ID"))
    private Set<Patika> patikas = new HashSet<>();

    public Course(String name) {
        this.name = name;
    }
}
