package org.nr.backendtask.api.exceptions;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class ExceptionTransformer {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity methodNotAllowedHandler(HttpServletRequest request) {
        return new ExceptionResponse.Builder()
                .withStatus(HttpStatus.METHOD_NOT_ALLOWED)
                .forPath(request.getServletPath())
                .withMessage("Method " + request.getMethod() + " not allowed").buildResponse();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity wrongArgumentHandler(HttpServletRequest request) {
        System.out.println(request);
        return new ExceptionResponse.Builder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .forPath(request.getServletPath())
                .withMessage("Wrong type of parameter in path").buildResponse();

    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity notSupportedMediaTypeHandler(HttpServletRequest request) {
        return new ExceptionResponse.Builder()
                .withStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .forPath(request.getRequestURI())
                .withMessage("Content type " + request.getContentType() + " not supported").buildResponse();
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity badlyFormattedJsonHandler(HttpServletRequest request) {

        return new ExceptionResponse.Builder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .forPath(request.getRequestURI())
                .withMessage("Could not read JSON request.").buildResponse();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity wrongObjectInRequestHandler(HttpServletRequest request) {

        return new ExceptionResponse.Builder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .forPath(request.getRequestURI())
                .withMessage("Wrong parameters in body").buildResponse();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity parametersInRequestNotOkHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {

        Optional<ObjectError> optionalError = ex.getBindingResult().getAllErrors().stream().findFirst();
        String error = "";
        if (optionalError.isPresent()) {
            error = optionalError.get().getDefaultMessage();
        }


        return new ExceptionResponse.Builder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .forPath(request.getRequestURI())
                .withMessage(error).buildResponse();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity apiExceptionsHandler(HttpServletRequest request, ApiException ex) {
        var response = ex.toResponse();
        response.setPath(request.getRequestURI());
        return new ResponseEntity(response, HttpStatus.valueOf(response.getHttpStatus()));

    }


}
