package com.gtxc.patikacloneserver.model;

/*
    Created by gt at 3:14 PM on Friday, March 04, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.model.
*/

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
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
        return users;
    }

    public void setInstructors(Set<User> users) {
        this.users = users;
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
                ", users=" + users +
                ", patikas=" + patikas +
                '}';
    }
}
