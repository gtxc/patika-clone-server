package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 3:11 PM on Friday, March 04, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Patika implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
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

    public Patika() {}

    public Patika(String name) {
        this.name = name;
    }

    public Patika(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<User> getStudents() {
        return users;
    }

    public void setStudents(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Patika{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                ", users=" + users +
                '}';
    }
}
