package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, выбрасываемое, когда экземпляр книги недоступен для выдачи.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class CopyNotAvailableException extends BusinessException {
    public CopyNotAvailableException(String message) {
        super(message);
    }
}