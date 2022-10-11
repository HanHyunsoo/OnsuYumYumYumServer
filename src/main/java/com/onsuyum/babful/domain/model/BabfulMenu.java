package com.onsuyum.babful.domain.model;

import com.onsuyum.common.domain.BaseTimeEntity;
import com.onsuyum.common.util.StringListConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@DynamicUpdate
@Table(name = "babful_menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BabfulMenu extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "menuDate", nullable = false, unique = true)
    private LocalDate menuDate;

    @Column(name = "foods", length = 1000)
    @Convert(converter = StringListConverter.class)
    private List<String> foods;

    @Column(name = "delicious_food")
    private String deliciousFood;

    @Builder
    public BabfulMenu(LocalDate menuDate, List<String> foods, String deliciousFood) {
        this.menuDate = menuDate;
        this.foods = foods;
        this.deliciousFood = deliciousFood;
    }

    public void update(LocalDate menuDate, List<String> foods, String deliciousFood) {
        this.menuDate = menuDate;
        this.foods = foods;
        this.deliciousFood = deliciousFood;
    }
}
