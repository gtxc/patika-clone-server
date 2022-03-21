package com.gtxc.patikacloneserver.service;

/*
    Created by gt at 6:11 PM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.service.
*/

import com.gtxc.patikacloneserver.exceptions.EntityNotFoundException;
import com.gtxc.patikacloneserver.model.Course;
import com.gtxc.patikacloneserver.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements SimpleEntityService<Course, Long> {

    public static final Logger log = LoggerFactory.getLogger(CourseService.class);
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course getById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Could not found course " + id
                ));
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = courseRepository.findAll();
        if (courses.isEmpty()) {
            throw new EntityNotFoundException("Could not found any course");
        }
        return courses;
    }

    @Override
    public Course addNew(Course course) {
        if (course.getId() != null && courseRepository.existsById(course.getId())) {
            String unavailable = "Course id '" + course.getId() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        } else if (course.getName() != null && courseRepository.existsByName(course.getName())) {
            String unavailable = "Course name '" + course.getName() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        }
        Optional<Course> newCourse = courseRepository.save(course);
        if (newCourse.isPresent()) {
            return newCourse.get();
        } else {
            String retrieveError = "Error while getting added course : " + course;
            log.warn(retrieveError);
            throw new EntityNotFoundException(retrieveError);
        }
    }

    @Override
    public void removeById(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        }
    }

    @Override
    public void removeAll() {
        if (!courseRepository.findAll().isEmpty()) {
            courseRepository.deleteAll();
        }
    }

    @Override
    public Course update(Course course, Long id) {
        Course oldCourse = getById(id);
        oldCourse.setId(id);
        oldCourse.setName(course.getName() != null && !course.getName().isEmpty() ? course.getName() : oldCourse.getName());
        oldCourse.setInfo(course.getInfo() != null && !course.getInfo().isEmpty() ? course.getInfo() : oldCourse.getInfo());
        removeById(id);
        return addNew(oldCourse);
    }

    public Course getByName(String name) {
        return courseRepository.findByName(name).orElse(null);
    }
}
