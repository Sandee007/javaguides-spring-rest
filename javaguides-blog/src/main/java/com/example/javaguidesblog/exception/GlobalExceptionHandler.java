package com.example.javaguidesblog.exception;

import com.example.javaguidesblog.payload.CustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // * ResponseEntityExceptionHandler is added for DTO validations

    //    * handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class) // ! **************
    public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException,
            WebRequest webRequest
    ) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                new Date(),
                resourceNotFoundException.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<CustomErrorResponse> handleBlogApiException(
            BlogApiException blogApiException,
            WebRequest webRequest
    ) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                new Date(),
                blogApiException.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    //    * handle global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGlobalException(Exception globalException, WebRequest webRequest) {

        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                new Date(),
                globalException.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //    ! must have to handle validations
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        //        * working, not using stream api
        //        Map<String, String> errors = new HashMap<>();
        //        ex.getBindingResult().getAllErrors().forEach(error -> {
        //            String key = ((FieldError) error).getField();
        //            String value = error.getDefaultMessage();
        //            errors.put(key, value);
        //        });

        /*
         ! NOTE - map errors using stream api
         *
         * validations might will more errors for the same key
         * key will be duplicated, hence the toMap will not work
         * must use 3rd argument to remove duplicates (merge function)
         * */
        Map<String, String> errors = ex.getBindingResult().getAllErrors()
                                       .stream()
                                       .collect(Collectors.toMap(
                                               error -> ((FieldError) error).getField(), // * key mapper
                                               error -> error.getDefaultMessage(), // * value mapper
                                               (duplicateKey1, duplicateKey2) -> duplicateKey1 // * merge function
                                       ));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    //    * access denied exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> accessDeniedException(
            AccessDeniedException accessDeniedException,
            WebRequest webRequest
    ) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                new Date(),
                accessDeniedException.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
    }
}
