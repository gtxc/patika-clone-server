package com.gtxc.patikacloneserver.exceptions;

/*
    Created by gt at 11:32 AM on Thursday, March 03, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.exceptions.
*/

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EntityNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String entityNotFoundHandler(EntityNotFoundException exception) {
        return exception.getMessage();
    }
}
