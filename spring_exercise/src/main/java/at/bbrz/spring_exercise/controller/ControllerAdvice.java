package at.bbrz.spring_exercise.controller;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.ControllerAdvice
@Log
public class ControllerAdvice {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleExceptions(Exception exception) {
        log.info(exception.getMessage());
        return exception.getMessage();
    }
}
