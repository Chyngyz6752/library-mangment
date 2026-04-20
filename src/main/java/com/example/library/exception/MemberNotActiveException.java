package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MemberNotActiveException extends BusinessException {
    public MemberNotActiveException(String message) {
        super(message);
    }
}
