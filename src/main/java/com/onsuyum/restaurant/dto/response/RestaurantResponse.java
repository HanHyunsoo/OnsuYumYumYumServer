package com.onsuyum.restaurant.dto.response;

import com.onsuyum.storage.dto.response.ImageFileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "음식점 Response")
public class RestaurantResponse {

    @Schema(description = "음식점 id", example = "1")
    private final Long id;
    @Schema(description = "이름", example = "돈내고돈먹기")
    private final String name;
    @Schema(description = "전화번호", example = "02-2616-5997")
    private final String phone;
    @Schema(description = "영업 시간")
    private final List<String> time;
    @Schema(description = "간략한 설명", example = "착한 가격의 생삼겹살집")
    private final String summary;
    @Schema(description = "주소", example = "서울 구로구 경인로3길 86 승일빌딩")
    private final String location;
    @Schema(description = "경도", example = "1.5")
    private final Double longitude;
    @Schema(description = "위도", example = "1.5")
    private final Double latitude;
    @Schema(description = "외부 이미지")
    private final ImageFileResponse outsideImage;
    @Schema(description = "내부 이미지")
    private final ImageFileResponse insideImage;
    @Schema(description = "생성 시간", example = "2022-05-11 00:00:00")
    private final LocalDateTime createdDate;
    @Schema(description = "수정 시간", example = "2022-05-11 00:00:00")
    private final LocalDateTime modifiedDate;

    @Builder
    public RestaurantResponse(Long id, String name, String phone, List<String> time, String summary,
            String location, Double longitude, Double latitude, ImageFileResponse outsideImage,
            ImageFileResponse insideImage, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.summary = summary;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.outsideImage = outsideImage;
        this.insideImage = insideImage;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}