package com.angel.tasksolution.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@Getter
public class EmployeeInfoDto {
    private final String employeeId;
    private final String projectId;
    private final LocalDate startDate;
    private final LocalDate endDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeInfoDto that = (EmployeeInfoDto) o;
        return Objects.equals(getEmployeeId(), that.getEmployeeId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmployeeId());
    }
}
