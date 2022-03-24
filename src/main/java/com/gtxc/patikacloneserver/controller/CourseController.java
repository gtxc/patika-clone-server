package com.gtxc.patikacloneserver.controller;

/*
    Created by gt at 7:22 PM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import com.gtxc.patikacloneserver.model.Course;
import com.gtxc.patikacloneserver.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public @ResponseBody List<Course> getAllCourses() {
        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Course getCourseById(@PathVariable("id") Long id) {
        return courseService.getById(id);
    }

    @PostMapping("/")
    public @ResponseBody List<Course> addNewCourse(@RequestBody Course course) {
        courseService.addNew(course);
        return getAllCourses();
    }

    @DeleteMapping("/{id}")
    public @ResponseBody List<Course> removeCourse(@PathVariable("id") Long id) {
        courseService.removeById(id);
        return getAllCourses();
    }

    @DeleteMapping("/remove-all")
    public @ResponseBody List<Course> removeCourses() {
        courseService.removeAll();
        return getAllCourses();
    }

    @PutMapping("/{id}")
    public @ResponseBody Course updateCourse(@RequestBody Course course, @PathVariable("id") Long id) {
        return courseService.update(course, id);
    }
}
