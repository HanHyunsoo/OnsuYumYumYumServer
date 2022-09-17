package com.onsuyum.storage.controller;

import com.onsuyum.storage.domain.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageFileController {
    private final ImageFileService imageFileService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> display(@PathVariable Long id) {
        Resource resource = imageFileService.getResourceById(id);
        HttpHeaders headers = new HttpHeaders();

        try {
            String filePath = resource.getFile().getPath();
            headers.add("Content-type", Files.probeContentType(Path.of(filePath)));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 불러오기 실패");
        }

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
