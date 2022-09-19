package com.onsuyum.storage.domain.service;

import com.onsuyum.storage.domain.model.ImageFile;
import com.onsuyum.storage.domain.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    @Value("${spring.servlet.multipart.location}")
    private String localFilePath;
    private final ImageFileRepository imageFileRepository;
    private final S3StorageService s3StorageService;
    private final LocalStorageService localStorageService;

    @Transactional
    public ImageFile save(MultipartFile file) {
        String newFileName = createRandomFileName(file.getOriginalFilename());
//        String s3Url = s3StorageService.upload(file, newFileName);
        String s3Url = "";
        localStorageService.upload(file, newFileName);

        ImageFile imageFile = ImageFile.builder()
                .originalName(file.getOriginalFilename())
                .convertedName(newFileName)
                .s3Url(s3Url)
                .build();

        return imageFileRepository.save(imageFile);
    }

    @Transactional(readOnly = true)
    public Resource getResourceById(Long id) {
        ImageFile imageFile = imageFileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이미지 파일을 찾을 수 없음"));

        Resource resource = new FileSystemResource(localFilePath + "/" + imageFile.getConvertedName());
        if (!resource.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "로컬에서 파일을 찾을 수 없음");
        }

        return resource;
    }

    @Transactional
    public void delete(Long id) {
        ImageFile imageFile = imageFileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이미지 파일을 찾을 수 없음"));

        s3StorageService.delete(imageFile.getConvertedName());
        localStorageService.delete(imageFile.getConvertedName());

        imageFileRepository.delete(imageFile);
    }

    private String createRandomFileName(String originalFileName) {
        return UUID.randomUUID() + " " + originalFileName;
    }
}
