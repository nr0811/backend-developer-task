package org.nr.backendtask.api.exceptions;

import org.springframework.http.HttpStatus;

public class ApiValidationException extends ApiException {

    public ApiValidationException() {
    }

    public ApiValidationException(String message) {
        super(message);
    }

    @Override
    public String getDefaultErrorMessage() {
        return "Validation failed";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}