package com.onsuyum.restaurant.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "restaurant_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public RestaurantCategory(Restaurant restaurant, Category category) {
        this.restaurant = restaurant;
        this.category = category;
    }
}