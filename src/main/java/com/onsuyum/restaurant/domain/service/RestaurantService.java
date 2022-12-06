package com.onsuyum.restaurant.domain.service;

import com.onsuyum.common.exception.*;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional
    public RestaurantResponse saveOutsideImage(Long id, MultipartFile multipartFile) {
        Restaurant restaurant = findEntityById(id);

        if (restaurant.getOutsideImage() != null) {
            throw new RestaurantOutsideImageAlreadyExistsException();
        }

        ImageFile imageFile = imageFileService.save(multipartFile);
        restaurant.updateOutsideImage(imageFile);

        return restaurantRepository.save(restaurant)
                                   .toResponseDTO();
    }

    @Transactional
    public RestaurantResponse saveInsideImage(Long id, MultipartFile multipartFile) {
        Restaurant restaurant = findEntityById(id);

        if (restaurant.getInsideImage() != null) {
            throw new RestaurantInsideImageAlreadyExistsException();
        }

        ImageFile imageFile = imageFileService.save(multipartFile);
        restaurant.updateInsideImage(imageFile);

        return restaurantRepository.save(restaurant)
                                   .toResponseDTO();
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
        Page<Restaurant> restaurantPage = restaurantRepository.findAllByRequestWithCategoryCount(
                pageable, isRequest);

        return restaurantPage.map(Restaurant::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> findAllByNameAndRequest(Pageable pageable, String name,
            boolean isRequest) {
        Page<Restaurant> restaurantPage = restaurantRepository.findAllByNameLikeAndRequest(pageable,
                name, isRequest);

        return restaurantPage.map(Restaurant::toResponseDTO);
    }

    @Transactional
    public RestaurantResponse update(Long id, JsonRestaurantRequest dto, Boolean isRequest) {
        Restaurant restaurant = findEntityById(id);

        if (isRequest == null) {
            isRequest = restaurant.isRequest();
        }

        restaurant.update(
                isRequest,
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
    public void deleteById(Long id) {
        Restaurant restaurant = findEntityById(id);

        if (restaurant.getInsideImage() != null) {
            imageFileService.delete(restaurant.getInsideImage().getId());
        }

        if (restaurant.getOutsideImage() != null) {
            imageFileService.delete(restaurant.getOutsideImage().getId());
        }

        restaurantRepository.delete(restaurant);
    }

    @Transactional
    public void deleteOutsideImageById(Long id) {
        Restaurant restaurant = findEntityById(id);
        ImageFile outsideImage = restaurant.getOutsideImage();

        if (outsideImage == null) {
            throw new RestaurantOutsideImageNotFoundException();
        }

        imageFileService.delete(outsideImage.getId());
        restaurant.updateOutsideImage(null);
    }

    @Transactional
    public void deleteInsideImageById(Long id) {
        Restaurant restaurant = findEntityById(id);
        ImageFile insideImage = restaurant.getInsideImage();

        if (insideImage == null) {
            throw new RestaurantInsideImageNotFoundException();
        }

        imageFileService.delete(insideImage.getId());
        restaurant.updateInsideImage(null);
    }

    // Service Layer 내에서 사용 가능한 메서드
    Restaurant findEntityById(Long id) {
        return restaurantRepository.findById(id)
                                   .orElseThrow(RestaurantNotFoundException::new);
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
