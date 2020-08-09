package org.nr.backendtask.api.exceptions;

import org.springframework.http.HttpStatus;

abstract class ApiException extends Exception {
    private String message;

    ApiException() {
        this.message = getDefaultErrorMessage();
    }

    ApiException(String message) {
        this.message = message;
    }

    public abstract String getDefaultErrorMessage();

    ExceptionResponse toResponse() {
        return new ExceptionResponse.Builder().withStatus(getHttpStatus()).withMessage(getMessage()).build();

    }

    public abstract HttpStatus getHttpStatus();

    @Override
    public String getMessage() {
        if (message == null) {
            return getDefaultErrorMessage();
        } else return message;
    }
}