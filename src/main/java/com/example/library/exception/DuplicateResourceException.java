package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение: попытка создать дублирующийся ресурс (например, ISBN или email).
 */

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
