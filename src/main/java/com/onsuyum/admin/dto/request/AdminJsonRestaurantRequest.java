package com.onsuyum.admin.dto.request;

import com.onsuyum.restaurant.dto.request.JsonRestaurantRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "Admin 전용 음식점 Request(with application/json)")
@SuperBuilder
public class AdminJsonRestaurantRequest extends JsonRestaurantRequest {

    @Schema(description = "익명 사용자에게 보이는지 유무(false면 보임)", example = "false")
    private boolean isRequest;

    public AdminJsonRestaurantRequest(String name, String phone, List<String> time, String summary, String location, Double longitude, Double latitude, boolean isRequest) {
        super(name, phone, time, summary, location, longitude, latitude);
        this.isRequest = isRequest;
    }
}
