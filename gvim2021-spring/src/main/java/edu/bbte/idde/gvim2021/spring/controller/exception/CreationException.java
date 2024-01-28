package edu.bbte.idde.gvim2021.spring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CreationException extends RuntimeException {
    public CreationException(String message) {
        super(message);
    }
}
