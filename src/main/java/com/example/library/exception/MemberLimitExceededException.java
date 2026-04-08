package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое, когда читатель превысил лимит активных займов.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class MemberLimitExceededException extends BusinessException {
    public MemberLimitExceededException(String message) {
        super(message);
    }
}