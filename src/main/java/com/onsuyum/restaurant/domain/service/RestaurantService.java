package com.onsuyum.restaurant.domain.service;

import com.onsuyum.common.exception.ForbiddenRestaurantException;
import com.onsuyum.common.exception.RestaurantNotFoundException;
import com.onsuyum.common.exception.RestaurantTimeNotValidException;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.repository.RestaurantRepository;
import com.onsuyum.restaurant.dto.request.JsonRestaurantRequest;
import com.onsuyum.restaurant.dto.request.MultipartRestaurantRequest;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import com.onsuyum.storage.domain.model.ImageFile;
import com.onsuyum.storage.domain.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ImageFileService imageFileService;

    @Transactional
    public RestaurantResponse save(MultipartRestaurantRequest dto, boolean isRequest) {
        ImageFile outsideImage = null, insideImage = null;

        if (dto.getOutsideImage() != null) {
            outsideImage = imageFileService.save(dto.getOutsideImage());
        }

        if (dto.getInsideImage() != null) {
            insideImage = imageFileService.save(dto.getInsideImage());
        }

        Restaurant restaurant = Restaurant
                .builder()
                .isRequest(isRequest)
                .name(dto.getName())
                .phone(dto.getPhone())
                .time(dto.getTime())
                .summary(dto.getSummary())
                .location(dto.getLocation())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .outsideImage(outsideImage)
                .insideImage(insideImage)
                .build();

        restaurant = restaurantRepository.save(restaurant);

        return restaurant.toResponseDTO();
    }

    @Transactional(readOnly = true)
    public RestaurantResponse findById(Long id) {
        Restaurant restaurant = findEntityById(id);

        return restaurant.toResponseDTO();
    }

    @Transactional(readOnly = true)
    public RestaurantResponse findByIdWithRequest(Long id, boolean checkRequest) {
        Restaurant restaurant = findEntityById(id);

        if (checkRequest) {
            validIsRequest(restaurant);
        }

        return restaurant.toResponseDTO();
    }

    @Transactional(readOnly = true)
    public RestaurantResponse findRandomRestaurant() {
        return restaurantRepository.findRandomOne()
                .orElseThrow(RestaurantNotFoundException::new)
                .toResponseDTO();
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> findAllByRequest(Pageable pageable, boolean isRequest) {
        Page<Restaurant> restaurantPage = restaurantRepository.findAllByRequestWithCategoryCount(pageable, isRequest);

        return restaurantPage.map(Restaurant::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> findAllByNameAndRequest(Pageable pageable, String name, boolean isRequest) {
        Page<Restaurant> restaurantPage = restaurantRepository.findAllByNameLikeAndRequest(pageable, name, isRequest);

        return restaurantPage.map(Restaurant::toResponseDTO);
    }

    @Transactional
    public RestaurantResponse update(Long id, JsonRestaurantRequest dto) {
        Restaurant restaurant = findEntityById(id);

        restaurant.update(
                restaurant.isRequest(),
                dto.getName(),
                dto.getPhone(),
                dto.getTime(),
                dto.getSummary(),
                dto.getLocation(),
                dto.getLongitude(),
                dto.getLatitude()
        );

        restaurant = restaurantRepository.save(restaurant);

        return restaurant.toResponseDTO();
    }

    @Transactional
    public RestaurantResponse changeIsRequest(Long id) {
        Restaurant restaurant = findEntityById(id);
        restaurant.changeIsRequest();

        restaurant = restaurantRepository.save(restaurant);

        return restaurant.toResponseDTO();
    }

    @Transactional
    public void deleteById(Long id) {
        Restaurant restaurant = findEntityById(id);
        restaurantRepository.delete(restaurant);
    }

    // Service Layer 내에서 사용 가능한 메서드
    Restaurant findEntityById(Long id) {
        return restaurantRepository.findById(id).orElseThrow(RestaurantNotFoundException::new);
    }

    void validTime(Restaurant restaurant) {
        LocalDateTime modifiedDate = restaurant.getModifiedDate();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(modifiedDate, now);
        if (duration.getSeconds() > 300) {
            throw new RestaurantTimeNotValidException();
        }
    }

    void validIsRequest(Restaurant restaurant) {
        if (restaurant.isRequest()) {
            throw new ForbiddenRestaurantException();
        }
    }
}
