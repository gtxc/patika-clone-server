package com.gtxc.patikacloneserver.helper;

/*
    Created by gt at 11:30 AM on Wednesday, March 02, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import org.apache.logging.log4j.message.Message;

public interface Config {
    String DB_URL = "jdbc:sqlite:testdb.sqlite3";
    String DB_USERNAME = "root";
    String DB_PASSWORD = "root";
}
