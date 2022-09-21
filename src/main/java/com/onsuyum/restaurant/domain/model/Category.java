package com.onsuyum.restaurant.domain.model;

import com.onsuyum.common.domain.BaseTimeEntity;
import com.onsuyum.restaurant.dto.response.CategoryResponse;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "category", fetch = FetchType.LAZY)
    private List<RestaurantCategory> restaurantCategories = new ArrayList<>();

    @Builder
    public Category(String name, List<RestaurantCategory> restaurantCategories) {
        this.name = name;
        this.restaurantCategories = restaurantCategories;
    }

    public CategoryResponse toResponseDTO() {
        return CategoryResponse.builder()
                .id(id)
                .name(name)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();
    }
}