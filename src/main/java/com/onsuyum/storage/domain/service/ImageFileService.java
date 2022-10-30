package com.onsuyum.storage.domain.service;

import com.onsuyum.common.exception.ImageNotFoundException;
import com.onsuyum.common.exception.LocalFileNotFoundException;
import com.onsuyum.storage.domain.model.ImageFile;
import com.onsuyum.storage.domain.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        // TODO S3 용량 때문에 잠깐 막아둠, 실서비스를 사용할 때는 주석 제거하기
        String s3Url = s3StorageService.upload(file, newFileName);
//        String s3Url = "";
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
        ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(ImageNotFoundException::new);

        Resource resource = new FileSystemResource(localFilePath + "/" + imageFile.getConvertedName());
        if (!resource.exists()) {
            throw new LocalFileNotFoundException();
        }

        return resource;
    }

    @Transactional
    public void delete(Long id) {
        ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(ImageNotFoundException::new);

        s3StorageService.delete(imageFile.getConvertedName());
        localStorageService.delete(imageFile.getConvertedName());

        imageFileRepository.delete(imageFile);
    }

    private String createRandomFileName(String originalFileName) {
        return UUID.randomUUID() + " " + originalFileName;
    }
}
