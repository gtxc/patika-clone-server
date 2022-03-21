package com.gtxc.patikacloneserver.helper;

/*
    Created by gt at 11:45 AM on Wednesday, March 02, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.helper.
*/

public interface SQLStatement {

    String enableForeignKeySupport =
            "PRAGMA FOREIGN_KEYS = ON;";

    String createUserTAble =
            "CREATE TABLE IF NOT EXISTS USER(" +
                    "ID INTEGER PRIMARY KEY," +
                    "FIRST_NAME VARCHAR," +
                    "LAST_NAME VARCHAR," +
                    "USERNAME VARCHAR NOT NULL UNIQUE," +
                    "EMAIL VARCHAR NOT NULL UNIQUE," +
                    "PASSWORD VARCHAR NOT NULL CHECK(LENGTH(PASSWORD) >= 8)" +
            ");";

    String createPatikaTable =
            "CREATE TABLE IF NOT EXISTS PATIKA(" +
                    "ID INTEGER PRIMARY KEY," +
                    "NAME VARCHAR NOT NULL UNIQUE" +
            ");";

    String createCourseTable =
            "CREATE TABLE IF NOT EXISTS COURSE(" +
                    "ID INTEGER PRIMARY KEY," +
                    "NAME VARCHAR NOT NULL UNIQUE," +
                    "INFO VARCHAR" +
           ");";

    String createRoleTable =
            "CREATE TABLE IF NOT EXISTS ROLE(" +
                    "ID INTEGER PRIMARY KEY," +
                    "ROLE_TYPE VARCHAR NOT NULL UNIQUE CHECK(UPPER(ROLE_TYPE) IN ('USER', 'INSTRUCTOR', 'MODERATOR', 'STUDENT', 'DEV'))" +
            ");";

    String createUserRoleTable =
            "CREATE TABLE IF NOT EXISTS USER_ROLE(" +
                    "USER_ID INTEGER NOT NULL," +
                    "ROLE_ID INTEGER NOT NULL," +
                    "FOREIGN KEY(USER_ID) REFERENCES USER(ID)," +
                    "FOREIGN KEY(ROLE_ID) REFERENCES ROLE(ID)" +
            ");";

    String createUserPatikaTable =
            "CREATE TABLE IF NOT EXISTS USER_PATIKA(" +
                    "USER_ID INTEGER NOT NULL," +
                    "PATIKA_ID INTEGER NOT NULL," +
                    "FOREIGN KEY(USER_ID) REFERENCES USER(ID)," +
                    "FOREIGN KEY(PATIKA_ID) REFERENCES PATIKA(ID)" +
            ");";

    String createUserCourseTable =
            "CREATE TABLE IF NOT EXISTS USER_COURSE(" +
                    "USER_ID INTEGER NOT NULL," +
                    "COURSE_ID INTEGER NOT NULL," +
                    "FOREIGN KEY(USER_ID) REFERENCES USER(ID)," +
                    "FOREIGN KEY(COURSE_ID) REFERENCES COURSE(ID)" +
            ");";

    String createCoursePatikaTable =
            "CREATE TABLE IF NOT EXISTS COURSE_PATIKA(" +
                    "COURSE_ID INTEGER NOT NULL," +
                    "PATIKA_ID INTEGER NOT NULL," +
                    "FOREIGN KEY(COURSE_ID) REFERENCES COURSE(ID)," +
                    "FOREIGN KEY(PATIKA_ID) REFERENCES PATIKA(ID)" +
            ");";

    String queryAllUsers =
            "SELECT * FROM USER;";
    String prQueryUserById =
            "SELECT * FROM USER WHERE ID = ?";
    String prQueryUserByUsername =
            "SELECT * FROM USER WHERE USERNAME = ?";
    String prQueryUserByEmail =
            "SELECT * FROM USER WHERE EMAIL = ?";
    String countUserRows =
            "SELECT COUNT(*) FROM USER";
    String prAddNewUser =
            "INSERT INTO USER(ID, FIRST_NAME, LAST_NAME, USERNAME, EMAIL, PASSWORD) VALUES(?, ?, ?, ?, ?, ?);";
    String prDeleteUserById =
            "DELETE FROM USER WHERE ID = ?";
    String prQueryUserRolesById =
            "SELECT ROLE_ID, ROLE_TYPE\n" +
            "FROM ROLE\n" +
            "INNER JOIN USER_ROLE ON ROLE.ID = USER_ROLE.ROLE_ID\n" +
            "INNER JOIN USER ON USER_ROLE.USER_ID = USER.ID\n" +
            "WHERE USER.ID = ?;";
    String prAssignRoleToUser =
            "INSERT INTO USER_ROLE(USER_ID, ROLE_ID) VALUES(?, ?);";

    String queryAllPatikas =
            "SELECT * FROM PATIKA;";
    String prQueryPatikaById =
            "SELECT * FROM PATIKA WHERE ID = ?";
    String prQueryPatikaByName =
            "SELECT * FROM PATIKA WHERE NAME = ?";
    String countPatikaRows =
            "SELECT COUNT(*) FROM PATIKA";
    String prAddNewPatika =
            "INSERT INTO PATIKA(ID, NAME) VALUES(?, ?);";
    String prDeletePatikaById =
            "DELETE FROM PATIKA WHERE ID = ?";

    String queryAllCourses =
            "SELECT * FROM COURSE;";
    String prQueryCourseById =
            "SELECT * FROM COURSE WHERE ID = ?";
    String prQueryCourseByName =
            "SELECT * FROM COURSE WHERE NAME = ?";
    String countCourseRows =
            "SELECT COUNT(*) FROM COURSE";
    String prAddNewCourse =
            "INSERT INTO COURSE(ID, NAME, INFO) VALUES(?, ?, ?);";
    String prDeleteCourseById =
            "DELETE FROM COURSE WHERE ID = ?";

    String queryAllRoles =
            "SELECT * FROM ROLE;";
    String prQueryRoleById =
            "SELECT * FROM ROLE WHERE ID = ?";
    String prQueryRoleByRoleType =
            "SELECT * FROM ROLE WHERE ROLE_TYPE = ?";
    String countRoleRows =
            "SELECT COUNT(*) FROM ROLE";
    String prAddNewRole =
            "INSERT INTO ROLE(ID, ROLE_TYPE) VALUES(?, ?);";
    String prDeleteRoleById =
            "DELETE FROM ROLE WHERE ID = ?";

    static String dropTable(String tableName) {
        return "DROP TABLE IF EXISTS " + tableName + ";";
    }

    static String tableInfo(String tableName) {
        return "PRAGMA TABLE_INFO(" + tableName + ");";
    }
}
