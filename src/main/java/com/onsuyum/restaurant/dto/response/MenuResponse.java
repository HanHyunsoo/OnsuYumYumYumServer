package com.onsuyum.restaurant.dto.response;

import com.onsuyum.storage.dto.response.ImageFileResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String description;
    private final ImageFileResponse menuImage;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    @Builder
    public MenuResponse(Long id, String name, Integer price, String description, ImageFileResponse menuImage, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.menuImage = menuImage;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}