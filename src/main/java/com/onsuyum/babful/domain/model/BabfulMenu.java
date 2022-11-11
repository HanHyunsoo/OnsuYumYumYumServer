package com.onsuyum.babful.domain.model;

import com.onsuyum.babful.dto.response.BabfulMenuResponse;
import com.onsuyum.common.domain.BaseTimeEntity;
import com.onsuyum.common.util.StringListConverter;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

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

    public BabfulMenuResponse toResponseDTO() {
        return BabfulMenuResponse.builder()
                                 .id(id)
                                 .menuDate(menuDate)
                                 .foods(foods)
                                 .deliciousFood(deliciousFood)
                                 .createdDate(createdDate)
                                 .modifiedDate(modifiedDate)
                                 .build();
    }
}
