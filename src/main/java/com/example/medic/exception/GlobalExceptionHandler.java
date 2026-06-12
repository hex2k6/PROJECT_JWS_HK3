package com.example.medic.exception;

import com.example.medic.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    @ResponseStatus(
            HttpStatus.NOT_FOUND
    )
    public ErrorResponse
    handleNotFound(

            ResourceNotFoundException ex,

            HttpServletRequest request
    ) {

        return ErrorResponse
                .builder()
                .timestamp(
                        LocalDateTime.now()
                )
                .status(404)
                .error("Not Found")
                .message(
                        ex.getMessage()
                )
                .path(
                        request.getRequestURI()
                )
                .build();
    }

    @ExceptionHandler(
            ConflictException.class
    )
    @ResponseStatus(
            HttpStatus.CONFLICT
    )
    public ErrorResponse
    handleConflict(

            ConflictException ex,

            HttpServletRequest request
    ) {

        return ErrorResponse
                .builder()
                .timestamp(
                        LocalDateTime.now()
                )
                .status(409)
                .error("Conflict")
                .message(
                        ex.getMessage()
                )
                .path(
                        request.getRequestURI()
                )
                .build();
    }

    @ExceptionHandler(
            UnauthorizedException.class
    )
    @ResponseStatus(
            HttpStatus.UNAUTHORIZED
    )
    public ErrorResponse
    handleUnauthorized(

            UnauthorizedException ex,

            HttpServletRequest request
    ) {

        return ErrorResponse
                .builder()
                .timestamp(
                        LocalDateTime.now()
                )
                .status(401)
                .error("Unauthorized")
                .message(
                        ex.getMessage()
                )
                .path(
                        request.getRequestURI()
                )
                .build();
    }

    @ExceptionHandler(
            Exception.class
    )
    @ResponseStatus(
            HttpStatus.INTERNAL_SERVER_ERROR
    )
    public ErrorResponse
    handleException(

            Exception ex,

            HttpServletRequest request
    ) {

        return ErrorResponse
                .builder()
                .timestamp(
                        LocalDateTime.now()
                )
                .status(500)
                .error(
                        "Internal Server Error"
                )
                .message(
                        ex.getMessage()
                )
                .path(
                        request.getRequestURI()
                )
                .build();
    }
}