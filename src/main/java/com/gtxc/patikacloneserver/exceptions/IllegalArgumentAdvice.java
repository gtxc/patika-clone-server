package com.gtxc.patikacloneserver.exceptions;

/*
    Created by gt at 2:26 PM on Thursday, March 10, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.exceptions.
*/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IllegalArgumentAdvice {
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String illegalArgumentHandler(IllegalArgumentException exception) {
        return exception.getMessage();
    }
}
