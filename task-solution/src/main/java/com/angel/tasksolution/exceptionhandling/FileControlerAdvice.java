package com.angel.tasksolution.exceptionhandling;

import com.angel.tasksolution.dto.ErrorDto;
import com.angel.tasksolution.exception.NoCrossingPeriodsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.time.LocalDate;

@RestControllerAdvice
public class FileControlerAdvice {

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleException(final IOException exception, final HttpServletRequest request) {
       return ErrorDto.builder()
                .errorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Failed to read file!")
                .path(request.getRequestURI())
                .timeStamp(LocalDate.now())
                .build();
    }

    @ExceptionHandler(NoCrossingPeriodsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleException(final NoCrossingPeriodsException exception,
                                    final HttpServletRequest request) {
        return ErrorDto.builder()
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .timeStamp(LocalDate.now())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDto handleException(final IllegalArgumentException exception,
                                    final HttpServletRequest request) {
        return ErrorDto.builder()
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .timeStamp(LocalDate.now())
                .build();
    }
}

