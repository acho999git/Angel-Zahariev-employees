package com.angel.tasksolution.service.api;

import com.angel.tasksolution.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DateProcessingService {
    ResponseDto getMaxCrossedWorkingPeriod(final String[] rows) throws IOException;
}
