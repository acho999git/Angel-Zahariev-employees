package com.angel.tasksolution.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseDto {
    private String firstEmployee;
    private String secondEmployee;
    private String differenceInDays;
}
