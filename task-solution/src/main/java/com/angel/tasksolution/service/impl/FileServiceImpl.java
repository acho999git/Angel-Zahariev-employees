package com.angel.tasksolution.service.impl;

import com.angel.tasksolution.dto.ResponseDto;
import com.angel.tasksolution.service.api.DateProcessingService;
import com.angel.tasksolution.service.api.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class FileServiceImpl implements FileService {

    private final DateProcessingService dateProcessingService;

    @Autowired
    public FileServiceImpl(DateProcessingService dateProcessingService) {
        this.dateProcessingService = dateProcessingService;
    }

    @Override
    public ResponseDto processFile(MultipartFile file) throws IOException {
        final String[] fileRows = getFileRows(file);
        return dateProcessingService.getMaxCrossedWorkingPeriod(fileRows);
    }

    private String[] getFileRows(final MultipartFile file) throws IOException {
        final byte[] bytes = file.getBytes();
        final String content = new String(bytes, StandardCharsets.UTF_8);
        return content.split("\\n");
    }
}
