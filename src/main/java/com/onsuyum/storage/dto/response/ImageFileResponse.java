package com.onsuyum.storage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "Image File Response")
public class ImageFileResponse {

    @Schema(description = "이미지 파일 ID", example = "1")
    private final Long id;
    @Schema(description = "업로드 당시 원래 이름", example = "안녕.jpg")
    private final String originalName;
    @Schema(description = "업로드 당시 바뀐 이름", example = "안녕 99cd9e63-4907-422c-ae4b-b860c8053bc2.jpg ")
    private final String convertedName;
    @Schema(description = "S3 이미지 파일 URL", example = "https://~~~~~~.com")
    private final String s3Url;
    @Schema(description = "생성 시간", example = "2022-05-11 00:00:00")
    private final LocalDateTime createdDate;
    @Schema(description = "수정 시간", example = "2022-05-11 00:00:00")
    private final LocalDateTime modifiedDate;

    @Builder
    public ImageFileResponse(Long id, String originalName, String convertedName, String s3Url,
            LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.originalName = originalName;
        this.convertedName = convertedName;
        this.s3Url = s3Url;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}