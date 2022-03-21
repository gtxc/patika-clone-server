package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 3:11 PM on Friday, March 04, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Patika implements Serializable {

    private Long id;
    private String name;

    private Set<Course> courses = new HashSet<>();
    private Set<User> students = new HashSet<>();

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
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Patika{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                ", students=" + students +
                '}';
    }
}
