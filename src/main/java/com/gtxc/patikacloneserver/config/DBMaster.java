package com.gtxc.patikacloneserver.config;

/*
    Created by gt at 9:35 AM on Wednesday, March 02, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.config.
*/

import com.gtxc.patikacloneserver.helper.DBConnector;
import com.gtxc.patikacloneserver.helper.SQLStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DBMaster {

    private static final Logger log = LoggerFactory.getLogger(DBMaster.class);
    private static final DBConnector dbConnector = new DBConnector();

    @Bean
    CommandLineRunner constructDB() {
        return args -> {
            dbConnector.getConnection().setAutoCommit(false);
            checkTable("user");
            checkTable("patika");
            checkTable("course");
            checkTable("role");
            checkTable("user_role");
            checkTable("user_patika");
            checkTable("user_course");
            checkTable("course_patika");
            dbConnector.close();
        };
    }

    private boolean createUserTable() {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.createUserTAble);
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn("Create User Table error : " + e.getMessage());
        }
        return false;
    }

    private boolean createPatikaTable() {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.createPatikaTable);
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn("Create Patika Table error : " + e.getMessage());
        }
        return false;
    }

    private boolean createCourseTable() {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.createCourseTable);
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn("Create Course Table error : " + e.getMessage());
        }
        return false;
    }

    private boolean createRoleTable() {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.createRoleTable);
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn("Create Role Table error : " + e.getMessage());
        }
        return false;
    }

    private boolean createUserRoleTable() {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.createUserRoleTable);
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn("Create User_Role Table error : " + e.getMessage());
        }
        return false;
    }

    private boolean createUserPatikaTable() {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.createUserPatikaTable);
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn("Create User_Patika Table error : " + e.getMessage());
        }
        return false;
    }

    private boolean createUserCourseTable() {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.createUserCourseTable);
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn("Create User_Course Table error : " + e.getMessage());
        }
        return false;
    }

    private boolean createCoursePatikaTable() {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.createCoursePatikaTable);
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn("Create Course_Patika Table error : " + e.getMessage());
        }
        return false;
    }

    private boolean checkColumnNames(String tableName) {
        try (Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.tableInfo(tableName))) {
            dbConnector.getConnection().commit();
            List<String> columnNames = new ArrayList<>();
            while (resultSet.next()) {
                columnNames.add(resultSet.getString("name"));
            }
            switch (tableName) {
                case "user":
                    return validateUserTable(columnNames);
                case "patika":
                    return validatePatikaTable(columnNames);
                case "course":
                    return validateCourseTable(columnNames);
                case "role":
                    return validateRoleTable(columnNames);
                case "user_role":
                    return validateUserRoleTable(columnNames);
                case "user_patika":
                    return validateUserPatikaTable(columnNames);
                case "user_course":
                    return validateUserCourseTable(columnNames);
                case "course_patika":
                    return validateCoursePatikaTable(columnNames);
                default:
                    log.warn("No table to check : " + tableName);
            }
            return false;
        } catch (SQLException e) {
            log.warn(tableName + " : Check column names error : " + e.getMessage());
        }
        return false;
    }

    private boolean dropTable(String tableName) {
        try (Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.dropTable(tableName));
            dbConnector.getConnection().commit();
            return true;
        } catch (SQLException e) {
            log.warn(tableName + " : Table drop error : " + e.getMessage());
        }
        return false;
    }

    private boolean validateUserTable(List<String> columnNames) {
        return columnNames.size() == 6 &&
               columnNames.get(0).equalsIgnoreCase("id") &&
               columnNames.get(1).equalsIgnoreCase("first_name") &&
               columnNames.get(2).equalsIgnoreCase("last_name") &&
               columnNames.get(3).equalsIgnoreCase("username") &&
               columnNames.get(4).equalsIgnoreCase("email") &&
               columnNames.get(5).equalsIgnoreCase("password");
    }

    private boolean validatePatikaTable(List<String> columnNames) {
        return columnNames.size() == 2 &&
               columnNames.get(0).equalsIgnoreCase("id") &&
               columnNames.get(1).equalsIgnoreCase("name");
    }

    private boolean validateCourseTable(List<String> columnNames) {
        return columnNames.size() == 3 &&
               columnNames.get(0).equalsIgnoreCase("id") &&
               columnNames.get(1).equalsIgnoreCase("name") &&
               columnNames.get(2).equalsIgnoreCase("info");
    }

    private boolean validateRoleTable(List<String> columnNames) {
        return columnNames.size() == 2 &&
                columnNames.get(0).equalsIgnoreCase("id") &&
                columnNames.get(1).equalsIgnoreCase("role_type");
    }

    private boolean validateUserRoleTable(List<String> columnNames) {
        return columnNames.size() == 2 &&
                columnNames.get(0).equalsIgnoreCase("user_id") &&
                columnNames.get(1).equalsIgnoreCase("role_id");
    }

    private boolean validateUserPatikaTable(List<String> columnNames) {
        return columnNames.size() == 2 &&
                columnNames.get(0).equalsIgnoreCase("user_id") &&
                columnNames.get(1).equalsIgnoreCase("patika_id");
    }

    private boolean validateUserCourseTable(List<String> columnNames) {
        return columnNames.size() == 2 &&
                columnNames.get(0).equalsIgnoreCase("user_id") &&
                columnNames.get(1).equalsIgnoreCase("course_id");
    }

    private boolean validateCoursePatikaTable(List<String> columnNames) {
        return columnNames.size() == 2 &&
                columnNames.get(0).equalsIgnoreCase("course_id") &&
                columnNames.get(1).equalsIgnoreCase("patika_id");
    }

    private void checkTable(String tableName) {
        tableName = tableName.toLowerCase();
        if (!checkColumnNames(tableName)) {
            if (dropTable(tableName)) {
                log.info(tableName + " Table dropped due to unsatisfied conditions");
            }
            boolean createTableState = false;
            switch (tableName) {
                case "user":
                    createTableState = createUserTable();
                    break;
                case "patika":
                    createTableState = createPatikaTable();
                    break;
                case "course":
                    createTableState = createCourseTable();
                    break;
                case "role":
                    createTableState = createRoleTable();
                    break;
                case "user_role":
                    createTableState = createUserRoleTable();
                    break;
                case "user_patika":
                    createTableState = createUserPatikaTable();
                    break;
                case "user_course":
                    createTableState = createUserCourseTable();
                    break;
                case "course_patika":
                    createTableState = createCoursePatikaTable();
                    break;
                default:
                    log.warn("No table to check : " + tableName);
            }
            if (createTableState) {
                log.info(tableName + " Table created");
            }
        }
    }
}
