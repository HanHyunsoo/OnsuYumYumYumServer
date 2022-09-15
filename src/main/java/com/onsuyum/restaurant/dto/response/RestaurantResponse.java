package com.onsuyum.restaurant.dto.response;

import com.onsuyum.storage.dto.response.ImageFileResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class RestaurantResponse {
    private final Long id;
    private final String name;
    private final String phone;
    private final List<String> time;
    private final String summary;
    private final String location;
    private final Double longitude;
    private final Double latitude;
    private final ImageFileResponse outsideImage;
    private final ImageFileResponse insideImage;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    @Builder
    public RestaurantResponse(Long id, String name, String phone, List<String> time, String summary, String location, Double longitude, Double latitude, ImageFileResponse outsideImage, ImageFileResponse insideImage, LocalDateTime createdDate, LocalDateTime modifiedDate) {
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