package com.discovery.controller;

import com.discovery.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/interstellar/files")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final FileService fileService;

    @PostMapping("/import")
    public boolean importFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.importPlanets(file);
        return true;
    }
}
