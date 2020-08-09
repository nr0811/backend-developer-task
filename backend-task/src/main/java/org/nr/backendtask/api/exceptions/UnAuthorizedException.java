package org.nr.backendtask.api.exceptions;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends ApiException {


    public UnAuthorizedException() {
        super();
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    @Override
    public String getDefaultErrorMessage() {
        return "Unauthorized";
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}