package com.onsuyum.storage.domain.service;

import com.onsuyum.common.exception.CouldNotSaveFileInLocal;
import com.onsuyum.common.exception.LocalFileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            throw new CouldNotSaveFileInLocal();
        }

        return newFile.getPath();
    }

    @Override
    public boolean delete(String fileName) {
        String decodeFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        File file = new File(localFilePath + "/" + decodeFileName);

        if (!file.exists()) {
            throw new LocalFileNotFoundException();
        }

        return file.delete();
    }
}
