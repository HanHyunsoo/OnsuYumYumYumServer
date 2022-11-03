package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
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
public class MultipartRestaurantRequest extends JsonRestaurantRequest {

    @Schema(description = "외부 이미지")
    private MultipartFile outsideImage;
    @Schema(description = "내부 이미지")
    private MultipartFile insideImage;

    public MultipartRestaurantRequest(String name, String phone, List<String> time, String summary,
            String location, Double longitude, Double latitude, MultipartFile outsideImage,
            MultipartFile insideImage) {
        super(name, phone, time, summary, location, longitude, latitude);
        this.outsideImage = outsideImage;
        this.insideImage = insideImage;
    }
}
