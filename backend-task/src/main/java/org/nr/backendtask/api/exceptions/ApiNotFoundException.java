package org.nr.backendtask.api.exceptions;

import org.springframework.http.HttpStatus;

public class ApiNotFoundException extends ApiException {
    public ApiNotFoundException() {
    }

    public ApiNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getDefaultErrorMessage() {
        return "Not found";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}