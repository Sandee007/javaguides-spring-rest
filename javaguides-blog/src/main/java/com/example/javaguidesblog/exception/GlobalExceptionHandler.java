package com.example.javaguidesblog.exception;

import com.example.javaguidesblog.payload.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

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
    public ResponseEntity<CustomErrorResponse> handleGlobalException(Exception globalException, WebRequest webRequest){

        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                new Date(),
                globalException.getMessage(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
