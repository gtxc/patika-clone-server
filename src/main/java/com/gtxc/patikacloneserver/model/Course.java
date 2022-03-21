package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 3:14 PM on Friday, March 04, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Course implements Serializable {

    private Long id;
    private String name;
    private String info;

    private Set<User> instructors = new HashSet<>();
    private Set<User> students = new HashSet<>();
    private Set<Patika> patikas = new HashSet<>();

    public Course() {}

    public Course(String name) {
        this.name = name;
    }

    public Course(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Course(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public Course(Long id, String name, String info) {
        this.id = id;
        this.name = name;
        this.info = info;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Set<User> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<User> instructors) {
        this.instructors = instructors;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }

    public Set<Patika> getPatikas() {
        return patikas;
    }

    public void setPatikas(Set<Patika> patikas) {
        this.patikas = patikas;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", instructors=" + instructors +
                ", students=" + students +
                ", patikas=" + patikas +
                '}';
    }
}
