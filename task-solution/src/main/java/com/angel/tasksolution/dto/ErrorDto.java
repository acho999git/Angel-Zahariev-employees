package com.angel.tasksolution.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto {
    private final LocalDate timeStamp;
    private final String errorCode;
    private final String statusCode;
    private final String message;
    private final String path;
}
