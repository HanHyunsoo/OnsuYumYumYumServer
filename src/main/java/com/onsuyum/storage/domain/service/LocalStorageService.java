package com.onsuyum.storage.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service("local")
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

    @Value("${spring.servlet.multipart.location}")
    private String localFilePath;

    @Override
    public String upload(MultipartFile file, String newFileName) {
        File newFile = new File(localFilePath + "/" + newFileName);

        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버측에서 파일을 저장할 수 없습니다.");
        }

        return newFile.getPath();
    }

    @Override
    public boolean delete(String fileName) {
        String decodeFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        File file = new File(localFilePath + "/" + decodeFileName);

        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "로컬 저장소에 파일이 존재하지 않음.");
        }

        return file.delete();
    }
}
