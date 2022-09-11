package com.onsuyum.storage.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StorageService {
    String upload(MultipartFile file);
    boolean delete(String fileName);

    default String createRandomFileName(String originalFileName) {
        return UUID.randomUUID() + " " + originalFileName;
    }
}
