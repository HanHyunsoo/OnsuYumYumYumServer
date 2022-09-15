package com.onsuyum.restaurant.domain.service;

import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.repository.RestaurantRepository;
import com.onsuyum.restaurant.dto.request.RestaurantRequest;
import com.onsuyum.storage.domain.model.ImageFile;
import com.onsuyum.storage.domain.service.ImageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ImageFileService imageFileService;

    @Transactional
    public Restaurant save(RestaurantRequest dto, boolean isRequest) {
        ImageFile outsideImage = imageFileService.save(dto.getOutsideImage());
        ImageFile insideImage = imageFileService.save(dto.getInsideImage());

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

        return restaurantRepository.save(restaurant);
    }

    @Transactional(readOnly = true)
    public Restaurant findByIdAndIsRequest(Long id, boolean isRequest) {

        return restaurantRepository.findByIdAndRequest(id, isRequest)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "음식점 정보 DB에 존재하지 않음"));
    }

    @Transactional(readOnly = true)
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "음식점 정보 DB에 존재하지 않음"));
    }

    @Transactional(readOnly = true)
    public Page<Restaurant> findAllByRequest(Pageable pageable, boolean isRequest) {
        return restaurantRepository.findAllByRequestWithCategoryCount(pageable, isRequest);
    }

    @Transactional
    public Restaurant update(Long id, RestaurantRequest dto) {
        Restaurant restaurant = findById(id);
        ImageFile outsideImage = restaurant.getOutsideImage();
        ImageFile insideImage = restaurant.getInsideImage();

        if (dto.getOutsideImage() != null) {
            imageFileService.delete(outsideImage.getId());
            outsideImage = imageFileService.save(dto.getOutsideImage());
        }

        if (dto.getInsideImage() != null) {
            imageFileService.delete(insideImage.getId());
            insideImage = imageFileService.save(dto.getInsideImage());
        }

        restaurant.update(
                restaurant.isRequest(),
                dto.getName(),
                dto.getPhone(),
                dto.getTime(),
                dto.getSummary(),
                dto.getLocation(),
                dto.getLongitude(),
                dto.getLatitude(),
                outsideImage,
                insideImage
        );

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public Restaurant changeIsRequest(Long id) {
        Restaurant restaurant = findById(id);
        restaurant.changeIsRequest();

        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void deleteById(Long id) {
        Restaurant restaurant = findById(id);
        restaurantRepository.delete(restaurant);
    }
}
