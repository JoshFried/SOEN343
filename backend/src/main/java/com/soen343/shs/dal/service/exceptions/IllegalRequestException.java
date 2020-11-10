package com.soen343.shs.dal.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalRequestException extends RuntimeException {
    public IllegalRequestException() {
        super();
    }

    public IllegalRequestException(final String msg) {
        super(msg);
    }
}
