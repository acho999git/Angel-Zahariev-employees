package com.angel.tasksolution.controller;

import com.angel.tasksolution.dto.ResponseDto;
import com.angel.tasksolution.service.api.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = "multipart/form-data")
    public ResponseEntity<ResponseDto> uploadFile(@RequestPart(value = "file") MultipartFile file) throws IOException {
        final ResponseDto result = this.fileService.processFile(file);
        return ResponseEntity.ok(result);
    }

}
