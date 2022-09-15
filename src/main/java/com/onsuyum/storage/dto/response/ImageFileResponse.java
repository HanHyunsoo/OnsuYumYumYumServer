package com.onsuyum.storage.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageFileResponse {
    private final Long id;
    private final String originalName;
    private final String convertedName;
    private final String s3Url;

    @Builder
    public ImageFileResponse(Long id, String originalName, String convertedName, String s3Url) {
        this.id = id;
        this.originalName = originalName;
        this.convertedName = convertedName;
        this.s3Url = s3Url;
    }
}