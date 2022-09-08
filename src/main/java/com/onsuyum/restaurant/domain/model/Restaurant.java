package com.onsuyum.restaurant.domain.model;

import com.onsuyum.common.domain.BaseTimeEntity;
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

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "business_hours", length = 500)
    @Convert(converter = StringListConverter.class)
    private List<String> businessHours;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "location", length = 500)
    private String location;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToOne(targetEntity = ImageFile.class)
    @JoinColumn(name = "image_id")
    private ImageFile imageFile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "restaurant")
    private List<RestaurantHashTag> restaurantHashTags = new ArrayList<>();

    public void update(String name, String phoneNumber, List<String> businessHours, String description, String location, Double longitude, Double latitude, ImageFile imageFile) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.businessHours = businessHours;
        this.description = description;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        if (imageFile != null) {
            this.imageFile = imageFile;
        }
    }

    @Builder
    public Restaurant(boolean isRequest, String name, String phoneNumber, List<String> businessHours, String description, String location, Double longitude, Double latitude, ImageFile imageFile, List<Menu> menus, List<RestaurantHashTag> restaurantHashTags) {
        this.isRequest = isRequest;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.businessHours = businessHours;
        this.description = description;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageFile = imageFile;
        this.menus = menus;
        this.restaurantHashTags = restaurantHashTags;
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