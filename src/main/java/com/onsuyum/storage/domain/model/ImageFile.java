package com.onsuyum.storage.domain.model;

import com.onsuyum.common.domain.BaseTimeEntity;
import com.onsuyum.storage.dto.response.ImageFileResponse;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "image_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "original_name", nullable = false, length = 500)
    private String originalName;

    @Column(name = "converted_name", nullable = false, length = 500)
    private String convertedName;

    @Column(name = "s3_url", nullable = false, length = 1000)
    private String s3Url;

    @Builder
    public ImageFile(String originalName, String convertedName, String s3Url) {
        this.originalName = originalName;
        this.convertedName = convertedName;
        this.s3Url = s3Url;
    }

    public ImageFileResponse toResponseDTO() {
        return ImageFileResponse.builder()
                                .id(id)
                                .originalName(originalName)
                                .convertedName(convertedName)
                                .s3Url(s3Url)
                                .createdDate(createdDate)
                                .modifiedDate(modifiedDate)
                                .build();
    }
}