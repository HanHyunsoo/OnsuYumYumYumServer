package com.onsuyum.restaurant.domain.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "restaurant_hash_tag")
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
}