package com.onsuyum.storage.domain.service;

import com.onsuyum.common.exception.ImageNotFoundException;
import com.onsuyum.common.exception.LocalFileNotFoundException;
import com.onsuyum.storage.domain.model.ImageFile;
import com.onsuyum.storage.domain.repository.ImageFileRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageFileService {

    @Value("${spring.profiles.active}")
    private String activeProfile;
    private final ImageFileRepository imageFileRepository;
    private final S3StorageService s3StorageService;
    private final LocalStorageService localStorageService;
    @Value("${spring.servlet.multipart.location}")
    private String localFilePath;

    @Transactional
    public ImageFile save(MultipartFile file) {
        String newFileName = createRandomFileName(file.getOriginalFilename());
        // 개발 환경이면 s3 미사용
        String s3Url =
                (activeProfile.equals("production")) ? s3StorageService.upload(file, newFileName)
                        : "dev";
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
                                                 .orElseThrow(ImageNotFoundException::new);

        Resource resource = new FileSystemResource(
                localFilePath + "/" + imageFile.getConvertedName());
        if (!resource.exists()) {
            throw new LocalFileNotFoundException();
        }

        return resource;
    }

    @Transactional
    public void delete(Long id) {
        ImageFile imageFile = imageFileRepository.findById(id)
                                                 .orElseThrow(ImageNotFoundException::new);

        s3StorageService.delete(imageFile.getConvertedName());
        localStorageService.delete(imageFile.getConvertedName());

        imageFileRepository.delete(imageFile);
    }

    private String createRandomFileName(String originalFileName) {
        return UUID.randomUUID() + " " + originalFileName;
    }
}
