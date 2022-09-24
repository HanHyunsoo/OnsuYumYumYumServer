package com.onsuyum.restaurant.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.RestaurantService;
import com.onsuyum.restaurant.dto.request.RestaurantRequest;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> saveRestaurantWithRequest(@ModelAttribute RestaurantRequest dto) {
        RestaurantResponse restaurantResponse = restaurantService.save(dto, true);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_CREATE_RESTAURANT,
                        restaurantResponse
                );
    }

    @GetMapping
    public ResponseEntity<SuccessResponseBody<Page<RestaurantResponse>>> findAllRestaurantWithNotRequest(Pageable pageable,
                                                                               @RequestParam(name = "keyword", required = false) String name) {
        Page<RestaurantResponse> restaurantResponsePage;
        if (name.isBlank()) {
            restaurantResponsePage = restaurantService.findAllByRequest(pageable, false);
        } else {
            restaurantResponsePage = restaurantService.findAllByNameAndRequest(pageable, name, false);
        }

        if (restaurantResponsePage.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_RESTAURANTS);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RESTAURANTS,
                        restaurantResponsePage
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> findRestaurantWithNotRequest(@PathVariable Long id) {
        RestaurantResponse restaurantResponse = restaurantService.findByIdWithRequest(id, true);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RESTAURANT,
                        restaurantResponse
                );
    }

    @GetMapping("/random")
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> findRandomRestaurantWithNotRequest() {
        RestaurantResponse restaurantResponse = restaurantService.findRandomRestaurant();

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RANDOM_RESTAURANT,
                        restaurantResponse
                );
    }
}