package com.onsuyum.restaurant.domain.model;

import com.onsuyum.common.domain.BaseTimeEntity;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import com.onsuyum.restaurant.dto.response.RestaurantMenuResponse;
import com.onsuyum.storage.domain.model.ImageFile;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@DynamicUpdate
@Table(name = "menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Restaurant.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description")
    private String description;

    @OneToOne(targetEntity = ImageFile.class)
    @JoinColumn(name = "menu_image")
    private ImageFile menuImage;

    @Builder
    public Menu(Restaurant restaurant, String name, Integer price, String description,
            ImageFile menuImage) {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
        this.description = description;
        this.menuImage = menuImage;
    }

    public void update(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void updateImage(ImageFile menuImage) {
        this.menuImage = menuImage;
    }

    public MenuResponse toResponseDTO() {
        return MenuResponse.builder()
                           .id(id)
                           .name(name)
                           .price(price)
                           .description(description)
                           .menuImage(menuImage != null ? menuImage.toResponseDTO() : null)
                           .createdDate(createdDate)
                           .modifiedDate(modifiedDate)
                           .build();
    }

    public RestaurantMenuResponse toResponseWithRestaurantDTO() {
        return RestaurantMenuResponse.builder()
                                     .id(id)
                                     .name(name)
                                     .price(price)
                                     .description(description)
                                     .menuImage(
                                             menuImage != null ? menuImage.toResponseDTO() : null)
                                     .createdDate(createdDate)
                                     .modifiedDate(modifiedDate)
                                     .restaurantId(getRestaurant().getId())
                                     .restaurantName(getRestaurant().getName())
                                     .build();
    }
}