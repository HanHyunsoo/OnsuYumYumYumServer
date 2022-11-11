package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "메뉴 Request(with multipart/form-data)")
@SuperBuilder
public class MultipartMenuRequest extends JsonMenuRequest {

    @Schema(description = "사진")
    private MultipartFile menuImage;

    public MultipartMenuRequest(String name, Integer price, String description,
            MultipartFile menuImage) {
        super(name, price, description);
        this.menuImage = menuImage;
    }
}

