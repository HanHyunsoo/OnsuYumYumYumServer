package com.onsuyum.restaurant.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class RestaurantRequest {
    private Long id;
    private String name;
    private String phone;
    private List<String> time;
    private String summary;
    private String location;
    private Double longitude;
    private Double latitude;
    private MultipartFile outsideImage;
    private MultipartFile insideImage;

    @Builder
    public RestaurantRequest(Long id, String name, String phone, List<String> time, String summary, String location, Double longitude, Double latitude, MultipartFile outsideImage, MultipartFile insideImage) {
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
    }
}
