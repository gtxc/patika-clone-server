package com.gtxc.patikacloneserver.exceptions;

/*
    Created by gt at 6:57 AM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.exceptions.
*/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SQLite3ExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(SQLite3Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String dbErrorHandler(SQLite3Exception exception) {
        return exception.getMessage();
    }
}
