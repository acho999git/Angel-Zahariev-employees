package com.angel.tasksolution.constant;

import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public final class Constants {

    private Constants() {
       throw new IllegalStateException("Constants class can not be instantiated");
    }

    public static final String NULL = "NULL";

    public static final List<DateTimeFormatter> SUPPORTED_FORMATS = List.of(

            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
            DateTimeFormatter.ofPattern("yyyy MM dd"),

            DateTimeFormatter.ofPattern("yyyy-dd-MM"),
            DateTimeFormatter.ofPattern("yyyy/dd/MM"),
            DateTimeFormatter.ofPattern("yyyy.dd.MM"),
            DateTimeFormatter.ofPattern("yyyy dd MM"),


            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
            DateTimeFormatter.ofPattern("dd MM yyyy"),

            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("MM.dd.yyyy"),
            DateTimeFormatter.ofPattern("MM dd yyyy"),

            DateTimeFormatter.ofPattern("yyyy-MM-dd"),

            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),
            DateTimeFormatter.ofPattern("dd.MM.yyyy"),

            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("MM.dd.yyyy"),
            DateTimeFormatter.ofPattern("MM.dd.yyyy")
    );

}
