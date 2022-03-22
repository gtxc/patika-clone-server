package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 5:05 PM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.exceptions.SQLite3Exception;
import com.gtxc.patikacloneserver.helper.DBConnector;
import com.gtxc.patikacloneserver.helper.SQLStatement;
import com.gtxc.patikacloneserver.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    private static final Logger log = LoggerFactory.getLogger(CourseRepository.class);

    @Override
    public Optional<Course> findById(Long id) {
        Optional<Course> optionalCourse = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryCourseById)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalCourse = Optional.of(fillCourseInfo(resultSet));
                log.info("query by id : " + optionalCourse.orElse(null));
            } else {
                log.warn("Could not found course : " + id);
            }
            return optionalCourse;
        } catch (SQLException e) {
            String dbError = "Failed find course by id : " + id + " : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public List<Course> findAll() {
        List<Course> courseList = new ArrayList<>();
        Course course;
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.queryAllCourses)) {
            while (resultSet.next()) {
                course = fillCourseInfo(resultSet);
                courseList.add(course);
            }
            if (courseList.isEmpty()) {
                log.warn("Could not found any course");
            } else {
                log.info("query : " + courseList);
            }
            return courseList;
        } catch (SQLException e) {
            String dbError = "Failed find all courses : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public Optional<Course> save(Course course) {
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prAddNewCourse)) {
            if (course.getId() != null) {
                preparedStatement.setLong(1, course.getId());
            } else {
                preparedStatement.setNull(1, java.sql.Types.NULL);
            }
            preparedStatement.setString(2, course.getName());
            preparedStatement.setString(3, course.getInfo() != null ? course.getInfo() : null);
            if (preparedStatement.executeUpdate() <= 0) {
                return Optional.empty();
            }
            Optional<Course> saved = findByName(course.getName());
            log.info("save : " + saved.orElse(null));
            return saved;
        } catch (SQLException e) {
            String dbError = "Failed save course : " + course + " : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public Long count() {
        long rowCount = 0;
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.countCourseRows)) {
            if (resultSet.next()) {
                rowCount = resultSet.getLong(1);
            }
            log.info("Course table row count : " + rowCount);
            return rowCount;
        } catch (SQLException e) {
            String dbError = "Failed get row count : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public void deleteAll() {
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.dropTable("course"));
            statement.execute(SQLStatement.createCourseTable);
            log.info("Course table all rows deleted");
        } catch (SQLException e) {
            String dbError = "Failed delete all : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prDeleteCourseById)) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() <= 0) {
                log.warn("Could not delete course : " + id);
            } else {
                log.info("Course deleted : " + id);
            }
        } catch (SQLException e) {
            String dbError = "Failed delete course by id : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

    private Course fillCourseInfo(ResultSet resultSet) {
        Course course = new Course();
        try {
            course.setId(resultSet.getLong("ID"));
            course.setName(resultSet.getString("NAME"));
            course.setInfo(resultSet.getString("INFO"));
            return course;
        } catch (SQLException e) {
            String dbError = "Failed fill course info : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public Optional<Course> findByName(String name) {
        Optional<Course> optionalCourse = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryCourseByName)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                optionalCourse = Optional.of(fillCourseInfo(resultSet));
                log.info("query by course name : " + optionalCourse.orElse(null));
            } else {
                log.warn("Could not found course : " + name);
            }
            return optionalCourse;
        } catch (SQLException e) {
            String dbError = "Failed find by course name : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }
}
