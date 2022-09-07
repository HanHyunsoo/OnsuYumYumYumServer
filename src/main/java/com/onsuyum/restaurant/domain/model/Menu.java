package com.onsuyum.restaurant.domain.model;

import com.onsuyum.common.domain.BaseTimeEntity;
import com.onsuyum.storage.domain.model.ImageFile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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

    @ManyToOne(targetEntity = Restaurant.class)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description")
    private String description;

    @OneToOne(targetEntity = ImageFile.class)
    @JoinColumn(name = "image_id")
    private ImageFile imageFile;

    public void update(String name, Integer price, String description, ImageFile imageFile) {
        this.name = name;
        this.price = price;
        this.description = description;
        if (imageFile != null) {
            this.imageFile = imageFile;
        }
    }

    @Builder
    public Menu(String name, Integer price, String description, ImageFile imageFile) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageFile = imageFile;
    }
}