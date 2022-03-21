package com.gtxc.patikacloneserver.helper;

/*
    Created by gt at 5:27 PM on Friday, February 25, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.helper.
*/

import com.gtxc.patikacloneserver.exceptions.SQLite3Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

public class DBConnector implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(DBConnector.class);
    private final Connection connection;

    public DBConnector() {
        try {
            log.info("DB connection established");
            this.connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD);
        } catch (SQLException e) {
            String dbError = "DB connection failed : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public void enableForeignKeySupport() {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute(SQLStatement.enableForeignKeySupport);
            log.info("Foreign Key Support enabled");
        } catch (SQLException e) {
            String dbError = "Failed Enable Foreign Key Support : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        try {
            this.connection.close();
            log.info("DB connection closed");
        } catch (SQLException e) {
            String dbError = "Close DB connection error: " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }
}
