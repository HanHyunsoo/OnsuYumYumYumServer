package com.onsuyum.storage.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ImageFileResponse {
    private final Long id;
    private final String originalName;
    private final String convertedName;
    private final String s3Url;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    @Builder
    public ImageFileResponse(Long id, String originalName, String convertedName, String s3Url, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.originalName = originalName;
        this.convertedName = convertedName;
        this.s3Url = s3Url;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}