package com.bernardawj.notey.utility;

import com.bernardawj.notey.exception.NoteServiceException;
import com.bernardawj.notey.exception.ProjectServiceException;
import com.bernardawj.notey.exception.UserServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final Log LOGGER = LogFactory.getLog(ExceptionControllerAdvice.class);

    private final Environment environment;

    @Autowired
    public ExceptionControllerAdvice(Environment environment) {
        this.environment = environment;
    }

    @ExceptionHandler({ NoteServiceException.class, ProjectServiceException.class, UserServiceException.class })
    public ResponseEntity<ErrorInfo> serviceExceptionHandler(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(status.value());
        errorInfo.setMessage(environment.getProperty(exception.getMessage()));

        return new ResponseEntity<>(errorInfo, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalExceptionHandler(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(status.value());
        errorInfo.setMessage(environment.getProperty("General.EXCEPTION_MESSAGE"));

        return new ResponseEntity<>(errorInfo, status);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        HttpStatus status = HttpStatus.BAD_REQUEST;

        String message;
        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manvException = (MethodArgumentNotValidException) exception;
            message = manvException.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        } else {
            ConstraintViolationException cvException = (ConstraintViolationException) exception;
            message = cvException.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
        }

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(status.value());
        errorInfo.setMessage(message);

        return new ResponseEntity<>(errorInfo, status);
    }
}