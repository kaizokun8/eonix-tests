package com.user.config;

import com.user.validation.UserExist;
import com.user.validation.ValidationErrorResponse;
import com.user.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    /*
     * par defaut une erreur de validation au niveau des parametres de méthode entraine une ConstraintViolationException
     * qui renvoie un code 500 Internal Server Error plutot que 400 BAD_REQUEST ce qui est le cas avec les
     * validation du body MethodArgumentNotValidException (methodes Post). Permet de renvoyer un code 400.
     * Tutorial : https://reflectoring.io/bean-validation-with-spring-boot/
     * */

    private static Map<String, HttpStatus> violationsStatus;

    static {

        violationsStatus = new HashMap<>();

        violationsStatus.put(UserExist.class.getSimpleName(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {

        ValidationErrorResponse error = new ValidationErrorResponse();

        for (ConstraintViolation violation : e.getConstraintViolations()) {

            HttpStatus status = violationsStatus.get(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());

            status = status == null ? HttpStatus.BAD_REQUEST : status;

            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage(), status.value()));
        }

        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return error;
    }

}
