package org.nr.backendtask.api.exceptions;

import org.springframework.http.HttpStatus;

public class ApiUnhandledException extends ApiException {

    @Override
    public String getDefaultErrorMessage() {
        return "Internal server error";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
