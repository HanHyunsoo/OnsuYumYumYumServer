package com.onsuyum.storage.domain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.onsuyum.common.exception.CouldNotSaveFileInS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service("s3")
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    @Override
    public String upload(MultipartFile file, String newFileName) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, newFileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new CouldNotSaveFileInS3();
        }

        return amazonS3.getUrl(bucket, newFileName).toString();
    }

    @Override
    public boolean delete(String fileName) {
        fileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        return true;
    }
}
