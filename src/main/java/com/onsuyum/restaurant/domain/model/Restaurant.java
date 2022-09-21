package com.onsuyum.restaurant.domain.model;

import com.onsuyum.common.domain.BaseTimeEntity;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import com.onsuyum.storage.domain.model.ImageFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Entity
@DynamicUpdate
@Table(name = "restaurant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "is_request", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isRequest;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "time", length = 500)
    @Convert(converter = StringListConverter.class)
    private List<String> time;

    @Column(name = "summary", length = 1000)
    private String summary;

    @Column(name = "location", length = 500)
    private String location;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToOne(targetEntity = ImageFile.class)
    @JoinColumn(name = "outside_img")
    private ImageFile outsideImage;

    @OneToOne(targetEntity = ImageFile.class)
    @JoinColumn(name = "inside_img")
    private ImageFile insideImage;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Menu> menu = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<RestaurantCategory> restaurantCategories = new ArrayList<>();

    public void update(boolean isRequest, String name, String phone, List<String> time, String summary, String location, Double longitude, Double latitude, ImageFile outsideImage, ImageFile insideImage) {
        this.isRequest = isRequest;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.summary = summary;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        if (outsideImage != null) {
            this.outsideImage = outsideImage;
        }
        if (insideImage != null) {
            this.insideImage = insideImage;
        }
    }

    public void changeIsRequest() {
        this.isRequest = !isRequest;
    }

    @Builder
    public Restaurant(boolean isRequest, String name, String phone, List<String> time, String summary, String location, Double longitude, Double latitude, ImageFile outsideImage, ImageFile insideImage, List<Menu> menu, List<RestaurantCategory> restaurantCategories) {
        this.isRequest = isRequest;
        this.name = name;
        this.phone = phone;
        this.time = time;
        this.summary = summary;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.outsideImage = outsideImage;
        this.insideImage = insideImage;
        this.menu = menu;
        this.restaurantCategories = restaurantCategories;
    }

    public RestaurantResponse toResponseDTO() {
        return RestaurantResponse.builder()
                .id(id)
                .name(name)
                .phone(phone)
                .time(time)
                .summary(summary)
                .location(location)
                .longitude(longitude)
                .latitude(latitude)
                .outsideImage(outsideImage != null ? outsideImage.toResponseDTO() : null)
                .insideImage(insideImage != null ? insideImage.toResponseDTO() : null)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();
    }
}

@Converter
class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ";!";
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return String.join(SPLIT_CHAR, attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split(SPLIT_CHAR));
    }
}