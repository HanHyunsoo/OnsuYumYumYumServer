package com.onsuyum.restaurant.controller;

import com.onsuyum.restaurant.domain.model.Menu;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.service.MenuService;
import com.onsuyum.restaurant.domain.service.RestaurantCategoryService;
import com.onsuyum.restaurant.domain.service.RestaurantService;
import com.onsuyum.restaurant.dto.request.MenuRequestForm;
import com.onsuyum.restaurant.dto.request.RestaurantRequest;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    // TODO: 2022.09.18 각 서비스 별 메소드에 서비스끼리 결합도가 높음 이걸 나눠야 될거같음(예: 어떤 음식점에 속한 메뉴들을 검색하려면 음식점 서비스, 메뉴 서비스가 필요한데 메뉴서비스의 findAllByRestaurantId 메소드에서 레스토랑 서비스를 호출하고 있음) - 순환참조 발생 위험있음
    private final MenuService menuService;
    private final RestaurantService restaurantService;
    private final RestaurantCategoryService restaurantCategoryService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> saveRestaurantWithRequest(@ModelAttribute RestaurantRequest dto) {
        Restaurant restaurant = restaurantService.save(dto, true);
        restaurantCategoryService.saveRestaurantCategories(restaurant, dto.getCategoryNames());

        return ResponseEntity.ok(restaurant.toResponseDTO());
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> findAllRestaurantWithNotRequest(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantService.findAllByRequest(pageable, false);

        if (restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(restaurants.map(Restaurant::toResponseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findRestaurantWithNotRequest(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findByIdAndIsRequest(id, false);

        return ResponseEntity.ok(restaurant.toResponseDTO());
    }


    @PostMapping("/{id}/menus")
    public ResponseEntity<List<MenuResponse>> saveAllMenusByRestaurantIdWithRequest(@PathVariable Long id, @ModelAttribute MenuRequestForm dto) {
        Restaurant restaurant = restaurantService.findById(id);
        List<Menu> menus = menuService.saveAllWithRequest(restaurant, dto.getMenuRequestList());

        return ResponseEntity.ok(menus.stream().map(Menu::toResponseDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity<List<MenuResponse>> findAllByRestaurantId(@PathVariable Long id) {
        List<Menu> menus = menuService.findAllByRestaurantId(id);

        return ResponseEntity.ok(menus.stream().map(Menu::toResponseDTO).collect(Collectors.toList()));
    }
}