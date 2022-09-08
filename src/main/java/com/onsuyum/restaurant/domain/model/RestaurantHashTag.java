package com.onsuyum.restaurant.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "restaurant_hash_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(targetEntity = Hashtag.class)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    @Builder
    public RestaurantHashTag(Restaurant restaurant, Hashtag hashtag) {
        this.restaurant = restaurant;
        this.hashtag = hashtag;
    }
}