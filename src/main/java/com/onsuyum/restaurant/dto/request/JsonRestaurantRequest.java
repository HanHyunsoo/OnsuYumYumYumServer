package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "음식점 Request(with application/json)")
@SuperBuilder
public class JsonRestaurantRequest {
    @Schema(description = "이름", example = "토마토", required = true)
    private String name;
    @Schema(description = "전화번호", example = "02-2060-0029")
    private String phone;
    @Schema(description = "영업 시간", example = "[\n" +
            "   \"월 09:00 ~ 20:00\",\n" +
            "   \"화 09:00 ~ 20:00\",\n" +
            "   \"수 09:00 ~ 20:00\",\n" +
            "   \"목 09:00 ~ 20:00\",\n" +
            "   \"금 09:00 ~ 20:00\",\n" +
            "   \"토 09:00 ~ 20:00\",\n" +
            "   \"일: 정기 휴무 (매주 일요일)\"\n" +
            "]")
    private List<String> time;
    @Schema(description = "간략한 설명", example = "성공회대생 혼밥성지")
    private String summary;
    @Schema(description = "주소", example = "서울 구로구 연동로 312")
    private String location;
    @Schema(description = "경도", example = "1.5")
    private Double longitude;
    @Schema(description = "위도", example = "1.5")
    private Double latitude;

    public JsonRestaurantRequest(String name, String phone, List<String> time, String summary, String location, Double longitude, Double latitude) {
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.summary = summary;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
