package com.onsuyum.restaurant.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class MenuRequest {
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private MultipartFile menuImage;

    @Builder
    public MenuRequest(Long id, String name, Integer price, String description, MultipartFile menuImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.menuImage = menuImage;
    }
}
