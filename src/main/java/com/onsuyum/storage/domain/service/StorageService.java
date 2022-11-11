package com.onsuyum.storage.domain.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String upload(MultipartFile file, String newFileName);

    boolean delete(String fileName);
}
