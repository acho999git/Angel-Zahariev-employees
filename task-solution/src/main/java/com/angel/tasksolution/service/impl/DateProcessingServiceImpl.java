package com.angel.tasksolution.service.impl;

import com.angel.tasksolution.dto.EmployeeInfoDto;
import com.angel.tasksolution.dto.ResponseDto;
import com.angel.tasksolution.exception.NoCrossingPeriodsException;
import com.angel.tasksolution.service.api.DateProcessingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.angel.tasksolution.constant.Constants.NULL;
import static com.angel.tasksolution.constant.Constants.SUPPORTED_FORMATS;

@Service
@Slf4j
public class DateProcessingServiceImpl implements DateProcessingService {

    @Override
    public ResponseDto getMaxCrossedWorkingPeriod(final  String[] rows) throws IOException {
        final List<EmployeeInfoDto> employeesInfo = getEmployeesInfo(rows);
        final Map<String, List<EmployeeInfoDto>> employeesByProjectId = getEmployeesInfoByProjectId(employeesInfo);

        return processProjectWorkingPeriods(employeesByProjectId);
    }

    private ResponseDto processProjectWorkingPeriods(final Map<String, List<EmployeeInfoDto>> employeesByProjectId) {
        long max = 0;
        long daysBetweenDates = 0;
        EmployeeInfoDto firstEmployee = null;
        EmployeeInfoDto secondEmployee = null;

        for(Map.Entry<String, List<EmployeeInfoDto>> entry : employeesByProjectId.entrySet()) {

            if (hasLessThanTwoEntries(entry)) {
                continue;
            }

            final List<EmployeeInfoDto> value = entry.getValue();

            for (int i = 0; i < value.size(); i++) {
                for (int j = i + 1; j < value.size(); j++) {
                    if (value.get(i).equals(value.get(j))) {
                        continue;
                    }

                    final LocalDate firstEmployeeStartDate = value.get(i).getStartDate();
                    final LocalDate firstEmployeeEndDate = value.get(i).getEndDate();
                    final LocalDate secondEmployeeStartDate = value.get(j).getStartDate();
                    final LocalDate secondEmployeeEndDate = value.get(j).getEndDate();

                    if (!hasCrossedPeriod(firstEmployeeStartDate, secondEmployeeEndDate,
                            firstEmployeeEndDate, secondEmployeeStartDate)) {
                        continue;
                    }

                    daysBetweenDates = getDaysBetween(firstEmployeeStartDate, firstEmployeeEndDate,
                            secondEmployeeStartDate, secondEmployeeEndDate);

                    if (isMaxGreater(max, daysBetweenDates)) {
                        max = daysBetweenDates;
                        firstEmployee = value.get(i);
                        secondEmployee = value.get(j);
                    }
                }
            }
        }

        checkForNonCrossingPeriods(max);

        return ResponseDto.builder()
                .differenceInDays(String.valueOf(max))
                .firstEmployee(Objects.requireNonNull(firstEmployee).getEmployeeId())
                .secondEmployee(Objects.requireNonNull(secondEmployee).getEmployeeId())
                .build();
    }

    private void checkForNonCrossingPeriods(final long max) {
        if(max == 0) {
            throw new NoCrossingPeriodsException("No crossing periods found!");
        }
    }

    private boolean isMaxGreater(final long max, final long daysBetween) {
        return max < daysBetween;
    }

    private boolean hasCrossedPeriod(final LocalDate firstEmployeeStartDate, final LocalDate secondEmployeeEndDate,
                                     final LocalDate firstEmployeeEndDate, final LocalDate secondEmployeeStartDate) {
        return !(firstEmployeeStartDate.isAfter(secondEmployeeEndDate)
                || firstEmployeeEndDate.isBefore(secondEmployeeStartDate));
    }

    private long getDaysBetween(final LocalDate firstEmployeeStartDate, final LocalDate firstEmployeeEndDate,
                                final LocalDate secondEmployeeStartDate, final LocalDate secondEmployeeEndDate) {
        final LocalDate maxStartDate = getMaxStartDate(firstEmployeeStartDate, secondEmployeeStartDate);
        final LocalDate minEndDate = getMinEndDate(firstEmployeeEndDate, secondEmployeeEndDate);
        return ChronoUnit.DAYS.between(maxStartDate, minEndDate);
    }

    private LocalDate getMaxStartDate(final LocalDate firstEmployeeStartDate, final LocalDate secondEmployeeStartDate) {
        return firstEmployeeStartDate.isAfter(secondEmployeeStartDate)
                ? firstEmployeeStartDate : secondEmployeeStartDate;
    }

    private LocalDate getMinEndDate(final LocalDate firstEmployeeEndDate, final LocalDate secondEmployeeEndDate) {
        return firstEmployeeEndDate.isBefore(secondEmployeeEndDate)
                ? firstEmployeeEndDate : secondEmployeeEndDate;
    }

    private boolean hasLessThanTwoEntries(final Map.Entry<String, List<EmployeeInfoDto>> entry) {
        return entry.getValue().size() < 2;
    }

    private  Map<String, List<EmployeeInfoDto>> getEmployeesInfoByProjectId(final List<EmployeeInfoDto> list) {
        return list.stream().collect(Collectors.groupingBy(EmployeeInfoDto::getProjectId));
    }

    private List<EmployeeInfoDto> getEmployeesInfo(final String[] array) {
        return Arrays.stream(array).map(item -> {
            final String[] columns = item.split(", ");
            return EmployeeInfoDto.builder()
                    .employeeId(columns[0])
                    .projectId(columns[1])
                    .startDate(parseFlexibleFormats(columns[2]))
                    .endDate(getEndDate(columns[3]))
                    .build();
        }).toList();
    }

    private LocalDate getEndDate(final String endDate) {
        return endDate.equals(NULL) ? LocalDate.now() : parseFlexibleFormats(endDate);
    }

    private LocalDate parseFlexibleFormats(final String date) {
        for (DateTimeFormatter formater : SUPPORTED_FORMATS) {
            try {
                return LocalDate.parse(date, formater);
            } catch (DateTimeParseException e) {
                log.debug("Failed to parse '{}' with pattern '{}'", date, formater.toString());
            }
        }
        throw new IllegalArgumentException("Unsupported date format: " + date);
    }


}
