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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "business_hours", length = 500)
    private String businessHours;

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

    public void update(String name, String phoneNumber, String businessHours, String description, String location, Double longitude, Double latitude, ImageFile imageFile) {
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
    public Restaurant(String name, String phoneNumber, String businessHours, String description, String location, Double longitude, Double latitude, ImageFile imageFile) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.businessHours = businessHours;
        this.description = description;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.imageFile = imageFile;
    }
}