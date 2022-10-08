package com.onsuyum.restaurant.dto.response;

import com.onsuyum.storage.dto.response.ImageFileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@Schema(description = "메뉴와 레스토랑 정보")
public class RestaurantMenuResponse extends MenuResponse {

    @Schema(description = "레스토랑 id", example = "1")
    private final Long restaurantId;

    @Schema(description = "레스토랑 이름", example = "토마토")
    private final String restaurantName;

    public RestaurantMenuResponse(Long id, String name, Integer price, String description, ImageFileResponse menuImage, LocalDateTime createdDate, LocalDateTime modifiedDate, Long restaurantId, String restaurantName) {
        super(id, name, price, description, menuImage, createdDate, modifiedDate);
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }
}
