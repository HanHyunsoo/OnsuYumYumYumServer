package com.onsuyum.storage.domain.model;

import com.onsuyum.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(name = "local_url", nullable = false, length = 1000)
    private String localUrl;

    @Column(name = "s3_url", nullable = false, length = 1000)
    private String s3Url;
}