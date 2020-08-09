package org.nr.backendtask.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ExceptionResponse {

    private int httpStatus;
    private String message;
    private String path;

    private ExceptionResponse(Builder builder) {
        this.httpStatus = builder.httpStatus.value();
        this.message = builder.message;
        this.path = builder.path;
    }

    protected void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus.value();
    }

    protected void setMessage(String message) {
        this.message = message;
    }


    protected void setPath(String path) {
        this.path = path;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    static class Builder {

        private HttpStatus httpStatus;
        private String message;
        private String path;

        Builder() {
        }

        Builder withStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        Builder forPath(String path) {
            this.path = path;
            return this;
        }

        ExceptionResponse build() {
            return new ExceptionResponse(this);
        }

        ResponseEntity buildResponse() {
            var body = build();
            return new ResponseEntity<>(body, HttpStatus.valueOf(body.httpStatus));
        }
    }

}